package com.example.eonet.domain.utils

import android.util.Log
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor() {
    fun formatDate(date:String?):String{

        if(date!=null){
            return try {
                // Parse the date-time string to ZonedDateTime
                val zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)

                // Define the output formatter for the human-readable format
                val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")

                // Format the ZonedDateTime to a human-readable string
                // Example output: August 30, 2024 19:00:00
                zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(outputFormatter)
            }catch (e:Exception){
                Log.w(javaClass.simpleName, "formatDate: ${e.message}")

                return date
            }
        } else{
            return "Not Available"
        }
    }
}