package com.nedrysystems.eventorias.domain.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel
import com.nedrysystems.eventorias.utils.image.Base64Converter
import com.nedrysystems.eventorias.utils.image.BitmapConverter
import com.nedrysystems.eventorias.utils.DateFormatter

fun Event.toDomain(): Event {
    return Event(
        id = id,
        tittle = tittle,
        description = description,
        timestamp = timestamp,
        picture = picture,
        adresse = adresse,
        cordinateGps = Coordinate(longitude = 0.0, latitude = 0.0),
        profilPicture = profilPicture
    )
}

fun Event.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "tittle" to tittle,
        "description" to description,
        "timestamp" to timestamp,
        "picture" to picture,
        "adresse" to adresse,
        "cordinateGps" to mapOf(
            "latitude" to (cordinateGps?.latitude ?: 0.0),
            "longitude" to (cordinateGps?.longitude ?: 0.0)
        ),
        "profilPicture" to profilPicture
    )

}

fun DocumentSnapshot.toEvent(): Event? {
    val data = this.data ?: return null
    val gps = data["cordinateGps"] as? Map<*, *>
    val latitude = gps?.get("latitude") as? Double ?: 0.0
    val longitude = gps?.get("longitude") as? Double ?: 0.0

    return Event(
        id = data["id"] as? String ?: "",
        tittle = data["tittle"] as? String ?: "",
        description = data["description"] as? String ?: "",
        timestamp = data["timestamp"] as? Long ?: 0,
        picture = data["picture"] as? String ?: "",
        adresse = data["adresse"] as? String ?: "",
        cordinateGps = Coordinate(latitude, longitude),
        profilPicture = data["profilPicture"] as? String ?: ""
    )
}

fun Event.toUiModel(): EventUiModel {

    val dateFormatted = DateFormatter.formatDate(timestamp)

    val profileBitmap = BitmapConverter.fromByteArray(Base64Converter.fromBase64(profilPicture))
    val eventBitmap = BitmapConverter.fromByteArray(Base64Converter.fromBase64(picture))

    return EventUiModel(
        id = id,
        title = tittle,
        description = description,
        formattedDate = dateFormatted,
        address = adresse,
        coordinateGps = cordinateGps,
        profileImage = profileBitmap,
        eventImage = eventBitmap
    )
}


