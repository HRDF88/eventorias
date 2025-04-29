package com.nedrysystems.eventorias.ui.addScreen

import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

/**
 * Data class representing the UI state for adding an event or user-related actions.
 *
 * This class holds the state information used by the UI to display the loading status, error messages,
 * success indicators, and other data related to adding an event or user.
 *
 * @property isLoading A Boolean indicating whether a loading operation is in progress. Default is false.
 * @property error An optional error code representing any error that occurred during the operation. Default is null.
 * @property event An optional [EventUiModel] representing the event that was added. Default is null.
 * @property success A Boolean indicating whether the operation was successful. Default is false.
 * @property message An optional message resource ID that could be used for displaying a message to the user. Default is null.
 * @property user An optional [User] object representing the user related to the action. Default is null.
 * @property loadUserError An optional error code representing any issue that occurred while loading user data. Default is null.
 */
data class AddUiState(
    val isLoading: Boolean = false,
    var error: Int? = null,
    val event: EventUiModel? = null,
    val success: Boolean = false,
    val message: Int? = null,
    val user: User? = null,
    val loadUserError: Int? = null,
)
