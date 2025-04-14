package com.nedrysystems.eventorias.domain.useCase.user.useCase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.domain.model.User
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(private val repository: UserRepositoryInterface) {

    suspend operator fun invoke(launcher: ActivityResultLauncher<Intent>): User? {
        return repository.signIn(launcher)

    }

}
