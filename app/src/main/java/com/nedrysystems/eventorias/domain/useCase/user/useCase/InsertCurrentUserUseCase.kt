package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class for inserting the current user's data into the repository.
 *
 * This use case encapsulates the logic of inserting the current user's data into the user repository.
 * It calls the corresponding method in the repository to perform the insertion.
 *
 * @property repository The user repository interface responsible for managing user data.
 *
 * @constructor Creates a new instance of [InsertCurrentUserUseCase] by injecting the user repository.
 */
class InsertCurrentUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the use case to insert the current user's data into the repository.
     *
     * This function calls the repository to insert the current user's data.
     * It is typically used when a user has signed in and their data needs to be saved.
     */
    operator fun invoke() {
        repository.insertCurrentUser()
    }
}