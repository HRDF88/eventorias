package com.nedrysystems.eventorias.data.webService.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.nedrysystems.eventorias.data.webService.serviceInterface.UserApi
import com.nedrysystems.eventorias.domain.mapper.toDomainUser
import com.nedrysystems.eventorias.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [UserApi] that integrates Firebase Authentication and Firestore.
 *
 * This class handles user sign-in, sign-out, user data management, and notification preferences
 * using Firebase services.
 */
class FirebaseUserService : UserApi {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var signInDeferred: CompletableDeferred<FirebaseUser?>? = null

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("User")

    /**
     * Callback for handling the result of the sign-in intent.
     *
     * @param result The result from the Firebase Auth UI.
     */
    override fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        Log.d("AuthUI", "onSignInResult called with resultCode: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val user = auth.currentUser
            Log.d("AuthUI", "User signed in: ${user?.email}")
            signInDeferred?.complete(user)
        } else {
            Log.d("AuthUI", "Sign-in failed or cancelled")
            signInDeferred?.complete(null)
        }
    }

    /**
     * Launches the sign-in process using the given activity result launcher.
     *
     * @param launcher An [ActivityResultLauncher] used to launch the Firebase Auth UI.
     * @return The signed-in [User], or null if the sign-in was cancelled or failed.
     */
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

    /**
     * Signs the current user out of Firebase.
     */
    override fun signOut() {
        auth.signOut()
    }

    /**
     * Returns the currently signed-in [User], or null if not signed in.
     */
    override fun getCurrentUser(): User? {
        return auth.currentUser?.toDomainUser()
    }

    /**
     * Checks whether a user is currently signed in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Inserts the current signed-in user into Firestore if they do not already exist.
     */
    override fun insertCurrentUser() {
        val firebaseUser = auth.currentUser ?: return

        val user = firebaseUser.toDomainUser()


        usersCollection.document(user.id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.exists()) {

                    usersCollection.document(user.id).set(user)
                        .addOnSuccessListener {
                            Log.d("FirebaseUserService", "User created: ${user.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebaseUserService", "Error creating user", e)
                        }
                }
            } else {
                Log.e("FirebaseUserService", "Error checking document existence", task.exception)
            }
        }
    }

    /**
     * Enables or disables notifications for the current user.
     *
     * @param enable `true` to enable notifications, `false` to disable them.
     */
    override fun setNotificationEnable(enable: Boolean) {
        val uid = auth.currentUser?.uid ?: return

        val userRef = usersCollection.document(uid)

        userRef.set(mapOf("asNotification" to enable), SetOptions.merge())
            .addOnSuccessListener {
                Log.d("FirebaseUserService", "Notification setting updated: $enable")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseUserService", "Error updating notification setting", e)
            }
    }

    /**
     * Loads and observes the current user's data from Firestore.
     *
     * @return A [Flow] that emits updates to the current user's data.
     */
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

    /**
     * Retrieves the notification preference of the specified user.
     *
     * @param userId The ID of the user.
     * @return `true` if notifications are enabled, `false` otherwise. Defaults to `true` if the field is missing.
     */
    override suspend fun getNotificationSetting(userId: String): Boolean {
        val userRef = usersCollection.document(userId)
        val snapshot = userRef.get().await()

        return snapshot.getBoolean("asNotification")
            ?: true
    }
}



