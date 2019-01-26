package io.github.edwinvanrooij.noteboard

import android.content.Context
import android.preference.PreferenceManager
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameSettings
import java.lang.ClassCastException

class MyPreferenceManager(private val context: Context) {

    private val pm = PreferenceManager.getDefaultSharedPreferences(context)

    fun getGameSettings(): FretsGameSettings {
        // Get the preferred amount of frets
        val defaultFrets = 20
        val fretsResourceKeyId = R.string.key_options_game_frets
        var frets: Int = getIntSafely(resourceKeyId = fretsResourceKeyId, default = defaultFrets)

        // Frets should be anything between 0 and 20, inclusive
        if (frets < 0 || frets > 20) {
            frets = defaultFrets
            putResource(fretsResourceKeyId, defaultFrets)
        }

        // Get the preferred amount of time
        val defaultTime = 30
        val timeResourceKeyId = R.string.key_options_game_time
        var time: Int = getIntSafely(resourceKeyId = timeResourceKeyId, default = defaultTime)

        // Time should be above 0
        if (time < 0) {
            time = defaultTime
            putResource(timeResourceKeyId, defaultTime)
        }

        return FretsGameSettings(frets, time)
    }

    private fun getIntSafely(resourceKeyId: Int, default: Int): Int {
        val stringResource = pm.getString(context.getString(resourceKeyId), null)

        if (stringResource == null) {
            putResource(resourceKeyId, default)
            return default
        }

        return try {
            stringResource.toInt()
        } catch (e: ClassCastException) {
            putResource(resourceKeyId, default)
            default
        }
    }

    private fun putResource(resourceKey: Int, default: Int) {
        pm.edit().putString(context.getString(resourceKey), default.toString()).apply()
    }
}
