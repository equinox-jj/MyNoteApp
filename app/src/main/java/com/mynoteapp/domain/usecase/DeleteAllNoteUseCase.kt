package com.mynoteapp.domain.usecase

import com.mynoteapp.domain.repository.INoteRepository
import javax.inject.Inject

class DeleteAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke() = noteRepository.deleteAllNote()
}