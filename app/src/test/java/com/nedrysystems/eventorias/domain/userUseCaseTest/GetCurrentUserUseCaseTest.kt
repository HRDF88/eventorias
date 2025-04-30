package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetCurrentUserUseCase
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetCurrentUserUseCaseTest {

    private val userRepository: UserRepositoryInterface = mock()
    private val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)

    @Test
    fun `invoke should return current user when authenticated`() {
        // Given
        val expectedUser = User(
            id = "123",
            name = "John Doe",
            email = "johndoe@example.com",
            profilPicture = "profile_picture_url",
            asNotification = true
        )


        `when`(userRepository.getCurrentUser()).thenReturn(expectedUser)

        val result = getCurrentUserUseCase.invoke()

        assertEquals(expectedUser, result)
    }

    @Test
    fun `invoke should return null when no user is authenticated`() {
        `when`(userRepository.getCurrentUser()).thenReturn(null)

        val result = getCurrentUserUseCase.invoke()

        assertNull(result)
    }
}
