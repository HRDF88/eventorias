package com.nedrysystems.eventorias.utils.serviceInterface

import com.nedrysystems.eventorias.domain.model.Coordinate

/**
 * Interface for a service that retrieves geolocation coordinates.
 *
 * This interface defines the contract for services that provide functionality to fetch geographic
 * coordinates (latitude and longitude) based on a given address.
 * Implementations should handle the logic to translate an address into its corresponding coordinates.
 */
interface GeolocationService {

    /**
     * Retrieves the geographic coordinates for a given address.
     *
     * This suspend function takes an address as input and returns a [Coordinate] object containing
     * the latitude and longitude of the specified address. If the address cannot be found or
     * the coordinates cannot be retrieved, it returns `null`.
     *
     * @param address The address whose coordinates are to be fetched.
     * @return A [Coordinate] object with latitude and longitude, or `null` if the address cannot
     *         be resolved.
     */
    suspend fun getCoordinatesFromAddress(address: String): Coordinate?
}