package com.nedrysystems.eventorias.utils

import com.nedrysystems.eventorias.utils.date.DateFormatter
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DateFormatterTest {



    @Test
    fun `isValidDate should return true for valid date in MM dd yyyy format`() {
        val validDate = "12/31/2022"

        assertTrue(DateFormatter.isValidDate(validDate))
    }

    @Test
    fun `isValidDate should return false for invalid date format`() {
        val invalidDate = "31/12/2022"

        assertFalse(DateFormatter.isValidDate(invalidDate))
    }

    }
