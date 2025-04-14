package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

class SignOutUserUseCase @Inject constructor(
    private val repository: UserRepositoryInterface
) {
    operator fun invoke() {
        repository.signOut()
    }
}