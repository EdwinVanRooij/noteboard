package io.github.edwinvanrooij.noteboard.listeners

import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults

interface GameFragmentListener {
    /**
     * Executed when the game has finished.
     */
    fun onGameOver(resultsFrets: FretsGameResults)
}
