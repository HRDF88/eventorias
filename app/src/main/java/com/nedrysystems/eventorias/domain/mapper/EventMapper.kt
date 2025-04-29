package com.nedrysystems.eventorias.domain.mapper

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel
import com.nedrysystems.eventorias.utils.image.Base64Converter
import com.nedrysystems.eventorias.utils.image.BitmapConverter
import com.nedrysystems.eventorias.utils.date.DateFormatter
import com.nedrysystems.eventorias.utils.serviceInterface.GeolocationService
import java.util.UUID
import javax.inject.Inject

/**
 * Converts an [Event] object into a Firestore-compatible Map.
 *
 * @return a [Map] representation of the event with keys matching Firestore field names.
 *
 * Fields include:
 * - "id": unique event identifier
 * - "tittle": the event title (note: "title" might be more appropriate)
 * - "description": event description
 * - "timestamp": the event's date and time in epoch milliseconds
 * - "picture": base64-encoded event image
 * - "adresse": textual address of the event
 * - "cordinateGps": a nested map with "latitude" and "longitude"
 * - "profilPicture": URL or base64 of the user's profile image
 */
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


/**
 * Converts a Firestore [DocumentSnapshot] into an [Event] object.
 *
 * @return the corresponding [Event] or null if the snapshot is empty or missing required fields.
 *
 * This function safely extracts:
 * - ID, title, description, timestamp, images, and GPS coordinates.
 */
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

/**
 * Maps an [Event] domain object into a [EventUiModel] for UI display.
 *
 * This includes:
 * - Formatting the timestamp into date and time strings
 * - Decoding the base64-encoded event image into a [Bitmap]
 * - Handling image decoding errors with a fallback transparent bitmap
 *
 * @return the corresponding [EventUiModel] used in the UI layer.
 */
fun Event.toUiModel(): EventUiModel {

    val dateFormatted = DateFormatter.formatDate(timestamp)
    val timeFormatted = DateFormatter.formatTime(timestamp)

    val eventBitmap = if (picture.isNotBlank()) {
        try {
            BitmapConverter.fromByteArray(Base64Converter.fromBase64(picture))
        } catch (e: Exception) {
            Log.e("Event", "Failed to decode event image", e)
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // fallback transparent
        }
    } else {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // fallback
    }

    return EventUiModel(
        id = id,
        title = tittle,
        description = description,
        formattedDate = dateFormatted,
        address = adresse,
        coordinateGps = cordinateGps,
        profileImage = profilPicture,
        eventImage = eventBitmap,
        timestamp = timestamp,
        formattedTime = timeFormatted

    )
}

class EventMapper @Inject constructor(private val geoService: GeolocationService) {

    /**
     * Maps form data to an [Event] object.
     *
     * This method is responsible for constructing an event based on:
     * - Date and hour string inputs
     * - Title, description, address, profile picture, and event picture
     * It also converts the event picture into a base64 string and fetches the GPS coordinates based on the address.
     *
     * @param date the date of the event in string format (e.g. "2025-04-29")
     * @param hour the hour of the event in string format (e.g. "15:30")
     * @param title the event title
     * @param description the event description
     * @param address the address of the event
     * @param profilPicture the profile picture of the event creator
     * @param eventPicture the event's image as a [Bitmap]
     * @return the newly created [Event] object
     *
     * @throws IllegalArgumentException if the input data is invalid or cannot be parsed.
     */
    suspend fun mapFormToEvent(
        date: String,
        hour: String,
        title: String,
        description: String,
        address: String,
        profilPicture: String,
        eventPicture: Bitmap?
    ): Event {
        val eventId = UUID.randomUUID().toString()
        val dateTime = "$date $hour"
        val timestamp = DateFormatter.parseDateTimeToTimestamp(dateTime)


        val eventPictureBase64 = eventPicture?.let {
            Base64Converter.toBase64(BitmapConverter.toByteArray(it))
        } ?: ""

        val gps = geoService.getCoordinatesFromAddress(address) ?: Coordinate(0.0, 0.0)

        return Event(
            id = eventId,
            tittle = title.trim(),
            description = description.trim(),
            timestamp = timestamp,
            picture = eventPictureBase64,
            adresse = address.trim(),
            cordinateGps = gps,
            profilPicture = profilPicture
        )
    }
}



