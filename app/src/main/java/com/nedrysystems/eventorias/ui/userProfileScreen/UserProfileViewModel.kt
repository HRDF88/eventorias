package com.nedrysystems.eventorias.ui.userProfileScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
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
                Log.d("UserProfileViewModel", "Loading user...")

                val user = userUseCases.getCurrentUser()
                Log.d("UserProfileViewModel", "User retrieved: $user")


                val userUiModel = user
                Log.d("UserProfileViewModel", "User UI Model: $userUiModel")


                _uiState.value = UserProfileUiState(user = userUiModel, isLoading = false)
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading user", e)
                _uiState.value = UserProfileUiState(error = R.string.error_load_user)
            }
        }
    }
}
