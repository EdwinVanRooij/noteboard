package io.github.edwinvanrooij.noteboard.ui

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.app.PendingIntent.getActivity
import android.content.pm.PackageManager
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.*
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import net.gotev.speech.Speech
import java.lang.Exception
import net.gotev.speech.GoogleVoiceTypingDisabledException
import net.gotev.speech.SpeechRecognitionNotAvailable
import net.gotev.speech.SpeechDelegate


@Suppress("CascadeIf")
class MainActivity : Activity(),
    FretsGameFragmentListener,
    OctavesGameFragmentListener,
    LandingFragmentListener,
    ResultsFragmentListener,
    MoreGamesFragmentListener,
    OptionsFragmentListener {

    private lateinit var landingFragment: LandingFragment
    private lateinit var optionsFragment: OptionsFragment
    private lateinit var moreGamesFragment: MoreGamesFragment

    private lateinit var fretsGameFragment: FretsGameFragment
    private lateinit var octavesGameFragment: OctavesGameFragment

    private lateinit var fretsResultsFragment: FretsResultsFragment
    private lateinit var octavesResultsFragment: OctavesResultsFragment

    private lateinit var preferenceManager: MyPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("speech", "speech recognition is now active")
        println("Test")

        // Initialize all fragments
        landingFragment = LandingFragment()
        optionsFragment = OptionsFragment()
        moreGamesFragment = MoreGamesFragment()

        fretsGameFragment = FretsGameFragment()
        octavesGameFragment = OctavesGameFragment()

        fretsResultsFragment = FretsResultsFragment()
        octavesResultsFragment = OctavesResultsFragment()

        // Set all fragment listeners
        landingFragment.setLandingFragmentListener(this)
        optionsFragment.setOptionsFragmentListener(this)
        moreGamesFragment.setMoreGamesFragmentListener(this)

        fretsGameFragment.setGameFragmentListener(this)
        octavesGameFragment.setGameFragmentListener(this)

        fretsResultsFragment.setResultsFragmentListener(this)
        octavesResultsFragment.setResultsFragmentListener(this)
        Log.i("speech", "speech recognition is now active")

        // Init preference listener
        preferenceManager = MyPreferenceManager(this)

        // Show landing fragment
        showLanding()

        Speech.init(this, packageName)
    }


    override fun onDestroy() {
        super.onDestroy()

        landingFragment.onDestroy()
        optionsFragment.onDestroy()
        moreGamesFragment.onDestroy()

        fretsGameFragment.onDestroy()
        octavesGameFragment.onDestroy()

        fretsResultsFragment.onDestroy()
        octavesResultsFragment.onDestroy()

        Speech.getInstance().shutdown()
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
        octavesResultsFragment.arguments = bundle
        showFragment(octavesResultsFragment)
    }

    override fun onGameOver(results: FretsGameResults) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_GAME_RESULTS, results)
        fretsResultsFragment.arguments = bundle
        showFragment(fretsResultsFragment)
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
