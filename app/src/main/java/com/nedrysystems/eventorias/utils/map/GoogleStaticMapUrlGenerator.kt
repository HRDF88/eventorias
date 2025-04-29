package com.nedrysystems.eventorias.utils.map

import com.nedrysystems.eventorias.utils.serviceInterface.StaticMapUrlGenerator

/**
 * Generates a URL for a static map using Google Maps API.
 *
 * This class constructs a URL to retrieve a static map image from Google Maps based on the specified
 * latitude, longitude, zoom level, image size, and API key. The generated URL can be used to request
 * the static map image from the Google Maps service.
 *
 * @param apiKey The Google Maps API key used to authenticate requests to the API.
 */
class GoogleStaticMapUrlGenerator(
    private val apiKey: String
) : StaticMapUrlGenerator {

    /**
     * Generates a static map URL based on the given parameters.
     *
     * This function constructs a URL for the Google Maps Static Maps API that will generate a map
     * centered on the provided coordinates (latitude and longitude), with the specified zoom level and
     * image size. A marker is placed at the provided coordinates.
     *
     * @param latitude The latitude of the map center.
     * @param longitude The longitude of the map center.
     * @param zoom The zoom level for the map. Higher values zoom in closer to the location.
     * @param sizePx A `Pair` representing the width and height of the map image in pixels.
     * @return A string representing the URL to fetch the static map image from Google Maps API.
     */
    override fun generateUrl(
        latitude: Double,
        longitude: Double,
        zoom: Int,
        sizePx: Pair<Int, Int>
    ): String {
        val (widthPx, heightPx) = sizePx
        return buildString {
            append("https://maps.googleapis.com/maps/api/staticmap")
            append("?center=$latitude,$longitude")
            append("&zoom=$zoom")
            append("&size=${widthPx}x${heightPx}")
            append("&markers=color:red%7C$latitude,$longitude")
            append("&key=$apiKey")
        }
    }
}
