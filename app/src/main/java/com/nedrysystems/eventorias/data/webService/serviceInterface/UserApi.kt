package com.nedrysystems.eventorias.data.webService.serviceInterface

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser

interface AuthApi {
    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult)
    suspend fun signIn(launcher: ActivityResultLauncher<Intent>) : FirebaseUser?
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean
}
