package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import java.io.Serializable

data class OctavesGameSettings(
    val time: Int // the time a game should last (in seconds)
) : Serializable
