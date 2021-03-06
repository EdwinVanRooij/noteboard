package io.github.edwinvanrooij.noteboard.listeners

interface ResultsFragmentListener {
    /**
     * Executed when the user wants to play again.
     */
    fun onPlayAgain()

    /**
     * Executed when the user wants to go back to the app menu.
     */
    fun onMenu()
}
