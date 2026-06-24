package com.example.mobile.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "nsync_auth_session",
)

class AuthSessionStore(context: Context) {
    private val appContext = context.applicationContext

    private companion object {
        val AccessTokenKey = stringPreferencesKey("access_token")
        val RefreshTokenKey = stringPreferencesKey("refresh_token")
        val UserIdKey = longPreferencesKey("user_id")
        val DisplayNameKey = stringPreferencesKey("display_name")
        val EmailKey = stringPreferencesKey("email")
    }

    private val preferencesFlow: Flow<Preferences> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    val accessTokenFlow: Flow<String?> = preferencesFlow.map { preferences ->
        preferences[AccessTokenKey]
    }

    val refreshTokenFlow: Flow<String?> = preferencesFlow.map { preferences ->
        preferences[RefreshTokenKey]
    }

    val sessionFlow: Flow<AuthSession?> = preferencesFlow.map { preferences ->
        val accessToken = preferences[AccessTokenKey]
        val refreshToken = preferences[RefreshTokenKey]
        val userId = preferences[UserIdKey]
        val displayName = preferences[DisplayNameKey]
        val email = preferences[EmailKey]

        if (
            accessToken != null && refreshToken != null && userId != null &&
            displayName != null && email != null
        ) {
            AuthSession(accessToken, refreshToken, userId, displayName, email)
        } else {
            null
        }
    }

    val isSignedIn: Flow<Boolean> = sessionFlow.map { it != null }

    suspend fun saveSession(session: AuthSession) {
        appContext.dataStore.edit { preferences ->
            preferences[AccessTokenKey] = session.accessToken
            preferences[RefreshTokenKey] = session.refreshToken
            preferences[UserIdKey] = session.userId
            preferences[DisplayNameKey] = session.displayName
            preferences[EmailKey] = session.email
        }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[AccessTokenKey] = accessToken
            preferences[RefreshTokenKey] = refreshToken
        }
    }

    suspend fun clearSession() {
        appContext.dataStore.edit { preferences ->
            preferences.remove(AccessTokenKey)
            preferences.remove(RefreshTokenKey)
            preferences.remove(UserIdKey)
            preferences.remove(DisplayNameKey)
            preferences.remove(EmailKey)
        }
    }
}
