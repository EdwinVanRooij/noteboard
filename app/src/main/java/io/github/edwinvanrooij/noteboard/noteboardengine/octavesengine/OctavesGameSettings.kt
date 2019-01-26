package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import java.io.Serializable

data class OctavesGameSettings(
    val startingOctave: Int, // learn notes starting at this octave (inclusive)
    val endingOctave: Int, // learn notes until this octave (inclusive)
    val time: Int // the time a game should last (in seconds)
) : Serializable
