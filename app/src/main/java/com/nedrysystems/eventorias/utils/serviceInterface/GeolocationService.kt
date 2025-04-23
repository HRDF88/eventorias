package com.nedrysystems.eventorias.utils.serviceInterface

import com.nedrysystems.eventorias.domain.model.Coordinate

interface GeolocationService {
    suspend fun getCoordinatesFromAddress(address: String): Coordinate?
}