package com.mynoteapp.data.source.local

import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.source.local.dao.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) : ILocalDataSource {

    override suspend fun getAllNote(): Flow<List<NoteData>> = noteDao.getAllData()
    override suspend fun insertNote(noteData: NoteData) = noteDao.insertData(noteData)
    override suspend fun updateNote(noteData: NoteData) = noteDao.updateData(noteData)
    override suspend fun deleteNote(noteData: NoteData) = noteDao.deleteData(noteData)
    override suspend fun deleteAllNote() = noteDao.deleteAll()
    override suspend fun searchNote(query: String): Flow<List<NoteData>> =
        noteDao.searchDatabase(query)

    override suspend fun sortByHighPriority(): Flow<List<NoteData>> = noteDao.sortByHighPriority()
    override suspend fun sortByLowPriority(): Flow<List<NoteData>> = noteDao.sortByLowPriority()

}