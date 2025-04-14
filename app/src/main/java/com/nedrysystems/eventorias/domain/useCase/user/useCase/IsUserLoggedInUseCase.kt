package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {
    operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}