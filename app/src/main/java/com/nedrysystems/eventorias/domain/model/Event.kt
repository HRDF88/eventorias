package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

/**
 * A data class representing an event with various details such as title, description, timestamp,
 * location, and associated media.
 *
 * This class is used to represent an event with information such as its ID, title, description,
 * timestamp, picture URL, address, and coordinates. It also includes the profile picture of the
 * event creator or organizer.
 *
 * @property id The unique identifier of the event.
 * @property tittle The title of the event.
 * @property description A brief description of the event.
 * @property timestamp The timestamp of the event (in milliseconds).
 * @property picture A base64-encoded string representing the picture of the event.
 * @property adresse The address where the event is held.
 * @property cordinateGps The geographical coordinates of the event. Defaults to null.
 * @property profilPicture The profile picture of the event creator/organizer.
 *
 * @constructor Creates a new [Event] object with the specified values. If no values are provided,
 *              defaults are used for certain properties (e.g., empty strings for `id`, `tittle`,
 *              `description`, and `adresse`, default `Coordinate` of (1.3, 1.3) for `cordinateGps`).
 */
data class Event(
    val id: String,
    val tittle: String,
    val description: String,
    val timestamp: Long,
    val picture: String,
    val adresse: String,
    var cordinateGps: Coordinate? = null,
    val profilPicture: String


) : Serializable {
    constructor() : this("", "", "", 1, "", "", Coordinate(1.3, 1.3), "")
}
