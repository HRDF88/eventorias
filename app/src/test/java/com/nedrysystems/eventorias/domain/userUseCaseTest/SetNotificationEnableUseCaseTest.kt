package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SetNotificationEnableUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class SetNotificationEnableUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: SetNotificationEnableUseCase

    @Before
    fun setUp() {
        repository = mock(UserRepositoryInterface::class.java)
        useCase = SetNotificationEnableUseCase(repository)
    }

    @Test
    fun `invoke should call repository setNotificationEnable with true`() {
        useCase(true)
        verify(repository).setNotificationEnable(true)
    }

    @Test
    fun `invoke should call repository setNotificationEnable with false`() {
        useCase(false)
        verify(repository).setNotificationEnable(false)
    }
}
