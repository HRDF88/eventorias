package com.nedrysystems.eventorias.data.repository

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.data.webService.serviceInterface.UserApi
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) : UserRepositoryInterface {


    private var signInDeferred: CompletableDeferred<User?>? = null

    override fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val user = userApi.getCurrentUser()
            signInDeferred?.complete(user)
        } else {
            signInDeferred?.complete(null)
        }
    }

    override suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User? {
        if (signInDeferred != null && !signInDeferred!!.isCompleted) {
            throw IllegalStateException("Sign-in already in progress.")
        }

        signInDeferred = CompletableDeferred()

        // Lance l'intent de connexion
        userApi.signIn(launcher)

        return signInDeferred?.await() // Attends le résultat de la connexion
    }

    override fun signOut() {
        userApi.signOut()
    }

    override fun getCurrentUser(): User? {
        return userApi.getCurrentUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return userApi.isUserLoggedIn()
    }

    override fun setNotificationEnable(enable: Boolean) {
        userApi.setNotificationEnable(enable)
    }

    override fun insertCurrentUser() {
        userApi.insertCurrentUser()
    }

    override fun loadUser(): Flow<User> {
        return userApi.loadUser()
    }

    override suspend fun getNotificationSetting(userId: String): Boolean{
        return userApi.getNotificationSetting(userId)
    }
}
