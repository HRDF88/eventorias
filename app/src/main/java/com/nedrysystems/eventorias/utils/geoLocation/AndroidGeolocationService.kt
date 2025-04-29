package com.nedrysystems.eventorias.utils.geoLocation

import android.content.Context
import android.location.Geocoder
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.utils.serviceInterface.GeolocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Service for retrieving geolocation coordinates from a given address.
 *
 * This implementation uses Android's `Geocoder` class to perform address lookups and obtain the corresponding
 * geographical coordinates (latitude and longitude). It operates asynchronously on an IO thread to prevent
 * blocking the main thread.
 *
 * @param context The application context used to access geolocation services.
 */
class AndroidGeolocationService(private val context: Context) : GeolocationService {

    /**
     * Retrieves the geographical coordinates (latitude and longitude) for a given address.
     *
     * This function uses the `Geocoder` class to resolve the address into a location, returning the latitude
     * and longitude as a `Coordinate` object. If the address cannot be resolved or if an error occurs, it returns `null`.
     *
     * @param address The address for which to retrieve the coordinates.
     * @return A `Coordinate` object containing the latitude and longitude, or `null` if the address cannot be resolved.
     */
    override suspend fun getCoordinatesFromAddress(address: String): Coordinate? {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val results = geocoder.getFromLocationName(address, 1)
                results?.firstOrNull()?.let {
                    Coordinate(it.latitude, it.longitude)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
