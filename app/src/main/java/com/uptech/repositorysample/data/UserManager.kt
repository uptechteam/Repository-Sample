package com.uptech.repositorysample.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(
  private val dataStore: DataStore<Preferences>
) {
  val userToken: Flow<String?> = dataStore.data.map { preferences ->
    preferences[ACCESS_TOKEN_KEY]
  }

  suspend fun login() {
    dataStore.edit { mutablePreferences ->
      mutablePreferences[ACCESS_TOKEN_KEY] = "dummyToken"
    }
  }

  suspend fun logout() {
    dataStore.edit { mutablePreferences ->
      mutablePreferences.remove(ACCESS_TOKEN_KEY)
    }
  }

  companion object {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
  }
}