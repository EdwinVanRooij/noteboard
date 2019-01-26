package io.github.edwinvanrooij.noteboard.listeners

import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.GameResults

interface GameFragmentListener {
    /**
     * Executed when the game has finished.
     */
    fun onGameOver(results: GameResults)
}
