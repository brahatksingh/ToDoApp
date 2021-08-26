package com.brahatksingh.to_doapp.Data

import androidx.room.TypeConverter
import com.brahatksingh.to_doapp.Data.Models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority : Priority) : String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(temp : String) : Priority {
        return Priority.valueOf(temp)
    }
}