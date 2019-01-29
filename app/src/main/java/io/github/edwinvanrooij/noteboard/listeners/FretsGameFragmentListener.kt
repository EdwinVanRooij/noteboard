package io.github.edwinvanrooij.noteboard.listeners

import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults

interface FretsGameFragmentListener {
    /**
     * Executed when the game has finished.
     */
    fun onGameOver(results: FretsGameResults)
}
