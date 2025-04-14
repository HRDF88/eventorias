package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

data class Coordinate(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Serializable {
    constructor() : this(1.3, 1.3)
}
