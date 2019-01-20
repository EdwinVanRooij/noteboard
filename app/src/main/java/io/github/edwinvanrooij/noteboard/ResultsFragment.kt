package io.github.edwinvanrooij.noteboard


import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.edwinvanrooij.noteboard.engine.GameResults
import kotlinx.android.synthetic.main.fragment_results.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 *
 */
class ResultsFragment : Fragment() {

    private lateinit var resultsFragmentListener: ResultsFragmentListener

    private lateinit var soundManager: SoundManager

    private lateinit var results: GameResults

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        results = arguments.getSerializable(KEY_GAME_RESULTS) as GameResults

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        tvPointsValue.text = "${results.points}"
        tvAccuracyValue.text = accuracyToString(results.accuracy)
        tvScoreValue.text = "${results.score}"

        btnPlayAgain.setOnClickListener {
            soundManager.playButtonClick()
            this.resultsFragmentListener.onPlayAgain()
        }
        btnMenu.setOnClickListener {
            soundManager.playButtonClick()
            this.resultsFragmentListener.onMenu()
        }
    }

    fun setResultsFragmentListener(resultsFragmentListener: ResultsFragmentListener) {
        this.resultsFragmentListener = resultsFragmentListener
    }
}
