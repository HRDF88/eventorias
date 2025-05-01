package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.domain.useCase.user.useCase.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class LoadUserUseCaseTest {

    private lateinit var repository: UserRepositoryInterface
    private lateinit var useCase: LoadUserUseCase

    @Before
    fun setUp() {
        repository = mock(UserRepositoryInterface::class.java)
        useCase = LoadUserUseCase(repository)
    }

    @Test
    fun `invoke should return user flow from repository`(): Unit = runBlocking {

        val expectedUser = User(
            id = "u1",
            name = "Jocelyn",
            email = "Jocelyn.testing@gmail.com",
            profilPicture = "https://example.com/pic.jpg",
            asNotification = true
        )

        `when`(repository.loadUser()).thenReturn(flowOf(expectedUser))

        val result = useCase.invoke().first()

        assertEquals(expectedUser, result)

        verify(repository).loadUser()
    }
}
