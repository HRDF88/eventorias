package com.nedrysystems.eventorias.data.webService.firebase

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.data.webService.serviceInterface.AuthApi
import kotlinx.coroutines.CompletableDeferred


class FirebaseAuthService : AuthApi {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var signInDeferred: CompletableDeferred<FirebaseUser?>? = null


    override fun onSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val user = auth.currentUser
            signInDeferred?.complete(user)
        } else {
            signInDeferred?.complete(null)
        }
    }

    override suspend fun signIn(launcher: ActivityResultLauncher<Intent>): FirebaseUser? {
        if (signInDeferred != null && !signInDeferred!!.isCompleted) {
            throw IllegalStateException("Sign-in already in progress.")
        }

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInDeferred = CompletableDeferred()
        launcher.launch(intent)
        return signInDeferred?.await()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
