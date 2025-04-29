package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class for retrieving the notification settings of a user.
 *
 * This use case encapsulates the logic of fetching the notification setting for a specific
 * user by calling the corresponding method in the user repository.
 *
 * @property userApi The user repository interface responsible for managing user data and settings.
 *
 * @constructor Creates a new instance of [GetNotificationSettingUseCase] by injecting the user repository.
 */
class GetNotificationSettingUseCase @Inject constructor(
    private val userApi: UserRepositoryInterface
) {

    /**
     * Invokes the use case to get the notification setting of a user.
     *
     * This function calls the repository to retrieve the notification setting for the user
     * identified by [userId]. It returns a [Boolean] indicating whether notifications are enabled
     * for the user.
     *
     * @param userId The unique identifier of the user whose notification setting is to be retrieved.
     * @return A [Boolean] indicating whether notifications are enabled for the user.
     */
    suspend operator fun invoke(userId: String): Boolean {
        return userApi.getNotificationSetting(userId)
    }
}