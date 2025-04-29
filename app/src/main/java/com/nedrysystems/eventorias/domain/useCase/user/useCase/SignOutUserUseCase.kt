package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class to handle the user sign-out process.
 *
 * This use case encapsulates the logic for signing out a user. It delegates the actual sign-out
 * process to the repository, which interacts with Firebase authentication.
 *
 * @property repository The user repository interface responsible for managing user authentication.
 *
 * @constructor Creates a new instance of [SignOutUserUseCase] by injecting the user repository.
 */
class SignOutUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the sign-out process for the user.
     *
     * This function triggers the sign-out process by calling the repository's sign-out method.
     * The user will be signed out from Firebase authentication.
     */
    operator fun invoke() {
        repository.signOut()
    }
}