package com.nedrysystems.eventorias.ui.authScreen

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState(user = userUseCases.getCurrentUser()))
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        userUseCases.onSignInResult(result)
    }


    fun launchSignIn(launcher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorResId = null)
            try {
                val user = userUseCases.signIn(launcher)
                if (user != null) {
                    _uiState.value = AuthUiState(user = user)
                } else {
                    _uiState.value = AuthUiState(errorResId = R.string.auth_error_connection)
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState(errorResId = R.string.auth_error_connection)
            }
        }
    }
}