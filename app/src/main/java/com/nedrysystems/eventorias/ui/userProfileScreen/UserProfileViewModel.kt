package com.nedrysystems.eventorias.ui.userProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.toUiModel
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState(isLoading = true))
    val uiState: StateFlow<UserProfileUiState> = _uiState

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UserProfileUiState(isLoading = true)
            try {
                // Récupère l'utilisateur
                val user = userUseCases.getCurrentUser()

                // Transforme l'utilisateur en UserUiModel
                val userUiModel = user?.toUiModel()

                // Mets à jour l'état avec le UserUiModel
                _uiState.value = UserProfileUiState(user = userUiModel, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = UserProfileUiState(error = R.string.error_load_user)
            }
        }
    }
}
