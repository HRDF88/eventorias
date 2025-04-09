package com.nedrysystems.eventorias.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.EmailSignInButton
import com.nedrysystems.eventorias.ui.component.GoogleSignInButton
import com.nedrysystems.eventorias.ui.theme.GrayEventorias

@Composable
fun AuthScreen() {

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
            GoogleSignInButton { }

            Spacer(modifier = Modifier.height(6.dp))

            EmailSignInButton { }
        }
    }
}

@Preview
@Composable
fun PreviewAuthScreen() {
    AuthScreen()
}