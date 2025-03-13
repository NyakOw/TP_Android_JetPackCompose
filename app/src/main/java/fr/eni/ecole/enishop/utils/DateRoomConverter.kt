package fr.eni.ecole.enishop.utils

import androidx.room.TypeConverter
import java.util.Date

class DateRoomConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}