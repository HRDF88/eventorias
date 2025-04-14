package com.nedrysystems.eventorias.data.webService.firebase

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.nedrysystems.eventorias.data.webService.serviceInterface.UserApi
import com.nedrysystems.eventorias.domain.mapper.toDomainUser
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FirebaseUserService : UserApi {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var signInDeferred: CompletableDeferred<FirebaseUser?>? = null

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")


    override fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val user = auth.currentUser
            signInDeferred?.complete(user)
        } else {
            signInDeferred?.complete(null)
        }
    }

    override suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User? {
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

        val firebaseUser = signInDeferred?.await()
        return firebaseUser?.toDomainUser()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): User? {
        return auth.currentUser?.toDomainUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun insertCurrentUser() {
        val firebaseUser = auth.currentUser ?: return

        val user = firebaseUser.toDomainUser()

        usersCollection.document(user.id).set(user)
    }

    override fun setNotificationEnable(enable: Boolean) {
        val uid = auth.currentUser?.uid ?: return

        usersCollection.document(uid)
            .update("asNotification", enable)
    }

    override fun loadUser(): Flow<User> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            close()
            return@callbackFlow
        }

        val listener = usersCollection.document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) {
                    close(error)
                    return@addSnapshotListener
                }

                val user = snapshot.toObject(User::class.java)
                if (user != null) trySend(user)
            }

        awaitClose { listener.remove() }
    }
}
