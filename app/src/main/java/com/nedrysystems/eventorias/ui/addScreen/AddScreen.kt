package com.nedrysystems.eventorias.ui.addScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.PhotoPickerComposable
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground
import com.nedrysystems.eventorias.ui.theme.GraysEventoriasField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    viewModel: AddViewModel
) {
    val eventState by viewModel.uiState.collectAsState()
    val errorMessage = eventState.error?.let {
        stringResource(id = it)
    } ?: ""
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }



    SideEffect {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.add_tittle),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GrayEventoriasBackground
                ),

                )
        }
    ) { innerPadding ->
        if (eventState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayEventoriasBackground),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Column(
            modifier = Modifier
                .background(GrayEventoriasBackground)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(
                        text = stringResource(R.string.title),
                        color = Color.White
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.new_event),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .padding(14.dp),


                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GraysEventoriasField,
                    unfocusedContainerColor = GraysEventoriasField,
                    disabledContainerColor = GraysEventoriasField,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                )
            )


            TextField(
                value = description,
                onValueChange = {
                    description = it
                },  label = {
                    Text(
                        text = stringResource(R.string.description),
                        color = Color.White
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.label_description),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(14.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GraysEventoriasField,
                    unfocusedContainerColor = GraysEventoriasField,
                    disabledContainerColor = GraysEventoriasField,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                )
            )

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                TextField(
                    value = date,
                    onValueChange = {
                        date = it
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.date),
                            color = Color.White
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_date),
                            color = Color.White
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(14.dp),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = GraysEventoriasField,
                        unfocusedContainerColor = GraysEventoriasField,
                        disabledContainerColor = GraysEventoriasField,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedPlaceholderColor = Color.White,
                        unfocusedPlaceholderColor = Color.White
                    )
                )

                TextField(
                    value = hour,
                    onValueChange = {
                        hour = it
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.time),
                            color = Color.White
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_time),
                            color = Color.White
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(14.dp),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = GraysEventoriasField,
                        unfocusedContainerColor = GraysEventoriasField,
                        disabledContainerColor = GraysEventoriasField,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedPlaceholderColor = Color.White,
                        unfocusedPlaceholderColor = Color.White
                    )
                )

            }

            TextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.address),
                        color = Color.White
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.label_address),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(14.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GraysEventoriasField,
                    unfocusedContainerColor = GraysEventoriasField,
                    disabledContainerColor = GraysEventoriasField,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                )
            )

            PhotoPickerComposable(imageBitmap = null) { }


            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.validate))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    val navController = rememberNavController()
    AddScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

