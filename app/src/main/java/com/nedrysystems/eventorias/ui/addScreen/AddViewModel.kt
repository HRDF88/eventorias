package com.nedrysystems.eventorias.ui.addScreen

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.EventMapper
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for handling event creation logic and managing UI state during the process.
 *
 * This ViewModel coordinates the user interaction with event-related use cases and handles
 * the mapping of event data. It manages the UI state for event submission, including loading states,
 * success or failure messages, and user data.
 *
 * @property eventUseCases The [EventUseCases] instance for interacting with event-related use cases.
 * @property eventMapper The [EventMapper] instance for mapping form data to an [Event] object.
 * @property userUseCases The [UserUseCases] instance for interacting with user-related use cases.
 * @property _uiState A mutable state flow that holds the UI state for the event creation process.
 * @property uiState A public, immutable state flow for observing the UI state.
 */
@HiltViewModel
class AddViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    private val eventMapper: EventMapper,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState

    /**
     * Submits the event form by mapping the form data to an [Event] and triggering the event creation.
     *
     * This function is responsible for:
     * - Mapping the form data to an [Event] object.
     * - Checking if the user's profile picture is available.
     * - Calling the use case to add the event and updating the UI state accordingly.
     *
     * @param date The date of the event.
     * @param hour The hour of the event.
     * @param title The title of the event.
     * @param description The description of the event.
     * @param address The address of the event.
     * @param eventPicture An optional [Bitmap] representing the event's picture.
     */
    fun submitEventForm(
        date: String,
        hour: String,
        title: String,
        description: String,
        address: String,
        eventPicture: Bitmap?
    ) {
        viewModelScope.launch {
            Log.d("AddViewModel", "submitEventForm: Début")

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val profilPicture = _uiState.value.user?.profilPicture
                if (profilPicture == null) {
                    Log.w("AddViewModel", "submitEventForm: profilPicture est null, mapping annulé")
                }

                val event = profilPicture?.let {
                    Log.d("AddViewModel", "Mapping de l'event en cours…")
                    eventMapper.mapFormToEvent(
                        date = date,
                        hour = hour,
                        title = title,
                        description = description,
                        address = address,
                        profilPicture = it,
                        eventPicture = eventPicture
                    )
                }

                if (event != null) {
                    Log.d("AddViewModel", "Event mappé avec succès : $event")
                    eventUseCases.addEvent(event).collect {
                        Log.d("AddViewModel", "Ajout terminé avec succès")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            success = true,
                            message = R.string.success_message
                        )
                        Log.d("AddViewModel", "Message après succès : ${_uiState.value.message}")
                    }
                } else {
                    Log.w("AddViewModel", "submitEventForm: Event null après mapping")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = R.string.success_false_message
                    )
                }
            } catch (e: Exception) {
                Log.e("AddViewModel", "Erreur lors de l'ajout de l'event", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    success = false,
                    message = R.string.success_false_message
                )
            }
        }
    }

    /**
     * Loads the current user from the user use cases.
     *
     * This function retrieves the current user and updates the UI state accordingly. If there
     * is an error loading the user, it updates the UI state to indicate the error.
     */
    fun loadUser() {
        viewModelScope.launch {
            try {
                Log.d("AddEventViewModel", "Loading user...")
                val user = userUseCases.getCurrentUser()
                Log.d("AddEventViewModel", "User loaded: $user")
                val userUiModel = user
                _uiState.value = _uiState.value.copy(user = userUiModel)
            } catch (e: Exception) {
                Log.e("AddEventViewModel", "Error loading user", e)
                _uiState.value = _uiState.value.copy(loadUserError = R.string.error_load_user)

            }
        }
    }

    /**
     * Resets the success or failure message in the UI state.
     *
     * This function is useful to clear any displayed message after it has been shown to the user.
     */
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    /**
     * Resets the load user error in the UI state.
     *
     * This function is used to clear the load user error message after resolving the issue.
     */
    fun resetLoadUserError() {
        _uiState.value = _uiState.value.copy(loadUserError = null)
    }
}
