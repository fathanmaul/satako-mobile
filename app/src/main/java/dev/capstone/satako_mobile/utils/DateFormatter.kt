package dev.capstone.satako_mobile.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun formatIsoDate(isoDateString: String): String {
        val indonesianLocale = Locale("en", "ID")
        val indonesianTimeZone = "Asia/Jakarta" // Time zone for Western Indonesia Time (WIB)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
            val localDateTime = LocalDateTime.parse(isoDateString, isoFormatter)
            val zonedDateTime = localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(indonesianTimeZone))
            val readableFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", indonesianLocale)
            zonedDateTime.format(readableFormatter)
        } else {
            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = isoFormatter.parse(isoDateString)!!
            val readableFormatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", indonesianLocale)
            readableFormatter.timeZone = TimeZone.getTimeZone(indonesianTimeZone)
            readableFormatter.format(date)
        }
    }
}