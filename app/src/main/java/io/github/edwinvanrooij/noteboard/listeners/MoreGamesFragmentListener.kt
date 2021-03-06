package io.github.edwinvanrooij.noteboard.listeners

interface MoreGamesFragmentListener {
    /**
     * Executed when the user selected the game Frets.
     */
    fun onChoseFrets()

    /**
     * Executed when the user selected the game Frets.
     */
    fun onChoseOctaves()

    /**
     * Executed when the user wants back to the menu.
     */
    fun onMenu()
}
