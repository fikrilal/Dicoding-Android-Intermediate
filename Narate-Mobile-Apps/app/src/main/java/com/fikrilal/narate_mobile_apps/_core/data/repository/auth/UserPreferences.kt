package com.fikrilal.narate_mobile_apps._core.data.repository.auth

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.IOException
import javax.inject.Inject

class UserPreferences @Inject constructor(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")

        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_LANGUAGE = stringPreferencesKey("user_language")
    }

    val userToken: Flow<String?> = getPreferenceFlow(USER_TOKEN)
    val userId: Flow<String?> = getPreferenceFlow(USER_ID)
    val userName: Flow<String?> = getPreferenceFlow(USER_NAME)
    val userLanguage: Flow<String?> = getPreferenceFlow(USER_LANGUAGE)

    private fun <T> getPreferenceFlow(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key]
            }
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(5000), null)
    }

    val userTokenState = userToken.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    suspend fun getUserToken(): String? {
        val token = getUserPreference(USER_TOKEN)
        return token
    }

    suspend fun getUserLanguage(): String? = getUserPreference(USER_LANGUAGE)

    private suspend fun <T> getUserPreference(key: Preferences.Key<T>): T? {
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }

    suspend fun saveUserLanguage(language: String) {
        savePreference(USER_LANGUAGE, language)
    }

    suspend fun saveUserDetails(token: String, userId: String, userName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
            preferences[USER_ID] = userId
            preferences[USER_NAME] = userName
//            Log.d("UserPreferences", "Saved user details: Token = $token, UserId = $userId, UserName = $userName")
        }
    }

    suspend fun clearUserDetails() {
        context.dataStore.edit { preferences ->
            preferences.clear()
//            Log.d("UserPreferences", "Cleared user details")
        }
    }

    private suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
//            Log.d("UserPreferences", "Saved preference for key: $key, value: $value")
        }
    }
}