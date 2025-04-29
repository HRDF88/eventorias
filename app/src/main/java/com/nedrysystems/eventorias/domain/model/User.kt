package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

/**
 * A data class representing a user with personal information and notification settings.
 *
 * This class contains details about a user such as their unique ID, name, email, profile picture,
 * and their notification preferences.
 *
 * @property id The unique identifier of the user.
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property profilPicture The URL or path of the user's profile picture.
 * @property asNotification Indicates whether the user has enabled notifications (true by default).
 *
 * @constructor Creates a new [User] object with the specified values. If no values are provided,
 *              defaults are used for certain properties (e.g., empty strings for `id`, `name`,
 *              `email`, and `profilPicture`, and true for `asNotification`).
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilPicture: String,
    val asNotification: Boolean
) : Serializable {
    constructor() : this("", "", "", "", true)
}
