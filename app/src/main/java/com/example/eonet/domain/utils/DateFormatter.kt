package com.example.eonet.domain.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor() {
    fun formatDate(date:String):String{

        // Parse the date-time string to ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Define the output formatter for the human-readable format
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")

        // Format the ZonedDateTime to a human-readable string
        // Example output: August 30, 2024 19:00:00
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(outputFormatter)

    }
}