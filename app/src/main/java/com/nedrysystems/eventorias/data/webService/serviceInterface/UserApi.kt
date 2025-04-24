package com.nedrysystems.eventorias.data.webService.serviceInterface

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserApi {
    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult)
    suspend fun signIn(launcher: ActivityResultLauncher<Intent>) : User?
    fun signOut()
    fun getCurrentUser(): User?
    fun isUserLoggedIn(): Boolean
    fun setNotificationEnable(enable : Boolean)
    fun insertCurrentUser()
    fun loadUser() : Flow<User>
    suspend fun getNotificationSetting(userId: String): Boolean
}
