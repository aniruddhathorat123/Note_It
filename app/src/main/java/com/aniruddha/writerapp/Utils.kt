package com.aniruddha.writerapp

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        /**
         * Returns the file name created from date and time.
         */
        fun getFileName() : String {
            val customFormat = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault())
            return "Note " + customFormat.format(Date())
//            LocalDateTime.now().toString()
//            val calendar = Calendar.getInstance()
//            Calendar.getInstance().time
//
//            val current = LocalDateTime.of(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                calendar.get(Calendar.SECOND)
//            )
//            return current.toString();
        }

        /**
         *
         */
        fun getDateString() : String {
            val customFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return customFormat.format(Date())
        }
    }
}