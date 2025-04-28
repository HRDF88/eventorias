package com.nedrysystems.eventorias.ui.detailScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.ErrorComposable
import com.nedrysystems.eventorias.ui.component.StaticMap
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
                                contentDescription = null,
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
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    uiState.event != null -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(GrayEventoriasBackground)
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp)

                        ) {
                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(event.eventImage)
                                    .crossfade(true)
                                    .error(R.drawable.error)
                                    .build()
                            )
                            Image(
                                painter = painter,
                                contentDescription = "profilePictureTextContentDescription",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(400.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .semantics {
                                        contentDescription = "profilePictureTextContentDescription"
                                    }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.event),
                                            contentDescription = null,
                                            tint = Color.White
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(
                                            text = uiState.event!!.formattedDate,
                                            color = Color.White,
                                            fontSize = 18.sp
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.schedule),
                                            contentDescription = null,
                                            tint = Color.White
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(
                                            text = uiState.event!!.formattedTime,
                                            color = Color.White,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                                val painterProfile = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(event.profileImage)
                                        .crossfade(true)
                                        .error(R.drawable.error)
                                        .build()
                                )
                                Image(
                                    painter = painterProfile,
                                    contentDescription = "profilePictureTextContentDescription",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .size(55.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.Gray, CircleShape)
                                        .semantics {
                                            contentDescription =
                                                "profilePictureTextContentDescription"
                                        }


                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = uiState.event!!.description,
                                color = Color.White

                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {

                                Text(
                                    text = uiState.event!!.address,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                )

                                event.coordinateGps?.let {
                                    StaticMap(
                                        latitude = it.latitude,
                                        longitude = event.coordinateGps.longitude,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(100.dp)
                                            .width(200.dp)

                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        ErrorComposable { viewModel.retry() }
    }
}
