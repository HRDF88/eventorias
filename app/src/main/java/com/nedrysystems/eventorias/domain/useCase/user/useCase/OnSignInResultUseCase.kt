package com.nedrysystems.eventorias.domain.useCase.user.useCase

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import javax.inject.Inject

class OnSignInResultUseCase @Inject constructor(private val repository: UserRepositoryInterface) {

    operator fun invoke(result: FirebaseAuthUIAuthenticationResult) {
        repository.onSignInResult(result)
    }
}