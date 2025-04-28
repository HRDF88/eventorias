package com.nedrysystems.eventorias.ui.addScreen

import android.graphics.Bitmap
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.PhotoPickerComposable
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground
import com.nedrysystems.eventorias.ui.theme.GraysEventoriasField
import com.nedrysystems.eventorias.utils.date.DateFormatter
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first


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
    val addMessage = eventState.message?.let {
        stringResource(id = it)
    } ?: ""

    val loadUserErrorMessage = eventState.loadUserError?.let {
        stringResource(id = it)
    } ?: ""

    val context = LocalContext.current

    //Textfield customization
    val textFieldPattern = TextFieldDefaults.colors(
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
    val textColor = Color.White
    val textErrorColor = Color.Red

    //States
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var hour by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var eventImage by remember { mutableStateOf<Bitmap?>(null) }


    //Error States
    var isTitleError by remember { mutableStateOf(false) }
    var isDescriptionError by remember { mutableStateOf(false) }
    var isDateError by remember { mutableStateOf(false) }
    var isHourError by remember { mutableStateOf(false) }
    var isAddressError by remember { mutableStateOf(false) }

    //Error Empty Message
    val errorEmptyTitle = stringResource(R.string.error_empty_title)
    val errorEmptyDescription = stringResource(R.string.error_empty_description)
    val errorEmptyDate = stringResource(R.string.error_empty_date)
    val errorEmptyHour = stringResource(R.string.error_empty_hour)
    val errorEmptyAddress = stringResource(R.string.error_empty_address)


    //Error Message
    var titleErrorMessage by remember { mutableStateOf("") }
    var descriptionErrorMessage by remember { mutableStateOf("") }
    var dateErrorMessage by remember { mutableStateOf("") }
    var hourErrorMessage by remember { mutableStateOf("") }
    var addressErrorMessage by remember { mutableStateOf("") }
    val errorInvalidDate = stringResource(R.string.error_invalid_date)
    val errorInvalidHour = stringResource(R.string.error_invalid_hour)


    //Field pattern
    val datePattern = Regex("""^(0[1-9]|1[0-2])/([0][1-9]|[12][0-9]|3[01])/([0-9]{4})$""")
    val hourPattern = Regex("""^([01][0-9]|2[0-3]):[0-5][0-9]$""")


    //Trigger
    var triggerAddEvent by remember { mutableStateOf(false) }



    SideEffect {
        if (eventState.error != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetMessage()
        }

        if (eventState.loadUserError != null) {
            Toast.makeText(context, loadUserErrorMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetLoadUserError()
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
            verticalArrangement = Arrangement.Top
        ) {

            TextField(
                value = title,
                onValueChange = {
                    title = it
                    when {
                        title.text.isEmpty() -> {
                            isTitleError = true
                            titleErrorMessage = errorEmptyTitle
                        }

                        else -> {
                            isTitleError = false
                            titleErrorMessage = ""
                        }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.title),
                        color = textColor
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.new_event),
                        color = textColor
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .padding(14.dp),


                colors = textFieldPattern
            )
            if (isTitleError) {
                Text(
                    text = titleErrorMessage,
                    color = textErrorColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }



            TextField(
                value = description,
                onValueChange = {
                    description = it
                    when {
                        description.text.isEmpty() -> {
                            isDescriptionError = true
                            descriptionErrorMessage = errorEmptyDescription
                        }

                        else -> {
                            isDescriptionError = false
                            descriptionErrorMessage = ""
                        }
                    }
                }, label = {
                    Text(
                        text = stringResource(R.string.description),
                        color = textColor
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.label_description),
                        color = textColor
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(14.dp),

                colors = textFieldPattern
            )
            if (isDescriptionError) {
                Text(
                    text = descriptionErrorMessage,
                    color = textErrorColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                TextField(
                    value = date,
                    onValueChange = {
                        date = it
                        val isFormatCorrect = date.text.matches(datePattern)
                        val isValidCalendarDate = DateFormatter.isValidDate(date.text)

                        when {
                            date.text.isEmpty() -> {
                                isDateError = true
                                dateErrorMessage = errorEmptyDate
                            }

                            !isFormatCorrect || !isValidCalendarDate -> {
                                isDateError = true
                                dateErrorMessage = errorInvalidDate
                            }

                            else -> {
                                isDateError = false
                                dateErrorMessage = ""
                            }
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.date),
                            color = textColor
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_date),
                            color = textColor
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(14.dp),

                    colors = textFieldPattern
                )
                if (isDateError) {
                    Text(
                        text = dateErrorMessage,
                        color = textErrorColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                TextField(
                    value = hour,
                    onValueChange = {
                        hour = it

                        when {
                            hour.text.isEmpty() -> {
                                isHourError = true
                                hourErrorMessage = errorEmptyHour
                            }

                            !hour.text.matches(hourPattern) -> {
                                isHourError = true
                                hourErrorMessage = errorInvalidHour
                            }

                            else -> {
                                isHourError = false
                                hourErrorMessage = ""
                            }
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.time),
                            color = textColor
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_time),
                            color = textColor
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(14.dp),

                    colors = textFieldPattern
                )

            }
            if (isHourError) {
                Text(
                    text = hourErrorMessage,
                    color = textErrorColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            TextField(
                value = address,
                onValueChange = {
                    address = it
                    when {
                        address.text.isEmpty() -> {
                            isAddressError = true
                            addressErrorMessage = errorEmptyAddress
                        }

                        else -> {
                            isAddressError = false
                            addressErrorMessage = ""
                        }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.address),
                        color = textColor
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.label_address),
                        color = textColor
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(14.dp),

                colors = textFieldPattern
            )
            if (isAddressError) {
                Text(
                    text = addressErrorMessage,
                    color = textErrorColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            PhotoPickerComposable(imageBitmap = eventImage) { eventImage = it }


            Spacer(modifier = Modifier.height(60.dp))

            val isFormValid = title.text.isNotBlank() && !isTitleError &&
                    description.text.isNotBlank() && !isDescriptionError &&
                    date.text.isNotBlank() && !isDateError &&
                    hour.text.isNotBlank() && !isHourError &&
                    address.text.isNotBlank() && !isAddressError && eventImage != null

            Button(
                onClick = { triggerAddEvent = true },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                Text(text = stringResource(R.string.validate))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }
    LaunchedEffect(triggerAddEvent) {
        if (triggerAddEvent) {
            triggerAddEvent = false


            snapshotFlow { viewModel.uiState.value.user }
                .filterNotNull()
                .first()
                .let {
                    viewModel.submitEventForm(
                        date = date.text,
                        hour = hour.text,
                        title = title.text,
                        description = description.text,
                        address = address.text,
                        eventPicture = eventImage
                    )
                }

            viewModel.uiState
                .filter { it.success || it.message != null }
                .first()
                .let { state ->
                    if (state.success) {
                        navController.popBackStack()
                    } else if (state.message != null) {
                        Toast.makeText(context, addMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    viewModel.resetMessage()
                }


        }
    }
}



