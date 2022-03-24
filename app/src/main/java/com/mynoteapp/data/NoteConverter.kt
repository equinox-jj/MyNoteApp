package com.mynoteapp.data

import androidx.room.TypeConverter
import com.mynoteapp.data.model.NotePriority

class NoteConverter {

    @TypeConverter
    fun fromPriority(priority: NotePriority): String { // to convert priority to string
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): NotePriority { // to convert string to priority
        return NotePriority.valueOf(priority)
    }

}