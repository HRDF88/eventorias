package com.nedrysystems.eventorias.utils.geoLocation

import android.content.Context
import android.location.Geocoder
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.utils.serviceInterface.GeolocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class AndroidGeolocationService(private val context: Context) : GeolocationService {
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
