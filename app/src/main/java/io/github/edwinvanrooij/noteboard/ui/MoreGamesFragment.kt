package io.github.edwinvanrooij.noteboard.ui


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.MoreGamesFragmentListener
import io.github.edwinvanrooij.noteboard.listeners.OctavesGameFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.IOctavesGameListener
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameEngine
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameSettings
import kotlinx.android.synthetic.main.fragment_more_games.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MoreGamesFragment : Fragment(), MoreGamesAdapterListener {

    private lateinit var moreGamesFragmentListener: MoreGamesFragmentListener

    private lateinit var soundManager: SoundManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_games, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        btnMenu.setOnClickListener {
            soundManager.playButtonClick()
            this.moreGamesFragmentListener.onMenu()
        }

        // Init ListView
        val games = arrayListOf(
                Game.FRETS,
                Game.OCTAVES
        )
        val adapter = MoreGamesAdapter(context, games)
        adapter.setMoreGamesAdapterListener(this)
        lvGames.adapter = adapter
    }

    override fun onChoseFrets() {
        soundManager.playButtonClick()
        moreGamesFragmentListener.onChoseFrets()
    }

    override fun onChoseOctaves() {
        soundManager.playButtonClick()
        moreGamesFragmentListener.onChoseOctaves()
    }

    fun setMoreGamesFragmentListener(moreGamesFragmentListener: MoreGamesFragmentListener) {
        this.moreGamesFragmentListener = moreGamesFragmentListener
    }
}
