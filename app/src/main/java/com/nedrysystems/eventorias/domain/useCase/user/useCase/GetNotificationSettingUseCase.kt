package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

class GetNotificationSettingUseCase @Inject constructor(
    private val userApi: UserRepositoryInterface
) {
    suspend operator fun invoke(userId: String): Boolean {
        return userApi.getNotificationSetting(userId)
    }
}