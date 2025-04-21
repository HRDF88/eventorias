package com.nedrysystems.eventorias.utils.image

import android.util.Base64

/**
 * Utility object for encoding and decoding Base64 strings.
 */
object Base64Converter {

    /**
     * Converts a ByteArray into a Base64-encoded string.
     *
     * @param byteArray The ByteArray to encode.
     * @return A Base64-encoded string representation of the byte array.
     */
    fun toBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * Decodes a Base64-encoded string into a ByteArray.
     *
     * @param base64String The Base64 string to decode.
     * @return A ByteArray representing the decoded data.
     */
    fun fromBase64(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }
}