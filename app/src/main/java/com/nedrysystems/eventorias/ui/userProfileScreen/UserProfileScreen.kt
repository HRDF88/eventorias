package com.nedrysystems.eventorias.ui.userProfileScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.ErrorComposable
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userState by viewModel.uiState.collectAsState()
    val user = remember(userState) { userState.user }
    val errorMessage = userState.error?.let {
        stringResource(id = it)
    } ?: ""
    val context = LocalContext.current

    if (userState.isLoading) {
        CircularProgressIndicator()
    }

    SideEffect {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    if (user == null) {
        ErrorComposable(onTryAgainClick = {})
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = stringResource(R.string.user_profile_tittle))


                        if (user != null) {
                            Image(
                                user.profileImage.asImageBitmap(),
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                                    .semantics { contentDescription = "" }
                            )
                        }
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GrayEventoriasBackground,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrayEventoriasBackground)
        ) {

            if (user != null) {
                OutlinedTextField(
                    value = user.name,
                    onValueChange = {},
                    label = { Text(text = stringResource(R.string.name)) },
                    readOnly = true,
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            if (user != null) {
                OutlinedTextField(
                    value = user.email,
                    onValueChange = {},
                    label = { Text(text = stringResource(R.string.email)) },
                    readOnly = true,
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    UserProfileScreen()
}

