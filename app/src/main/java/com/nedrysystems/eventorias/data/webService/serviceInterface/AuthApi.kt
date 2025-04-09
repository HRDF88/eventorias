package com.nedrysystems.eventorias.data.webService.serviceInterface

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseUser

interface AuthApi {
    fun onSignInResult(result: ActivityResult)
    suspend fun signIn(launcher: ActivityResultLauncher<Intent>) : FirebaseUser?
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean
}
