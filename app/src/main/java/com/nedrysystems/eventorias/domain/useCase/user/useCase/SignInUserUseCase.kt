package com.nedrysystems.eventorias.domain.useCase.user.useCase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import javax.inject.Inject

/**
 * A use case class to handle the user sign-in process.
 *
 * This use case encapsulates the logic for signing in a user. It delegates the actual sign-in
 * process to the repository, which interacts with Firebase authentication.
 *
 * @property repository The user repository interface responsible for managing user authentication.
 *
 * @constructor Creates a new instance of [SignInUserUseCase] by injecting the user repository.
 */
class SignInUserUseCase @Inject constructor(private val repository: UserRepositoryInterface) {

    /**
     * Invokes the sign-in process for the user.
     *
     * This function triggers the sign-in process by calling the repository's sign-in method.
     * It uses an [ActivityResultLauncher] to launch the sign-in intent.
     *
     * @param launcher The [ActivityResultLauncher] used to launch the sign-in intent.
     *
     * @return A [User] object representing the signed-in user, or null if the sign-in failed or was canceled.
     */
    suspend operator fun invoke(launcher: ActivityResultLauncher<Intent>): User? {
        return repository.signIn(launcher)

    }

}
