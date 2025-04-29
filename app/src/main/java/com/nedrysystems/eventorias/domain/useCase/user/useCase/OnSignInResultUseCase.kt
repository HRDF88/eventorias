package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

/**
 * A use case class to handle the result of a sign-in attempt.
 *
 * This use case encapsulates the logic of handling the result of a Firebase authentication
 * sign-in operation. It delegates the processing of the result to the repository.
 *
 * @property repository The user repository interface responsible for managing user data and
 *                      processing the sign-in result.
 *
 * @constructor Creates a new instance of [OnSignInResultUseCase] by injecting the user repository.
 */
class OnSignInResultUseCase @Inject constructor(private val repository: UserRepositoryInterface) {

    /**
     * Invokes the use case to handle the result of a sign-in attempt.
     *
     * This function calls the repository method responsible for handling the result of a
     * Firebase authentication UI sign-in attempt.
     *
     * @param result The [FirebaseAuthUIAuthenticationResult] containing the result of the sign-in
     *               operation, including whether it was successful and user information.
     */
    operator fun invoke(result: FirebaseAuthUIAuthenticationResult) {
        repository.onSignInResult(result)
    }
}