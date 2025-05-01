package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.OnSignInResultUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class OnSignInResultUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: OnSignInResultUseCase

    @Before
    fun setUp() {
        repository = mock(UserRepositoryInterface::class.java)
        useCase = OnSignInResultUseCase(repository)
    }

    @Test
    fun `invoke should call onSignInResult on repository with given result`() {
        val result = mock(FirebaseAuthUIAuthenticationResult::class.java)

        useCase.invoke(result)

        verify(repository).onSignInResult(result)
    }
}
