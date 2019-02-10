package io.github.edwinvanrooij.noteboard.ui

import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults

interface MoreGamesAdapterListener {

    /**
     * Executed when the user selected the game Frets.
     */
    fun onChoseFrets()

    /**
     * Executed when the user selected the game Frets.
     */
    fun onChoseOctaves()
}
