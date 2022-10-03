package com.mynoteapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.source.local.dao.NoteDao

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}