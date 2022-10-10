package com.mynoteapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val _getAllData = MutableLiveData<List<NoteData>>()
    val getAllData: LiveData<List<NoteData>> = _getAllData

    private val _searchNote = MutableLiveData<List<NoteData>>()
    val searchNote: LiveData<List<NoteData>> = _searchNote

    private var _sortByHighPriority = MutableLiveData<List<NoteData>>()
    val sortByHighPriority: LiveData<List<NoteData>> = _sortByHighPriority

    private var _sortByLowPriority = MutableLiveData<List<NoteData>>()
    val sortByLowPriority: LiveData<List<NoteData>> = _sortByLowPriority

    init {
        sortHighPriority()
        sortLowPriority()
    }

    private fun sortHighPriority() {
        viewModelScope.launch {
            useCase.sortByHighPriorityUseCase.invoke().collectLatest {
                _sortByHighPriority.value = it
            }
        }
    }

    private fun sortLowPriority() {
        viewModelScope.launch {
            useCase.sortByLowPriorityUseCase.invoke().collectLatest {
                _sortByLowPriority.value = it
            }
        }
    }

    fun getAllData() {
        viewModelScope.launch {
            useCase.getAllNoteUseCase.invoke().collect {
                _getAllData.postValue(it)
            }
        }
    }

    fun searchNote(query: String) {
        viewModelScope.launch {
            useCase.searchNoteUseCase.invoke(query).collect {
                _searchNote.postValue(it)
            }
        }
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.insertNoteUseCase.invoke(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.updateNoteUseCase.invoke(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.deleteNoteUseCase.invoke(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            useCase.deleteAllNoteUseCase.invoke()
        }
    }

}