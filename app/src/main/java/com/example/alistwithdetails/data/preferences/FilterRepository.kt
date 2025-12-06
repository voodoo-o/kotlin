package com.example.alistwithdetails.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Create the DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class FilterRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val LANGUAGE_FILTER = stringPreferencesKey("language_filter")
    }

    // Flow to read the saved language filter
    val languageFilter: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.LANGUAGE_FILTER] ?: ""
        }

    // Function to save the language filter
    suspend fun saveLanguageFilter(language: String) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.LANGUAGE_FILTER] = language
        }
    }
}
