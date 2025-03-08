package com.suganth.multiselectdatepicker.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    const val DATE_ddMMyyyy = "dd/MM/yyyy"
    const val SELECTED_DATES = "dates"
    const val DATE_FORMAT_yyyyMMddHHmmssZZZZZ = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
    const val DOBString = "T00:00:00+00:00"
    const val DATE_FORMAT_yyyyMMdd = "yyyy-MM-dd"

    fun getDatePatternddMMyyyy() = SimpleDateFormat(DATE_ddMMyyyy, Locale.ENGLISH)

    fun getDateDDMMYYYY(): SimpleDateFormat {
        return SimpleDateFormat(DATE_ddMMyyyy, Locale.ENGLISH)
    }

    fun getDateString(
        time: Long,
        inputFormat: String? = null,
        outputFormat: String? = null
    ): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            inputFormat,
            Locale.ENGLISH
        )
        return format.format(date).let {
            if (outputFormat == DATE_FORMAT_yyyyMMddHHmmssZZZZZ) {
                "${format.format(date)}$DOBString"
            } else
                format.format(date)
        }
    }
}