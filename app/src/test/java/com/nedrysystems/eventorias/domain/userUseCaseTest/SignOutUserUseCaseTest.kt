package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignOutUserUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.verify
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(MockitoJUnitRunner::class)
class SignOutUserUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: SignOutUserUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = SignOutUserUseCase(repository)
    }

    @Test
    fun `invoke should call signOut on repository`() {

        useCase.invoke()


        verify(repository).signOut()
    }
}
