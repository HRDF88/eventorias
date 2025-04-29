package com.nedrysystems.eventorias.utils.date

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.Date
import java.util.Locale

/**
 * Utility object for formatting, parsing, and validating date and time.
 *
 * This object provides functions for formatting timestamps into human-readable date and time strings,
 * as well as validating and parsing date strings. It supports different formats based on the locale and allows
 * easy conversion between date/time representations and Unix timestamps.
 */
object DateFormatter {

    /**
     * Formats a timestamp into a date string.
     *
     * The date format varies based on the locale. For French (`fr`), the format is `dd, MMMM, yyyy`. For other languages,
     * it uses the format `MMMM, dd, yyyy`.
     *
     * @param timestamp The timestamp to format, in milliseconds since the Unix epoch.
     * @param locale The locale to use for formatting. Defaults to the system's default locale.
     * @return A formatted date string based on the timestamp and locale.
     */
    fun formatDate(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        val pattern = when (locale.language) {
            "fr" -> "dd, MMMM, yyyy"
            else -> "MMMM, dd, yyyy"
        }

        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }

    /**
     * Formats a timestamp into a time string.
     *
     * The time format varies based on the locale. For French (`fr`), the format is `HH:mm`. For other languages,
     * it uses the format `h:mm a` (12-hour format with AM/PM).
     *
     * @param timestamp The timestamp to format, in milliseconds since the Unix epoch.
     * @param locale The locale to use for formatting. Defaults to the system's default locale.
     * @return A formatted time string based on the timestamp and locale.
     */
    fun formatTime(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        val pattern = when (locale.language) {
            "fr" -> "HH:mm"
            else -> "h:mm a"
        }
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }

    /**
     * Validates whether a date string is in the format `MM/dd/yyyy`.
     *
     * This function checks whether the provided date string adheres to the `MM/dd/yyyy` format using strict parsing rules.
     *
     * @param dateStr The date string to validate.
     * @return `true` if the date string is valid, `false` otherwise.
     */
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

    /**
     * Parses a date-time string into a Unix timestamp.
     *
     * This function parses a date-time string in the format `MM/dd/yyyy HH:mm` and converts it to a timestamp
     * in milliseconds since the Unix epoch.
     *
     * @param dateTimeStr The date-time string to parse, in the format `MM/dd/yyyy HH:mm`.
     * @return The timestamp in milliseconds since the Unix epoch, or `0L` if parsing fails.
     */
    fun parseDateTimeToTimestamp(dateTimeStr: String): Long {
        return try {
            val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            val date = formatter.parse(dateTimeStr)
            date?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}
