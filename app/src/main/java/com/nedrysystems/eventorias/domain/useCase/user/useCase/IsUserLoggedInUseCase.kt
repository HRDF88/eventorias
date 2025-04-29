package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class to check if a user is logged in.
 *
 * This use case encapsulates the logic of checking whether the current user is logged in.
 * It calls the corresponding method in the repository to determine the login status of the user.
 *
 * @property repository The user repository interface responsible for managing user data.
 *
 * @constructor Creates a new instance of [IsUserLoggedInUseCase] by injecting the user repository.
 */
class IsUserLoggedInUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the use case to check if the user is logged in.
     *
     * This function calls the repository to determine whether the current user is logged in.
     * It returns `true` if the user is logged in, and `false` otherwise.
     *
     * @return Boolean indicating whether the user is logged in or not.
     */
    operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}