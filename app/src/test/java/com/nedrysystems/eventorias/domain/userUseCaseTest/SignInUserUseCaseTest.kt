package com.nedrysystems.eventorias.domain.userUseCaseTest

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignInUserUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SignInUserUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: SignInUserUseCase
    private lateinit var launcher: ActivityResultLauncher<Intent>

    @Before
    fun setUp() {
        repository = mock()
        launcher = mock()
        useCase = SignInUserUseCase(repository)
    }

    @Test
    fun `invoke should call signIn on repository with launcher`() = runBlocking {
        val mockUser = mock<User>()

        `when`(repository.signIn(launcher)).thenReturn(mockUser)

        val result = useCase(launcher)


        verify(repository).signIn(launcher)

        assert(result == mockUser)
    }

    @Test
    fun `invoke should return null if signIn fails or is canceled`() = runTest {

        `when`(repository.signIn(launcher)).thenReturn(null)

        val result = useCase(launcher)

        verify(repository).signIn(launcher)
        assert(result == null)
    }
}
