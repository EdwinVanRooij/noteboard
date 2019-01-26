package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine

import java.io.Serializable

data class GameSettings(
    val guitarFrets: Int, // the amount of frets on the user's guitar
    val time: Int // the time in seconds
) : Serializable
