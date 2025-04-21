package com.nedrysystems.eventorias.ui.authScreen


import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.toDomainUser
import com.nedrysystems.eventorias.domain.mapper.toUiModel
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var signInDeferred: CompletableDeferred<User?>? = null

    init {
        val currentUser = userUseCases.getCurrentUser()
        _uiState.update { it.copy(user = currentUser, isSignedIn = currentUser != null) }
    }

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        userUseCases.onSignInResult(result)

        val firebaseUser = userUseCases.getCurrentUser()

        _uiState.update {
            if (firebaseUser != null) {
                it.copy(
                    user = firebaseUser,
                    isLoading = false,
                    isSignedIn = true,
                    errorResId = null
                )
            } else {
                it.copy(
                    isLoading = false,
                    errorResId = R.string.email_sign_in,
                    isSignedIn = false
                )
            }
        }
    }

    fun signIn(launcher: ActivityResultLauncher<Intent>) {
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, errorResId = null) }

        viewModelScope.launch {
            signInDeferred = CompletableDeferred()
            try {
                val user = userUseCases.signIn(launcher)
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            user = user,
                            isLoading = false,
                            isSignedIn = true,
                            errorResId =  null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSignedIn = false,
                            errorResId = R.string.email_sign_in
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorResId =  R.string.email_sign_in
                    )
                }
            }
        }
    }
}
