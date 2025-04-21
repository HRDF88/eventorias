package com.nedrysystems.eventorias

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.nedrysystems.eventorias.data.webService.firebase.MyFirebaseMessagingService
import com.nedrysystems.eventorias.ui.addScreen.AddScreen
import com.nedrysystems.eventorias.ui.authScreen.AuthScreen
import com.nedrysystems.eventorias.ui.eventListScreen.EventListScreen
import com.nedrysystems.eventorias.ui.homeScreen.HomeScreen
import com.nedrysystems.eventorias.ui.theme.EventoriasTheme
import com.nedrysystems.eventorias.ui.userProfileScreen.UserProfileScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var myFirebaseMessagingService: MyFirebaseMessagingService

    private val REQUEST_CODE_PERMISSIONS = 1001

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val response = IdpResponse.fromResultIntent(result.data)
                if (result.resultCode == RESULT_OK) {
                    val user = FirebaseAuth.getInstance().currentUser
                    Log.d("Auth", "Connexion réussie : ${user?.email}")
                } else {
                    if (response == null) {
                        Log.d("Auth", "Connexion annulée par l’utilisateur.")
                    } else {
                        Log.e("Auth", "Erreur de connexion", response.error)
                    }
                }
            }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                REQUEST_CODE_PERMISSIONS
            )
        }

        setContent {
            EventoriasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHostApp()
                }
            }
        }
        myFirebaseMessagingService.fireBaseMessaging.token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Token récupéré : $token")
                } else {
                    Log.e("FCM", "Erreur lors de la récupération du token", task.exception)
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, vous pouvez maintenant accéder aux images
                Log.d("Permissions", "Permission granted for READ_MEDIA_IMAGES")
            } else {
                // La permission a été refusée, vous ne pouvez pas accéder aux images
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun NavHostApp() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "login") {
                AuthScreen(navController = navController)
            }
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("event") {
                EventListScreen(
                    onFilterClick = { },
                    onSearchClick = { },
                    viewModel = hiltViewModel(),
                    navController = navController
                )

            }
            composable("profile") {
                UserProfileScreen()
            }

            composable(route = "add") {
                AddScreen(navController = navController, viewModel = hiltViewModel())
            }

        }
    }
}
