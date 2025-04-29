package com.nedrysystems.eventorias.ui.detailScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.ErrorComposable
import com.nedrysystems.eventorias.ui.component.EventDetailContent
import com.nedrysystems.eventorias.ui.component.LoadingEventorias
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event = uiState.event

    val context = LocalContext.current

    val errorMessage = uiState.error?.let {
        stringResource(id = it)
    } ?: ""

    val backButton = stringResource(R.string.back_button)

    SideEffect {
        if (uiState.error != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetMessage()
        }
    }
    if (event != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = event.title, color = Color.White)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = backButton,
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GrayEventoriasBackground
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(GrayEventoriasBackground)
            ) {
                when {
                    uiState.isLoading -> {
                        LoadingEventorias()
                    }

                    uiState.event != null -> {
                        EventDetailContent(event = event)
                    }

                    uiState.event == null -> {
                        ErrorComposable { viewModel.retry() }
                    }
                }
            }
        }
    }
}
