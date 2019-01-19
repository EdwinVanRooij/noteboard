package io.github.edwinvanrooij.noteboard.engine

data class GameResults(
    val time: Int, // the amount of time that passed in seconds
    val score: Int,
    val accuracy: Double
)
