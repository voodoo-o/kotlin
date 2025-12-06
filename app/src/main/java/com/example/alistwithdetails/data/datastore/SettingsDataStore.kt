package com.example.alistwithdetails.data.datastore

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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val GITHUB_USER_KEY = stringPreferencesKey("github_user")
    }

    val selectedUser: Flow<String> = context.dataStore.data.map {
        preferences -> preferences[PreferencesKeys.GITHUB_USER_KEY] ?: "google"
    }

    suspend fun saveSelectedUser(user: String) {
        context.dataStore.edit {
            preferences -> preferences[PreferencesKeys.GITHUB_USER_KEY] = user
        }
    }
}