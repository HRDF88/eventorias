package com.nedrysystems.eventorias.utils.date

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
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

    fun isValidDate(dateStr: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu")
                .withResolverStyle(ResolverStyle.STRICT)
            LocalDate.parse(dateStr, formatter)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Pour convertir une chaîne de date + heure en timestamp
    fun parseDateTimeToTimestamp(dateTimeStr: String): Long {
        return try {
            val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault()) // Format de date+heure
            val date = formatter.parse(dateTimeStr)
            date?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}
