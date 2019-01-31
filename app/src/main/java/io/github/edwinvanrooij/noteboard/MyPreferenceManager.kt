package io.github.edwinvanrooij.noteboard

import android.content.Context
import android.preference.PreferenceManager
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameSettings
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameSettings
import java.lang.ClassCastException

class MyPreferenceManager(private val context: Context) {

    private val pm = PreferenceManager.getDefaultSharedPreferences(context)

    fun getFretsGameSettings(): FretsGameSettings {
        // Get the preferred amount of frets
        val defaultFrets = 15
        val fretsPrefKey = context.getString(R.string.key_options_game_frets)

        var frets: Int = getIntSafely(prefKey = fretsPrefKey, default = defaultFrets)

        // Frets should be anything between 0 and 20, inclusive
        if (frets < 0 || frets > 20) {
            frets = defaultFrets
            putResource(fretsPrefKey, defaultFrets)
        }

        // Get the preferred amount of time
        val defaultTime = 30
        val timePrefKey = context.getString(R.string.key_options_game_time)
        var time: Int = getIntSafely(prefKey = timePrefKey, default = defaultTime)

        // Time should be above 0
        if (time < 0) {
            time = defaultTime
            putResource(timePrefKey, defaultTime)
        }

        return FretsGameSettings(frets, time)
    }

    fun getOctavesGameSettings(): OctavesGameSettings {
        // Get the preferred amount of time
        val defaultTime = 30
        val timePrefKey = context.getString(R.string.key_options_game_time)
        var time: Int = getIntSafely(prefKey = timePrefKey, default = defaultTime)

        // Time should be above 0
        if (time < 0) {
            time = defaultTime
            putResource(timePrefKey, defaultTime)
        }

        return OctavesGameSettings(time)
    }

    private fun getIntSafely(prefKey: String, default: Int): Int {
        val stringResource = pm.getString(prefKey, null)

        if (stringResource == null) {
            putResource(prefKey, default)
            return default
        }

        return try {
            stringResource.toInt()
        } catch (e: ClassCastException) {
            putResource(prefKey, default)
            default
        }
    }

    /**
     * Saves a string value to SharedPreferences.
     */
    private fun putResource(prefKey: String, value: String) {
        pm.edit().putString(prefKey, value).apply()
    }

    /**
     * Saves an int value to SharedPreferences.
     */
    private fun putResource(prefKey: String, value: Int) {
        pm.edit().putString(prefKey, value.toString()).apply()
    }

    fun setSelectedGame(game: Game) {
        putResource(KEY_SELECTED_GAME, game.ordinal)
    }

    fun getSelectedGame(): Game {
        val ordinal = getIntSafely(
            KEY_SELECTED_GAME,
            Game.FRETS.ordinal
        ) // we'll use 'FRETS' game as the default, no particular reason

        return Game.values()[ordinal]
    }
}
