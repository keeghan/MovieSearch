package com.keeghan.movieinfo.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
) : ViewModel() {


    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        settingsDataStore.edit { prefs ->
            prefs[PreferencesKeys.IS_DARK_MODE] = isDarkTheme
        }
    }


    val settingsPreferencesFlow: Flow<SettingsPreferences> = settingsDataStore.data
        .map { prefs ->
            val isDarkTheme = prefs[PreferencesKeys.IS_DARK_MODE] ?: true
            SettingsPreferences(isDarkTheme)
        }


    data class SettingsPreferences(val isDarkTheme: Boolean)

    private object PreferencesKeys {
        val IS_DARK_MODE = booleanPreferencesKey("is_darkMode")
    }
}