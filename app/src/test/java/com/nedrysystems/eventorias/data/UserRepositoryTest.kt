package com.nedrysystems.eventorias.data

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.data.repository.UserRepository
import com.nedrysystems.eventorias.data.webService.serviceInterface.UserApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test

class UserRepositoryTest {

    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository

    private val testUser = com.nedrysystems.eventorias.domain.model.User(
        id = "123",
        name = "Jocelyn Testing",
        email = "jocelyn.testing@gmail.com",
        profilPicture = "http://example.com/pic.jpg",
        asNotification = true
    )
    @Before
    fun setup() {
        userApi = mock()
        userRepository = UserRepository(userApi)
    }

    @Test
    fun `getCurrentUser should return current user`() {
        `when`(userApi.getCurrentUser()).thenReturn(testUser)

        val result = userRepository.getCurrentUser()

        assertEquals(testUser, result)
    }

    @Test
    fun `isUserLoggedIn should return true if user is logged in`() {
        `when`(userApi.isUserLoggedIn()).thenReturn(true)

        val result = userRepository.isUserLoggedIn()

        assertTrue(result)
    }

    @Test
    fun `getNotificationSetting should return true when notifications are enabled`() = runBlocking {
        `when`(userApi.getNotificationSetting("123")).thenReturn(true)

        val result = userRepository.getNotificationSetting("123")

        assertTrue(result)
    }

    @Test
    fun `onSignInResult should complete signInDeferred with user on success`() = runBlocking {
        // Arrange
        val mockResult: FirebaseAuthUIAuthenticationResult = mock()
        `when`(mockResult.resultCode).thenReturn(Activity.RESULT_OK)
        `when`(userApi.getCurrentUser()).thenReturn(testUser)

        val launcher: ActivityResultLauncher<Intent> = mock()
        val deferred = async { userRepository.signIn(launcher) }

        delay(100)
        userRepository.onSignInResult(mockResult)

        val user = deferred.await()
        assertEquals(testUser, user)
    }

    @Test
    fun `onSignInResult should complete signInDeferred with null on failure`() = runBlocking {
        val mockResult: FirebaseAuthUIAuthenticationResult = mock()
        `when`(mockResult.resultCode).thenReturn(Activity.RESULT_CANCELED)

        val launcher: ActivityResultLauncher<Intent> = mock()
        val deferred = async { userRepository.signIn(launcher) }

        delay(100)
        userRepository.onSignInResult(mockResult)

        val user = deferred.await()
        assertNull(user)
    }
}