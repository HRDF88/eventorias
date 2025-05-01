package com.nedrysystems.eventorias.utils

import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import android.util.Base64
import com.nedrysystems.eventorias.utils.image.Base64Converter

class Base64ConverterTest {

    @Test
    fun `test toBase64 should encode ByteArray correctly`() {

        val mockBase64 = mockStatic(Base64::class.java)
        val input = "Hello, World!".toByteArray()
        val expectedBase64 = "SGVsbG8sIFdvcmxkIQ=="

        mockBase64.`when`<Any> { Base64.encodeToString(input, Base64.DEFAULT) }.thenReturn(expectedBase64)

        val base64String = Base64Converter.toBase64(input)

        assertEquals(expectedBase64, base64String)

        mockBase64.close()
    }

    @Test
    fun `test fromBase64 should decode Base64 string correctly`() {

        val mockBase64 = mockStatic(Base64::class.java)
        val base64String = "SGVsbG8sIFdvcmxkIQ=="
        val expectedByteArray = "Hello, World!".toByteArray()

        mockBase64.`when`<Any> { Base64.decode(base64String, Base64.DEFAULT) }.thenReturn(expectedByteArray)

        val decodedByteArray = Base64Converter.fromBase64(base64String)

        assertArrayEquals(expectedByteArray, decodedByteArray)

        mockBase64.close()
    }

    @Test
    fun `test fromBase64 to toBase64 round-trip`() {
        val originalByteArray = "Test string for Base64 encoding!".toByteArray()


        val mockBase64 = mockStatic(Base64::class.java)
        val base64String = "VGVzdCBzdHJpbmcgZm9yIEJhc2U2NCBlbmNvZGluZyE="

        mockBase64.`when`<Any> { Base64.encodeToString(originalByteArray, Base64.DEFAULT) }.thenReturn(base64String)
        mockBase64.`when`<Any> { Base64.decode(base64String, Base64.DEFAULT) }.thenReturn(originalByteArray)


        val encodedBase64 = Base64Converter.toBase64(originalByteArray)

        val decodedByteArray = Base64Converter.fromBase64(encodedBase64)

        assertArrayEquals(originalByteArray, decodedByteArray)

        mockBase64.close()
    }
}

