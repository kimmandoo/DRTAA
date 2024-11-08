package com.drtaa.core_model.rent

import java.lang.String.format
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class RentSchedule(
    val year: Int,
    val month: Int,
    val date: Int,
    val day: String,
    val hour: Int,
    val minute: Int
) {
    fun toStringTime(): String {
        return format(Locale.ROOT, "%02d:%02d", hour, minute)
    }

    fun toStringShortDate(): String {
        return format(Locale.ROOT, "%02d.%02d(%s)", month, date, day)
    }

    fun toStringDate(): String {
        return format(Locale.ROOT, "%04d.%02d.%02d(%s)", year, month, date, day)
    }

    fun toRequestUnassignedCar(): String {
        return format(Locale.ROOT, "%04d-%02d-%02d", year, month, date)
    }

    fun toDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - DIFF_MONTH, date, hour, minute)
        return calendar.time
    }

    override fun toString(): String {
        return format(
            Locale.ROOT,
            "%02d.%02d(%s) %02d:%02d",
            month,
            date,
            day,
            hour,
            minute
        )
    }

    companion object {
        const val DIFF_MONTH = 1
    }
}
