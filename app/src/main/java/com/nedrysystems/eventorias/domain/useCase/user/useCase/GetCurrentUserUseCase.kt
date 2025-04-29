package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import javax.inject.Inject

/**
 * A use case class for retrieving the current user.
 *
 * This use case encapsulates the logic of fetching the currently authenticated user from
 * the repository. It calls the appropriate method in the repository to get the user data.
 *
 * @property repository The repository interface responsible for managing user data.
 *
 * @constructor Creates a new instance of [GetCurrentUserUseCase] by injecting the repository.
 */
class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the use case to get the current authenticated user.
     *
     * This function calls the repository to fetch the current user data. It returns a [User]
     * object containing the user information, or null if no user is currently authenticated.
     *
     * @return The current [User] or null if no user is authenticated.
     */
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}