package com.nedrysystems.eventorias.ui.uiModel

import android.graphics.Bitmap
import com.nedrysystems.eventorias.domain.model.Coordinate

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