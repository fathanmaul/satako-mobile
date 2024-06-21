package dev.capstone.satako_mobile.utils

import dev.capstone.satako_mobile.utils.DateFormatter.formatIsoDate
import org.junit.Assert
import org.junit.Test

class DateFormatterTest {
    @Test
    fun `given correct ISO 8601 format then should format correctly`() {
        val currentDate = "2024-06-04T07:38:19.354Z"
        Assert.assertEquals("04 June 2024, 14:38", formatIsoDate(currentDate))
    }
}