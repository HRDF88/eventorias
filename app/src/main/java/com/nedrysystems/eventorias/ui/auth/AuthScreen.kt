package com.nedrysystems.eventorias.ui.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.EmailSignInButton
import com.nedrysystems.eventorias.ui.component.GoogleSignInButton
import com.nedrysystems.eventorias.ui.theme.GrayEventorias

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract()
    ) { result ->
        viewModel.onSignInResult(result)
    }

    val context = LocalContext.current

    val errorResId = uiState.errorResId

    LaunchedEffect(errorResId) {
        errorResId?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = GrayEventorias)
            .padding(6.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_eventorias),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)

        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 70.dp)
                .fillMaxWidth()
        ) {
            GoogleSignInButton {
                viewModel.launchSignIn(launcher)
            }

            Spacer(modifier = Modifier.height(6.dp))

            EmailSignInButton {
                viewModel.launchSignIn(launcher)
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }

        }
    }
}

    @Preview
    @Composable
    fun PreviewAuthScreen() {
        AuthScreen()
    }