package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import java.io.Serializable

data class OctavesGameResults(
    val score: Int,
    val accuracy: Double,
    val points: Int // points = score * accuracy
) : Serializable
