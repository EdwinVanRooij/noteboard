package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine

import java.io.Serializable

data class GameResults(
    val score: Int,
    val accuracy: Double,
    val points: Int // points = score * accuracy
) : Serializable
