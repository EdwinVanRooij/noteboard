package io.github.edwinvanrooij.noteboard

interface LandingFragmentListener {
    /**
     * Executed when start button was clicked.
     */
    fun onStartClick()

    /**
     * Executed when stats button was clicked.
     */
    fun onStatsClick()

    /**
     * Executed when options button was clicked.
     */
    fun onOptionsClick()
}
