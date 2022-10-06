package com.mynoteapp.di

import android.content.Context
import androidx.room.Room
import com.mynoteapp.common.Constants.NOTE_DB_NAME
import com.mynoteapp.data.repository.NoteRepository
import com.mynoteapp.data.source.local.dao.NoteDao
import com.mynoteapp.data.source.local.db.NoteDatabase
import com.mynoteapp.domain.repository.INoteRepository
import com.mynoteapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTE_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providesNoteDao(database: NoteDatabase) = database.noteDao()

    @Singleton
    @Provides
    fun providesRepository(noteDao: NoteDao): INoteRepository = NoteRepository(noteDao)

    @Singleton
    @Provides
    fun providesUseCase(repository: INoteRepository): UseCase {
        return UseCase(
            getAllNoteUseCase = GetAllNoteUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            searchNoteUseCase = SearchNoteUseCase(repository),
            deleteAllNoteUseCase = DeleteAllNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            updateNoteUseCase = UpdateNoteUseCase(repository),
            sortByHighPriorityUseCase = SortByHighPriorityUseCase(repository),
            sortByLowPriorityUseCase = SortByLowPriorityUseCase(repository)
        )
    }

}