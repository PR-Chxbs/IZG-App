package com.prince.izg.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "izg_prefs"

private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreManager(
    private val context: Context
) {

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.AUTH_TOKEN] = token
        }
    }

    fun getAuthToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.AUTH_TOKEN]
        }
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(DataStoreKeys.AUTH_TOKEN)
        }
    }
}
