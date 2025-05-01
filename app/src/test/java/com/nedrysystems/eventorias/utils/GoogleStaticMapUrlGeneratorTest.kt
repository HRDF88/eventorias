package com.nedrysystems.eventorias.utils

import com.nedrysystems.eventorias.utils.map.GoogleStaticMapUrlGenerator
import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test

class GoogleStaticMapUrlGeneratorTest {

    private val apiKey = "FAKE_API_KEY"
    private lateinit var urlGenerator: GoogleStaticMapUrlGenerator

    @Before
    fun setUp() {
        urlGenerator = GoogleStaticMapUrlGenerator(apiKey)
    }

    @Test
    fun `generateUrl returns correct Google Maps URL`() {

        val latitude = 48.8584
        val longitude = 2.2945
        val zoom = 15
        val sizePx = Pair(600, 400)


        val url = urlGenerator.generateUrl(latitude, longitude, zoom, sizePx)


        val expectedUrl = "https://maps.googleapis.com/maps/api/staticmap" +
                "?center=48.8584,2.2945" +
                "&zoom=15" +
                "&size=600x400" +
                "&markers=color:red%7C48.8584,2.2945" +
                "&key=$apiKey"

        assertEquals(expectedUrl, url)
    }
}
