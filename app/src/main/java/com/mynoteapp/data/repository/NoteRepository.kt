package com.mynoteapp.data.repository

import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.source.local.ILocalDataSource
import com.mynoteapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
) : INoteRepository {

    //    val getAllData: LiveData<List<NoteData>> = noteDao.getAllData()
//    val sortByHighPriority: LiveData<List<NoteData>> = noteDao.sortByHighPriority()
//    val sortByLowPriority: LiveData<List<NoteData>> = noteDao.sortByLowPriority()
//
//    suspend fun insertData(noteData: NoteData) {
//        noteDao.insertData(noteData)
//    }
//
//    suspend fun updateData(noteData: NoteData) {
//        noteDao.updateData(noteData)
//    }
//
//    suspend fun deleteData(noteData: NoteData) {
//        noteDao.deleteData(noteData)
//    }
//
//    suspend fun deleteAll() {
//        noteDao.deleteAll()
//    }
//
//    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
//        return noteDao.searchDatabase(searchQuery)
//    }

    override suspend fun getAllNote(): Flow<List<NoteData>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(noteData: NoteData) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(noteData: NoteData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteData: NoteData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNote() {
        TODO("Not yet implemented")
    }

    override suspend fun searchNote(query: String): Flow<List<NoteData>> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByHighPriority(): Flow<List<NoteData>> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByLowPriority(): Flow<List<NoteData>> {
        TODO("Not yet implemented")
    }

}