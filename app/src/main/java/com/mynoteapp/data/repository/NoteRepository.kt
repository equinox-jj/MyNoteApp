package com.mynoteapp.data.repository

import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.source.local.dao.NoteDao
import com.mynoteapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) : INoteRepository {

    override fun getAllNote(): Flow<List<NoteData>> = noteDao.getAllData()
    override fun searchNote(query: String): Flow<List<NoteData>> = noteDao.searchDatabase(query)
    override fun sortByHighPriority(): Flow<List<NoteData>> = noteDao.sortByHighPriority()
    override fun sortByLowPriority(): Flow<List<NoteData>> = noteDao.sortByLowPriority()
    override suspend fun insertNote(noteData: NoteData) = noteDao.insertData(noteData)
    override suspend fun updateNote(noteData: NoteData) = noteDao.updateData(noteData)
    override suspend fun deleteNote(noteData: NoteData) = noteDao.deleteData(noteData)
    override suspend fun deleteAllNote() = noteDao.deleteAll()

}