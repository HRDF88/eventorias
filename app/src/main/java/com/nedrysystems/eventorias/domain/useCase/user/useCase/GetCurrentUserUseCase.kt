package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}