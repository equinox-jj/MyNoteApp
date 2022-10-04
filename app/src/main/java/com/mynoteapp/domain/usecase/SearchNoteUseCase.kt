package com.mynoteapp.domain.usecase

import com.mynoteapp.domain.repository.INoteRepository
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke(query: String) = noteRepository.searchNote(query)
}