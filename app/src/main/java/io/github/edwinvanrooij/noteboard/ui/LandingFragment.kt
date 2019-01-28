package io.github.edwinvanrooij.noteboard.ui


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.edwinvanrooij.noteboard.Game
import io.github.edwinvanrooij.noteboard.KEY_SELECTED_GAME
import io.github.edwinvanrooij.noteboard.listeners.LandingFragmentListener
import io.github.edwinvanrooij.noteboard.R
import io.github.edwinvanrooij.noteboard.SoundManager
import kotlinx.android.synthetic.main.fragment_landing.*


/**
 * A simple [Fragment] subclass.
 *
 */
class LandingFragment : Fragment() {

    private lateinit var landingFragmentListener: LandingFragmentListener

    private lateinit var selectedGame: Game

    private lateinit var soundManager: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedGame = arguments.getSerializable(KEY_SELECTED_GAME) as Game

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        tvSelectedGame.text = selectedGame.name.toLowerCase()

        btnStart.setOnClickListener {
            soundManager.playButtonClick()
            this.landingFragmentListener.onStartClick()
        }
        btnMoreGames.setOnClickListener {
            soundManager.playButtonClick()
            this.landingFragmentListener.onMoreGamesClick()
        }
        btnOptions.setOnClickListener {
            soundManager.playButtonClick()
            this.landingFragmentListener.onOptionsClick()
        }
    }

    fun setLandingFragmentListener(landingFragmentListener: LandingFragmentListener) {
        this.landingFragmentListener = landingFragmentListener;
    }
}
