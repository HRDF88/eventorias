package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class to enable or disable notifications for a user.
 *
 * This use case encapsulates the logic of enabling or disabling notifications. It delegates
 * the actual update of the notification setting to the repository.
 *
 * @property repository The user repository interface responsible for managing user data and
 *                      updating the notification settings.
 *
 * @constructor Creates a new instance of [SetNotificationEnableUseCase] by injecting the user repository.
 */
class SetNotificationEnableUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the use case to enable or disable notifications.
     *
     * This function calls the repository method to update the notification setting for the user.
     *
     * @param enable A boolean indicating whether notifications should be enabled (true) or disabled (false).
     */
    operator fun invoke(enable: Boolean) {
        repository.setNotificationEnable(enable)
    }
}