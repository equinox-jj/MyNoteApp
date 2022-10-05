package com.mynoteapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val _getAllNote = MutableLiveData<>()
    val getAllNote: LiveData<> = _getAllNote

    private val _insertNote = MutableLiveData<NoteData>()
    val insertNote: LiveData<NoteData>
        get() = _insertNote

    private val _updateNote = MutableLiveData<>()
    val updateNote: LiveData<> = _updateNote

    private val _deleteNote = MutableLiveData<>()
    val deleteNote: LiveData<> = _deleteNote

    private val _deleteAllNote = MutableLiveData<>()
    val deleteAllNote: LiveData<> = _deleteAllNote

    private val _searchNote = MutableLiveData<>()
    val searchNote: LiveData<> = _searchNote

    private var _sortByHighPriority = MutableLiveData<>()
    val sortByHighPriority: LiveData<> = _sortByHighPriority

    private var _sortByLowPriority = MutableLiveData<>()
    val sortByLowPriority: LiveData<> = _sortByLowPriority

    private var _test = MutableStateFlow<NoteData>()
    val test: StateFlow<NoteData> = _test

    init {
        viewModelScope.launch {
//            _sortByLowPriority = sortByHighPriorityUseCase.invoke()
//            _sortByHighPriority = sortByLowPriorityUseCase.invoke()
        }
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertNoteUseCase.invoke(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateNoteUseCase.invoke(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteNoteUseCase.invoke(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteAllNoteUseCase.invoke()
        }
    }

    fun searchDatabase(searchQuery: String) {
        viewModelScope.launch {
            useCase.searchNoteUseCase.invoke(searchQuery)
        }
    }

    fun testing() {
        viewModelScope.launch {
            _test.emit()
        }
    }
}