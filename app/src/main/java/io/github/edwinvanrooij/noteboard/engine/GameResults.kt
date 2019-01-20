package io.github.edwinvanrooij.noteboard.engine

import java.io.Serializable

data class GameResults(
    val score: Int,
    val accuracy: Double,
    val points: Int // points = score * accuracy
) : Serializable
