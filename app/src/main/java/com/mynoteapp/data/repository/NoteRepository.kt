package com.mynoteapp.data.repository

import androidx.lifecycle.LiveData
import com.mynoteapp.data.NoteDao
import com.mynoteapp.data.model.NoteData

class NoteRepository(private val noteDao: NoteDao) {

    val getAllData: LiveData<List<NoteData>> = noteDao.getAllData()
    val sortByHighPriority: LiveData<List<NoteData>> = noteDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<NoteData>> = noteDao.sortByLowPriority()

    suspend fun insertData(noteData: NoteData) {
        noteDao.insertData(noteData)
    }

    suspend fun updateData(noteData: NoteData) {
        noteDao.updateData(noteData)
    }

    suspend fun deleteData(noteData: NoteData) {
        noteDao.deleteData(noteData)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
        return noteDao.searchDatabase(searchQuery)
    }

}