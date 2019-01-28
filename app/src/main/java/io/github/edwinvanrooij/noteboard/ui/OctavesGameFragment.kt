package io.github.edwinvanrooij.noteboard.ui


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.OctavesGameFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.IOctavesGameListener
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameEngine
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameSettings


/**
 * A simple [Fragment] subclass.
 *
 */
class OctavesGameFragment : Fragment(), IOctavesGameListener {

    private lateinit var gameFragmentListener: OctavesGameFragmentListener

    private lateinit var gameEngine: OctavesGameEngine
    private lateinit var soundManager: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_octaves, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settings = arguments.getSerializable(KEY_GAME_SETTINGS) as OctavesGameSettings

        gameEngine = OctavesGameEngine()
        gameEngine.initialize(settings)
        gameEngine.setGameListener(this)

        soundManager = SoundManager(activity)
    }

    override fun onGameStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGameStop(results: OctavesGameResults) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNewNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccuracyChange(accuracy: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScoreChange(score: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCorrectGuess(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIncorrectGuess(correctNote: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setGameFragmentListener(gameFragmentListener: OctavesGameFragmentListener) {
        this.gameFragmentListener = gameFragmentListener
    }
}
