package com.nedrysystems.eventorias.utils.serviceInterface

interface StaticMapUrlGenerator {
    fun generateUrl(
        latitude: Double,
        longitude: Double,
        zoom: Int = 17,
        sizePx: Pair<Int, Int> = 800 to 400
    ): String
}