package com.nedrysystems.eventorias.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun formatDate(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        val pattern = when (locale.language) {
            "fr" -> "dd MMM yyyy"
            else -> "MMM dd yyyy"
        }

        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }
}
