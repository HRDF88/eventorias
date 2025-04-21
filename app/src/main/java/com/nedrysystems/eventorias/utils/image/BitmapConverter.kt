package com.nedrysystems.eventorias.utils.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

/**
 * Utility class for converting Bitmap objects to byte arrays and vice versa.
 */
object BitmapConverter {

    /**
     * Default quality for bitmap compression, defined in SizeBitmapCONST.
     */
    const val qualityConvert = SizeBitmapCONST.quality

    /**
     * Converts a Bitmap into a ByteArray with a customizable format and quality.
     *
     * @param bitmap The Bitmap to convert.
     * @param format The compression format (default is PNG).
     * @param quality The quality of the compression (default is qualityConvert from SizeBitmapCONST).
     * @return A ByteArray representing the compressed bitmap.
     */
    fun toByteArray(
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        quality: Int = qualityConvert
    ): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(format, quality, outputStream)
        return outputStream.toByteArray()
    }

    /**
     * Converts a ByteArray into a Bitmap.
     *
     * @param byteArray The ByteArray containing the bitmap data.
     * @return The decoded Bitmap.
     */
    fun fromByteArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}