package fr.eni.ecole.enishop.service

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "settings")

object DataStoreManager {

    private val DARK_THEME_KEY = booleanPreferencesKey(name = "dark_theme")

    fun isDarkThemeActivated(context: Context) : Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[DARK_THEME_KEY]?:false
        }
    }

    suspend fun setDarkThemeActivated(context: Context, isActivated : Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = isActivated
        }
    }

}
