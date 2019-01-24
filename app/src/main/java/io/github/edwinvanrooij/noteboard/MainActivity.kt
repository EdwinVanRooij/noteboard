package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.transition.Fade
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.engine.GameResults


class MainActivity : Activity(), GameFragmentListener, LandingFragmentListener, ResultsFragmentListener, OptionsFragmentListener {

    private val landingFragment = LandingFragment()
    private val gameFragment = GameFragment()
    private val resultsFragment = ResultsFragment()
    private val optionsFragment = OptionsFragment()

    private lateinit var preferenceManager: MyPreferenceManager

    init {
        landingFragment.setLandingFragmentListener(this)
        gameFragment.setGameFragmentListener(this)
        resultsFragment.setResultsFragmentListener(this)
        optionsFragment.setOptionsFragmentListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceManager = MyPreferenceManager(this)
        showFragment(landingFragment)
    }

    override fun onGameOver(results: GameResults) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_GAME_RESULTS, results)
        resultsFragment.arguments = bundle

        showFragment(resultsFragment)
    }

    override fun onStartClick() {
        val bundle = Bundle()
        val settings = preferenceManager.getGameSettings()
        bundle.putSerializable(KEY_GAME_SETTINGS, settings)
        gameFragment.arguments = bundle

        showFragment(gameFragment)
    }

    override fun onStatsClick() {
        Toast.makeText(this, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsDone() {
        showFragment(landingFragment)
    }

    override fun onOptionsClick() {
        showFragment(optionsFragment)
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
