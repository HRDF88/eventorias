package com.nedrysystems.eventorias.utils.map

import com.nedrysystems.eventorias.utils.serviceInterface.StaticMapUrlGenerator

class GoogleStaticMapUrlGenerator(
    private val apiKey: String
) : StaticMapUrlGenerator {

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
