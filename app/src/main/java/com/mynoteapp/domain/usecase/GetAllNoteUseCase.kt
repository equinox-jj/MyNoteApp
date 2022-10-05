package com.mynoteapp.domain.usecase

import com.mynoteapp.data.model.NoteData
import com.mynoteapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    operator fun invoke(): Flow<List<NoteData>> = noteRepository.getAllNote()
}