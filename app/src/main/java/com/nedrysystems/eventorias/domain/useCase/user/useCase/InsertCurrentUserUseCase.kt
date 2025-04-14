package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

class InsertCurrentUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {
    operator fun invoke() {
        repository.insertCurrentUser()
    }
}