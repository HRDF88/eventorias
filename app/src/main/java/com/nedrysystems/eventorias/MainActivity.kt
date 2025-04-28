package com.nedrysystems.eventorias

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.nedrysystems.eventorias.ui.addScreen.AddScreen
import com.nedrysystems.eventorias.ui.authScreen.AuthScreen
import com.nedrysystems.eventorias.ui.detailScreen.DetailScreen
import com.nedrysystems.eventorias.ui.eventListScreen.EventListScreen
import com.nedrysystems.eventorias.ui.homeScreen.HomeScreen
import com.nedrysystems.eventorias.ui.theme.EventoriasTheme
import com.nedrysystems.eventorias.ui.userProfileScreen.UserProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val REQUEST_CODE_READ_IMAGE = 1001
    private val REQUEST_CODE_POST_NOTIFS = 1002

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
                REQUEST_CODE_READ_IMAGE
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFS
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
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Token récupéré : $token")
                    // → ici tu peux appeler ta repo pour l'envoyer au serveur
                } else {
                    Log.e("FCM", "Erreur de récupération du token", task.exception)
                }
            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_IMAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permissions", "Permission accordée pour READ_MEDIA_IMAGES")
                } else {
                    Toast.makeText(this, "Permission READ_MEDIA_IMAGES refusée", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            REQUEST_CODE_POST_NOTIFS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permissions", "Permission accordée pour POST_NOTIFICATIONS")
                } else {
                    Toast.makeText(
                        this,
                        "Permission POST_NOTIFICATIONS refusée",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                        navController = navController,
                        viewModel = hiltViewModel()
                    )

                }
                composable("profile") {
                    UserProfileScreen()
                }

                composable(route = "add") {
                    AddScreen(navController = navController, viewModel = hiltViewModel())
                }

                composable(
                    route = "detail/{eventId}",
                    arguments = listOf(
                        navArgument("eventId") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    DetailScreen(navController = navController)
                }
            }

            composable(
                route = "detail/{eventId}",
                arguments = listOf(
                    navArgument("eventId") {
                        type = NavType.StringType
                    }
                )
            ) {
                DetailScreen(navController = navController)
            }
        }

    }
