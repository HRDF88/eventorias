package com.nedrysystems.eventorias.ui.uiModel

import android.graphics.Bitmap
import com.nedrysystems.eventorias.domain.model.Coordinate

/**
 * UI representation of an event, used to display event data in the user interface layer.
 *
 * This model contains both raw and formatted data suitable for direct consumption by the UI.
 *
 * @property id Unique identifier of the event.
 * @property title Title of the event.
 * @property description Description providing details about the event.
 * @property formattedDate Human-readable date string (e.g., "April 29, 2025").
 * @property address Address where the event takes place.
 * @property coordinateGps Optional GPS coordinates of the event location.
 * @property profileImage URL or encoded string of the user’s profile image.
 * @property eventImage Bitmap representing the event image to display in the UI.
 * @property timestamp Unix timestamp of the event (used for sorting or filtering).
 * @property formattedTime Human-readable time string (e.g., "14:30").
 */
data class EventUiModel(
    val id: String,
    val title: String,
    val description: String,
    val formattedDate: String,
    val address: String,
    val coordinateGps: Coordinate?,
    val profileImage: String,
    val eventImage: Bitmap,
    val timestamp: Long,
    val formattedTime: String
)