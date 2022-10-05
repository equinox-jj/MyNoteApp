package com.mynoteapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

//    private val _getAllNote = MutableStateFlow<>()
//    val getAllNote: StateFlow<> = _getAllNote
//
//    private val _insertNote = MutableStateFlow<>()
//    val insertNote: StateFlow<> = _insertNote
//
//    private val _updateNote = MutableStateFlow<>()
//    val updateNote: StateFlow<> = _updateNote
//
//    private val _deleteNote = MutableStateFlow<>()
//    val deleteNote: StateFlow<> = _deleteNote
//
//    private val _deleteAllNote = MutableStateFlow<>()
//    val deleteAllNote: StateFlow<> = _deleteAllNote
//
//    private val _searchNote = MutableStateFlow<>()
//    val searchNote: StateFlow<> = _searchNote
//
//    private var _sortByHighPriority = MutableStateFlow<>()
//    val sortByHighPriority: StateFlow<> = _sortByHighPriority
//
//    private var _sortByLowPriority = MutableStateFlow<>()
//    val sortByLowPriority: StateFlow<> = _sortByLowPriority

    init {
        viewModelScope.launch {
//            _sortByLowPriority = sortByHighPriorityUseCase.invoke()
//            _sortByHighPriority = sortByLowPriorityUseCase.invoke()
        }
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
//            noteRepository.insertData(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
//            noteRepository.updateData(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
//            noteRepository.deleteData(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
//            noteRepository.deleteAll()
        }
    }

//    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
//        return noteRepository.searchDatabase(searchQuery)
//    }

}