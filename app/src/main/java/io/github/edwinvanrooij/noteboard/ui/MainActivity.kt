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
import java.lang.Exception


@Suppress("CascadeIf")
class MainActivity : Activity(),
    FretsGameFragmentListener,
    OctavesGameFragmentListener,
    LandingFragmentListener,
    ResultsFragmentListener,
    MoreGamesFragmentListener,
    OptionsFragmentListener {

    private val landingFragment = LandingFragment()
    private val resultsFragment = FretsResultsFragment()
    private val optionsFragment = OptionsFragment()
    private val moreGamesFragment = MoreGamesFragment()

    private val fretsGameFragment = FretsGameFragment()
    private val octavesGameFragment = OctavesGameFragment()

    private lateinit var preferenceManager: MyPreferenceManager

    init {
        landingFragment.setLandingFragmentListener(this)
        resultsFragment.setResultsFragmentListener(this)
        optionsFragment.setOptionsFragmentListener(this)
        moreGamesFragment.setMoreGamesFragmentListener(this)

        fretsGameFragment.setGameFragmentListener(this)
        octavesGameFragment.setGameFragmentListener(this)
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
        val selectedGame = preferenceManager.getSelectedGame()
        if (selectedGame == Game.FRETS) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_GAME_SETTINGS, preferenceManager.getFretsGameSettings())
            fretsGameFragment.arguments = bundle
            showFragment(fretsGameFragment)
        } else if (selectedGame == Game.OCTAVES) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_GAME_SETTINGS, preferenceManager.getOctavesGameSettings())
            octavesGameFragment.arguments = bundle
            showFragment(octavesGameFragment)
        } else {
            throw Exception("Could not figure out which game to start at onStartClick")
        }
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
        val selectedGame = preferenceManager.getSelectedGame()
        if (selectedGame == Game.FRETS) {
            showFragment(fretsGameFragment)
        } else if (selectedGame == Game.OCTAVES) {
            showFragment(octavesGameFragment)
        } else {
            throw Exception("Could not figure out which game to start at onPlayAgain")
        }
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
