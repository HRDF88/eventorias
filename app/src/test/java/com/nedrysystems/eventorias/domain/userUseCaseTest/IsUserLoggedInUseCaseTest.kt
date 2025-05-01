package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.IsUserLoggedInUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class IsUserLoggedInUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: IsUserLoggedInUseCase

    @Before
    fun setUp() {
        repository = mock(UserRepositoryInterface::class.java)
        useCase = IsUserLoggedInUseCase(repository)
    }

    @Test
    fun `invoke should return true when repository returns true`() {
        `when`(repository.isUserLoggedIn()).thenReturn(true)

        val result = useCase.invoke()

        assertTrue(result)
        verify(repository).isUserLoggedIn()
    }

    @Test
    fun `invoke should return false when repository returns false`() {
        `when`(repository.isUserLoggedIn()).thenReturn(false)

        val result = useCase.invoke()

        assertFalse(result)
        verify(repository).isUserLoggedIn()
    }
}
