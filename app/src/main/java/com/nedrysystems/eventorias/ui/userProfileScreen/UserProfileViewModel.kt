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

/**
 * ViewModel responsible for managing the user profile screen UI state and user-related actions.
 *
 * This ViewModel handles loading the current user, managing notification settings, and updating the UI accordingly.
 *
 * @property userUseCases Use case class for interacting with user-related data.
 * @property fcmSubscriptionManager Manager for handling Firebase Cloud Messaging (FCM) subscription and unsubscription.
 * @property context Application context, used for accessing shared preferences to store user settings.
 */
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


    /**
     * Loads the current user and their notification setting, then updates the UI state.
     */
    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UserProfileUiState(isLoading = true)
            try {
                Log.d("UserProfileViewModel", "Loading user...")

                val user = userUseCases.getCurrentUser()
                Log.d("UserProfileViewModel", "User retrieved: $user")

                val notificationSetting = user?.let { userUseCases.getNotificationSetting(it.id) }
                Log.d("UserProfileViewModel", "Notification setting: $notificationSetting")

                val userUiModel = notificationSetting?.let { user.copy(asNotification = it) }
                Log.d("UserProfileViewModel", "User UI Model: $userUiModel")

                _uiState.value = notificationSetting?.let {
                    UserProfileUiState(
                        user = userUiModel,
                        isLoading = false,
                        asNotification = it
                    )
                }!!
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading user", e)
                _uiState.value = UserProfileUiState(error = R.string.error_load_user)
            }
        }
    }

    /**
     * Toggles the notification setting for the user and updates the UI and local storage.
     */
    fun toggleNotificationSetting() {
        viewModelScope.launch {
            val currentState = _uiState.value.asNotification
            val newState = !currentState
            val userId = _uiState.value.user?.id ?: return@launch

            try {
                userUseCases.setNotificationEnable(newState)

                if (newState) {
                    fcmSubscriptionManager.subscribeToNotifications()
                } else {
                    fcmSubscriptionManager.unsubscribeFromNotifications()
                }


                saveNotificationSettingLocally(newState)

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

    /**
     * Saves the notification setting locally using shared preferences.
     *
     * @param isEnabled The notification setting value to be saved.
     */
    private fun saveNotificationSettingLocally(isEnabled: Boolean) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("notifications_enabled", isEnabled)
            .apply()
    }

}
