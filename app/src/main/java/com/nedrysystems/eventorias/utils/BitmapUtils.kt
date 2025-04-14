package com.nedrysystems.eventorias.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri

/**
 * Utility object for various Bitmap operations such as conversion, resizing, and rotation.
 */
object BitmapUtils {

    /**
     * Converts a URI to a Bitmap.
     *
     * @param context The application context.
     * @param uri The URI of the image to convert.
     * @return The decoded Bitmap, or null if an error occurs.
     */
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    /**
     * Creates a white Bitmap with the specified width and height.
     *
     * @param width The width of the Bitmap.
     * @param height The height of the Bitmap.
     * @return A Bitmap filled with white color.
     */
    fun create(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            val canvas = Canvas(this)
            canvas.drawColor(Color.WHITE)
        }
    }

    /**
     * Resizes a Bitmap while maintaining its aspect ratio.
     *
     * @param bitmap The original Bitmap to resize.
     * @param maxWidth The maximum width allowed.
     * @param maxHeight The maximum height allowed.
     * @return The resized Bitmap.
     */
    fun resize(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
        var newWidth = maxWidth
        var newHeight = (newWidth / aspectRatio).toInt()

        if (newHeight > maxHeight) {
            newHeight = maxHeight
            newWidth = (newHeight * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    /**
     * Rotates a Bitmap by a specified number of degrees.
     *
     * @param bitmap The original Bitmap to rotate.
     * @param degrees The number of degrees to rotate the Bitmap.
     * @return The rotated Bitmap.
     */
    fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
