package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

/**
 * A data class representing geographical coordinates with latitude and longitude.
 *
 * This class is used to represent a point on the Earth's surface using the [latitude] and
 * [longitude] properties. It also implements the [Serializable] interface, allowing it to be
 * serialized for storage or transmission.
 *
 * @property latitude The latitude of the coordinate. Defaults to 0.0.
 * @property longitude The longitude of the coordinate. Defaults to 0.0.
 *
 * @constructor Creates a new [Coordinate] object with the specified latitude and longitude values.
 *              If no values are provided, defaults to (1.3, 1.3).
 */
data class Coordinate(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Serializable {
    constructor() : this(1.3, 1.3)
}
