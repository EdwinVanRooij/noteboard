package io.github.edwinvanrooij.noteboard

import io.github.edwinvanrooij.noteboard.engine.GameResults

interface GameFragmentListener {
    /**
     * Executed when the game has finished.
     */
    fun onGameOver(results: GameResults)
}
