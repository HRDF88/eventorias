package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {
    operator fun invoke(): Flow<User> {
        return repository.loadUser()
    }
}