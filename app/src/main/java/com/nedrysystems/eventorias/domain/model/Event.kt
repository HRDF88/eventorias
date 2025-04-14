package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

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
