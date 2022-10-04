package com.mynoteapp.domain.usecase

import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.repository.INoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke(noteData: NoteData) = noteRepository.insertNote(noteData)
}