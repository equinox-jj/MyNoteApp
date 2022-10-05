package com.mynoteapp.data.source.local.db

import androidx.room.TypeConverter
import com.mynoteapp.data.model.NotePriority

class NoteConverter {

    @TypeConverter
    fun fromPriority(notePriority: NotePriority): String {
        return notePriority.name
    }

    @TypeConverter
    fun toPriority(string: String): NotePriority {
        return NotePriority.valueOf(string)
    }

}