package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import io.github.edwinvanrooij.noteboard.engine.GameResults


class MainActivity : Activity(), GameFragmentListener, LandingFragmentListener, ResultsFragmentListener {

    private val landingFragment = LandingFragment()
    private val gameFragment = GameFragment()
    private val resultsFragment = ResultsFragment()

    init {
        landingFragment.setLandingFragmentListener(this)
        gameFragment.setGameFragmentListener(this)
        resultsFragment.setResultsFragmentListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(landingFragment)
    }

    override fun onGameOver(results: GameResults) {
        showFragment(resultsFragment)
    }

    override fun onStartGame() {
        showFragment(gameFragment)
    }

    override fun onMenu() {
        showFragment(landingFragment)
    }

    override fun onPlayAgain() {
        showFragment(gameFragment)
    }

    private fun showFragment(fragment: Fragment) {
        // Set enter transition
        val enterTransition = Fade()
        enterTransition.duration = 200
        enterTransition.startDelay = 200
        fragment.enterTransition = enterTransition

        // Set exit transition
        val exitTransition = Fade()
        exitTransition.duration = 200
        fragment.exitTransition = exitTransition

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
