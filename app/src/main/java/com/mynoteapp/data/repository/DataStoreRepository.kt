package com.mynoteapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mynoteapp.common.Constants.DS_NAME
import com.mynoteapp.common.Constants.PRIORITY_PREF_NAME
import com.mynoteapp.data.model.NotePriority
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DS_NAME)

@ViewModelScoped
class DataStoreRepository(@ApplicationContext context: Context) {

    companion object {
        val priorityPrefKey = stringPreferencesKey(PRIORITY_PREF_NAME)
    }

    private val dataStore = context.dataStore

    suspend fun setPriority(priority: NotePriority) {
        dataStore.edit { pref ->
            pref[priorityPrefKey] = priority.name
        }
    }

    val getPriority: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { pref ->
            pref[priorityPrefKey] ?: NotePriority.NONE.name
        }
}