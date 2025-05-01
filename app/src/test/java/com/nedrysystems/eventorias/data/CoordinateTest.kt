package com.nedrysystems.eventorias.data

import com.nedrysystems.eventorias.domain.model.Coordinate
import org.junit.Assert.*
import org.junit.Test

class CoordinateTest {


    @Test
    fun `should create Coordinate with custom values`() {
        val coordinate = Coordinate(latitude = 48.8566, longitude = 2.3522)

        assertEquals(48.8566, coordinate.latitude, 0.0001)
        assertEquals(2.3522, coordinate.longitude, 0.0001)
    }

    @Test
    fun `should compare two Coordinates for equality`() {
        val coordinate1 = Coordinate(48.8566, 2.3522)
        val coordinate2 = Coordinate(48.8566, 2.3522)
        val coordinate3 = Coordinate(51.5074, 0.1278)

        assertTrue(coordinate1 == coordinate2)

        assertFalse(coordinate1 == coordinate3)
    }

    @Test
    fun `should create a copy of Coordinate with modified values`() {
        val originalCoordinate = Coordinate(48.8566, 2.3522)

        val modifiedCoordinate = originalCoordinate.copy(latitude = 51.5074)

        assertEquals(51.5074, modifiedCoordinate.latitude, 0.0001)
        assertEquals(2.3522, modifiedCoordinate.longitude, 0.0001)

        assertEquals(48.8566, originalCoordinate.latitude, 0.0001)
        assertEquals(2.3522, originalCoordinate.longitude, 0.0001)
    }

    @Test
    fun `should return correct string representation of Coordinate`() {
        val coordinate = Coordinate(48.8566, 2.3522)

        assertEquals("Coordinate(latitude=48.8566, longitude=2.3522)", coordinate.toString())
    }
}
