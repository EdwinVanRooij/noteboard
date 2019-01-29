package io.github.edwinvanrooij.noteboard.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults
import io.github.edwinvanrooij.noteboard.listeners.ResultsFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import kotlinx.android.synthetic.main.fragment_results.*


/**
 * A simple [Fragment] subclass.
 */
class OctavesResultsFragment : Fragment() {

    private lateinit var resultsFragmentListener: ResultsFragmentListener

    private lateinit var soundManager: SoundManager

    private lateinit var gameResults: OctavesGameResults

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameResults = arguments.getSerializable(KEY_GAME_RESULTS) as OctavesGameResults

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        tvPointsValue.text = "${gameResults.points}"
        tvAccuracyValue.text = accuracyToString(gameResults.accuracy)
        tvScoreValue.text = "${gameResults.score}"

        btnPlayAgain.setOnClickListener {
            soundManager.playButtonClick()
            this.resultsFragmentListener.onPlayAgain()
        }
        btnMenu.setOnClickListener {
            soundManager.playButtonClick()
            this.resultsFragmentListener.onMenu()
        }

        enableButtons()
    }

    private fun enableButtons() {
        if (activity == null) {
            return
        }

        val newThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(1200)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        btnMenu.isEnabled = true
                        btnPlayAgain.isEnabled = true
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        newThread.start()
    }

    fun setResultsFragmentListener(resultsFragmentListener: ResultsFragmentListener) {
        this.resultsFragmentListener = resultsFragmentListener
    }
}
