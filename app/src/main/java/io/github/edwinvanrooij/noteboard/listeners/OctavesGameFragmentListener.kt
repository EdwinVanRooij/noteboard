package io.github.edwinvanrooij.noteboard.listeners

import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults

interface OctavesGameFragmentListener {
    /**
     * Executed when the game has finished.
     */
    fun onGameOver(results: OctavesGameResults)
}
