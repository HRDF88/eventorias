package com.nedrysystems.eventorias.domain

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetNotificationSettingUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetNotificationSettingUseCaseTest {

    private val userRepository: UserRepositoryInterface = mock()
    private val getNotificationSettingUseCase = GetNotificationSettingUseCase(userRepository)

    @Test
    fun `invoke should return true when notifications are enabled for user`() = runBlocking {
        val userId = "123"
        val notificationEnabled = true

        `when`(userRepository.getNotificationSetting(userId)).thenReturn(notificationEnabled)

        val result = getNotificationSettingUseCase.invoke(userId)

        assertTrue(result)
    }

    @Test
    fun `invoke should return false when notifications are disabled for user`() = runBlocking {

        val userId = "123"
        val notificationEnabled = false

        `when`(userRepository.getNotificationSetting(userId)).thenReturn(notificationEnabled)

        val result = getNotificationSettingUseCase.invoke(userId)

        assertFalse(result)
    }
}
