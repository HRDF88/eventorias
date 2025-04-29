package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case class to load the current user.
 *
 * This use case encapsulates the logic of loading the user data. It calls the corresponding method
 * in the repository to retrieve the user information as a flow, which can be observed over time.
 *
 * @property repository The user repository interface responsible for managing user data.
 *
 * @constructor Creates a new instance of [LoadUserUseCase] by injecting the user repository.
 */
class LoadUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {

    /**
     * Invokes the use case to load the current user data.
     *
     * This function calls the repository to fetch the user data as a flow, which can be collected
     * to receive updates about the user's information.
     *
     * @return A [Flow] of [User] representing the current user data. It emits user information as
     *         it changes, or completes if no data is available.
     */
    operator fun invoke(): Flow<User> {
        return repository.loadUser()
    }
}