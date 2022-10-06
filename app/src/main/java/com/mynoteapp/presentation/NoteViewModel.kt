package com.mynoteapp.presentation

import androidx.lifecycle.*
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    val search: MediatorLiveData<List<NoteData>> = MediatorLiveData()

    private var _sortByHighPriority = MutableLiveData<List<NoteData>>()
    val sortByHighPriority: LiveData<List<NoteData>> = _sortByHighPriority

    private var _sortByLowPriority = MutableLiveData<List<NoteData>>()
    val sortByLowPriority: LiveData<List<NoteData>> = _sortByLowPriority

    init {
        viewModelScope.launch {
            useCase.sortByLowPriorityUseCase.invoke().collect {
                _sortByHighPriority.postValue(it)
            }
            useCase.sortByLowPriorityUseCase.invoke().collect {
                _sortByLowPriority.postValue(it)
            }
        }
    }

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllNoteUseCase.invoke().collect {
                _getAllData.postValue(it)
            }
        }
    }

    fun searchNote(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchNoteUseCase.invoke(query).collect {
                _searchNote.postValue(it)
            }
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

}