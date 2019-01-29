package io.github.edwinvanrooij.noteboard.listeners

interface LandingFragmentListener {
    /**
     * Executed when start button was clicked.
     */
    fun onStartClick()

    /**
     * Executed when options button was clicked.
     */
    fun onOptionsClick()

    /**
     * Executed when more games button was clicked.
     */
    fun onMoreGamesClick()
}
