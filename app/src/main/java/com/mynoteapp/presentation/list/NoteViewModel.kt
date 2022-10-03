package com.mynoteapp.presentation.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.repository.NoteRepository
import com.mynoteapp.data.source.local.db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    private val noteRepository: NoteRepository = NoteRepository(noteDao)
    val getAllData: LiveData<List<NoteData>> = noteRepository.getAllData
    val sortByHighPriority: LiveData<List<NoteData>>
    val sortByLowPriority: LiveData<List<NoteData>>

    init {
        sortByHighPriority = noteRepository.sortByHighPriority
        sortByLowPriority = noteRepository.sortByLowPriority
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertData(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateData(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteData(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
        return noteRepository.searchDatabase(searchQuery)
    }

}