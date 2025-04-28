package com.nedrysystems.eventorias.ui.userProfileScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import com.nedrysystems.eventorias.utils.serviceInterface.FCMSubscriptionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val fcmSubscriptionManager: FCMSubscriptionManager,
    @ApplicationContext private val context: Context
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

                // Récupérer l'utilisateur
                val user = userUseCases.getCurrentUser()
                Log.d("UserProfileViewModel", "User retrieved: $user")

                // Récupérer l'état des notifications
                val notificationSetting = user?.let { userUseCases.getNotificationSetting(it.id) }
                Log.d("UserProfileViewModel", "Notification setting: $notificationSetting")

                // Mettre à jour l'UI state avec l'utilisateur et l'état des notifications
                val userUiModel = notificationSetting?.let { user.copy(asNotification = it) }
                Log.d("UserProfileViewModel", "User UI Model: $userUiModel")

                _uiState.value = notificationSetting?.let { UserProfileUiState(user = userUiModel, isLoading = false, asNotification = it) }!!
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading user", e)
                _uiState.value = UserProfileUiState(error = R.string.error_load_user)
            }
        }
    }
    fun toggleNotificationSetting() {
        viewModelScope.launch {
            val currentState = _uiState.value.asNotification
            val newState = !currentState
            val userId = _uiState.value.user?.id ?: return@launch

            try {
                userUseCases.setNotificationEnable(newState) // Tu continues d'envoyer au serveur

                // Abonner/désabonner FCM
                if (newState) {
                    fcmSubscriptionManager.subscribeToNotifications()
                } else {
                    fcmSubscriptionManager.unsubscribeFromNotifications()
                }

                // Sauvegarder localement l'état des notifications pour le onMessageReceived
                saveNotificationSettingLocally(newState)

                // Mettre à jour l'UI State
                _uiState.value = _uiState.value.copy(
                    asNotification = newState,
                    user = _uiState.value.user?.copy(asNotification = newState)
                )

                Log.d("UserProfileViewModel", "Notification setting updated: $newState")
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Erreur lors du changement de notification", e)
            }
        }

    }
    private fun saveNotificationSettingLocally(isEnabled: Boolean) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("notifications_enabled", isEnabled)
            .apply()
    }

}
