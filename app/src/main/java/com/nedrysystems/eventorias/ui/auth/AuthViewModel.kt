package com.nedrysystems.eventorias.ui.auth

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.data.webService.serviceInterface.AuthApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authService: AuthApi) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(authService.getCurrentUser())
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()

    fun onSignInResult(result: ActivityResult) {
        authService.onSignInResult(result)
    }

    fun signOut() {
        authService.signOut()
        _user.value = null
    }

    fun launchSignIn(launcher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            val user = authService.signIn(launcher)
            _user.value = user
        }
    }
}
