package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.InsertCurrentUserUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.verify
import org.mockito.kotlin.times
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertCurrentUserUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: InsertCurrentUserUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = InsertCurrentUserUseCase(repository)
    }

    @Test
    fun `invoke should call insertCurrentUser on repository`() {

        useCase.invoke()

        verify(repository, times(1)).insertCurrentUser()
    }
}
