package io.github.edwinvanrooij.noteboard.ui

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.transition.Fade
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.*
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults


class MainActivity : Activity(),
    FretsGameFragmentListener,
    OctavesGameFragmentListener,
    LandingFragmentListener,
    ResultsFragmentListener,
    MoreGamesFragmentListener,
    OptionsFragmentListener {

    private val landingFragment = LandingFragment()
    private val gameFragment = FretsGameFragment()
    private val resultsFragment = FretsResultsFragment()
    private val optionsFragment = OptionsFragment()
    private val moreGamesFragment = MoreGamesFragment()

    private lateinit var preferenceManager: MyPreferenceManager

    init {
        landingFragment.setLandingFragmentListener(this)
        gameFragment.setGameFragmentListener(this)
        resultsFragment.setResultsFragmentListener(this)
        optionsFragment.setOptionsFragmentListener(this)
        moreGamesFragment.setMoreGamesFragmentListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceManager = MyPreferenceManager(this)

        showLanding()
    }

    private fun showLanding() {
        val bundle = Bundle()
        bundle.putSerializable(KEY_SELECTED_GAME, preferenceManager.getSelectedGame())
        landingFragment.arguments = bundle
        showFragment(landingFragment)
    }

    override fun onGameOver(results: OctavesGameResults) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_GAME_RESULTS, results)
        resultsFragment.arguments = bundle

        showFragment(resultsFragment)
    }

    override fun onGameOver(results: FretsGameResults) {
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

    override fun onChoseFrets() {
        preferenceManager.setSelectedGame(Game.FRETS)
        showLanding()
    }

    override fun onChoseOctaves() {
        preferenceManager.setSelectedGame(Game.OCTAVES)
        showLanding()
    }

    override fun onMoreGamesClick() {
        showFragment(moreGamesFragment)
    }

    override fun onOptionsDone() {
        showLanding()
    }

    override fun onOptionsClick() {
        showFragment(optionsFragment)
    }

    override fun onMenu() {
        showLanding()
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
