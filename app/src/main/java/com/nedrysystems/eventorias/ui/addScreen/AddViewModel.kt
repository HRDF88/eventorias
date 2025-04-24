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

@HiltViewModel
class AddViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    private val eventMapper: EventMapper,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState

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
                    eventUseCases.addEvent(event).collect { result ->
                        Log.d("AddViewModel", "Ajout terminé avec succès")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            success = true,
                            message = R.string.success_message
                        )
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


    fun loadUser() {
        viewModelScope.launch {
            try {
                Log.d("AddEventViewModel", "Loading user...")
                val user = userUseCases.getCurrentUser()
                Log.d("AddEventViewModel", "User loaded: $user")
                val userUiModel = user // tu peux le mapper ici si nécessaire
                _uiState.value = _uiState.value.copy(user = userUiModel)
            } catch (e: Exception) {
                Log.e("AddEventViewModel", "Error loading user", e)
                _uiState.value = _uiState.value.copy(loadUserError = R.string.error_load_user)

            }
        }
    }


    fun resetMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    fun resetLoadUserError() {
        _uiState.value = _uiState.value.copy(loadUserError = null)
    }
}
