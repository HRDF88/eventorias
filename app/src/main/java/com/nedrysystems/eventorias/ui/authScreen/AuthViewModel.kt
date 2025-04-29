package com.nedrysystems.eventorias.ui.authScreen


import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.R
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

/**
 * ViewModel responsible for handling user authentication state and logic.
 *
 * This ViewModel interacts with the domain layer (via [UserUseCases]) to perform operations
 * such as signing in, processing sign-in results, and initializing the UI state based on
 * the current user session.
 *
 * @property userUseCases A container of all user-related use cases injected via Hilt.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var signInDeferred: CompletableDeferred<User?>? = null

    // Initialize the UI state with the currently signed-in user, if available
    init {
        val currentUser = userUseCases.getCurrentUser()
        _uiState.update { it.copy(user = currentUser, isSignedIn = currentUser != null) }
    }

    /**
     * Handles the result returned by FirebaseUI after the authentication flow.
     *
     * @param result The [FirebaseAuthUIAuthenticationResult] returned from FirebaseUI.
     */
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

    /**
     * Launches the FirebaseUI sign-in flow.
     *
     * @param launcher The [ActivityResultLauncher] to start the FirebaseUI activity.
     */
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
                            errorResId = null
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
                        errorResId = R.string.email_sign_in
                    )
                }
            }
        }
    }
}
