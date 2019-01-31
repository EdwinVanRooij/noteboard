package io.github.edwinvanrooij.noteboard.ui


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.OctavesGameFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.IOctavesGameListener
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameEngine
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameSettings
import kotlinx.android.synthetic.main.fragment_game_octaves.*
import com.skyfishjy.library.RippleBackground
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameNotStartedException


/**
 * A simple [Fragment] subclass.
 *
 */
class OctavesGameFragment : Fragment(), IOctavesGameListener {

    private lateinit var gameFragmentListener: OctavesGameFragmentListener

    private lateinit var gameEngine: OctavesGameEngine
    private lateinit var soundManager: SoundManager

    private lateinit var rbg: RippleBackground

    private val newNoteDelay: Long = 2500L // in ms
    private var newNoteThread: Thread? = null

    private val guessFeedbackRemovalDelay: Long = 2200L // in ms
    private var guessFeedbackRemovalThread: Thread? = null

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

        rbg = rbContent as RippleBackground

        gameEngine = OctavesGameEngine()
        gameEngine.initialize(settings)
        gameEngine.setGameListener(this)

        soundManager = SoundManager(activity)

        rbg.setOnClickListener {
            soundManager.repeatLastNote()
        }

        gameEngine.start()
        setGuessButtonListeners()
    }

    private fun setGuessButtonListeners() {
        fun guess(note: NoteName) {
            try {
                gameEngine.guess(note)
            } catch (e: GameNotStartedException) {
                Toast.makeText(activity, R.string.game_not_started, Toast.LENGTH_SHORT).show()
            }
        }
        btnC.setOnClickListener {
            guess(C)
        }
        btnD.setOnClickListener {
            guess(D)
        }
        btnE.setOnClickListener {
            guess(E)
        }
        btnF.setOnClickListener {
            guess(F)
        }
        btnG.setOnClickListener {
            guess(G)
        }
        btnA.setOnClickListener {
            guess(A)
        }
        btnB.setOnClickListener {
            guess(B)
        }
    }

    override fun onGameStart() {
        rbg.startRippleAnimation()
    }

    override fun onGameStop(results: OctavesGameResults) {
        gameFragmentListener.onGameOver(results)
    }

    override fun onNewNote(note: Note) {
        newNoteThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(newNoteDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        soundManager.playSound(note)
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        newNoteThread!!.start()
    }

    override fun onAccuracyChange(accuracy: Double) {
        tvAccuracy.text = accuracyToString(accuracy)
    }

    override fun onScoreChange(score: Int) {
        tvScore.text = score.toString()
    }

    override fun onCorrectGuess(note: Note) {
        setNoteButtonCorrect(note)
        soundManager.playCorrectSound()

        removeGuessFeedback(note)
    }

    override fun onIncorrectGuess(correctNote: Note, guessedNote: NoteName) {
        setNoteButtonIncorrect(guessedNote)
        setNoteButtonCorrect(correctNote)
        soundManager.playIncorrectSound()

        removeGuessFeedback(correctNote, guessedNote)
    }

    private fun removeGuessFeedback(correctNote: Note, incorrectNote: NoteName? = null) {
        guessFeedbackRemovalThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(guessFeedbackRemovalDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        setNoteButtonNormal(correctNote)
                        if (incorrectNote != null) {
                            setNoteButtonNormal(incorrectNote)
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        guessFeedbackRemovalThread!!.start()
    }

    private fun setNoteButtonNormal(note: NoteName) {
        when (note) {
            C -> btnC.setColor(context.getColor(R.color.colorPrimary))
            D -> btnD.setColor(context.getColor(R.color.colorPrimary))
            E -> btnE.setColor(context.getColor(R.color.colorPrimary))
            F -> btnF.setColor(context.getColor(R.color.colorPrimary))
            G -> btnG.setColor(context.getColor(R.color.colorPrimary))
            A -> btnA.setColor(context.getColor(R.color.colorPrimary))
            B -> btnB.setColor(context.getColor(R.color.colorPrimary))
        }
    }

    private fun setNoteButtonNormal(note: Note) {
        when (note.noteName) {
            C -> btnC.setColor(context.getColor(R.color.colorPrimary))
            D -> btnD.setColor(context.getColor(R.color.colorPrimary))
            E -> btnE.setColor(context.getColor(R.color.colorPrimary))
            F -> btnF.setColor(context.getColor(R.color.colorPrimary))
            G -> btnG.setColor(context.getColor(R.color.colorPrimary))
            A -> btnA.setColor(context.getColor(R.color.colorPrimary))
            B -> btnB.setColor(context.getColor(R.color.colorPrimary))
        }
    }

    private fun setNoteButtonCorrect(note: Note) {
        when (note.noteName) {
            C -> btnC.setColor(context.getColor(R.color.correct))
            D -> btnD.setColor(context.getColor(R.color.correct))
            E -> btnE.setColor(context.getColor(R.color.correct))
            F -> btnF.setColor(context.getColor(R.color.correct))
            G -> btnG.setColor(context.getColor(R.color.correct))
            A -> btnA.setColor(context.getColor(R.color.correct))
            B -> btnB.setColor(context.getColor(R.color.correct))
        }
    }

    private fun setNoteButtonIncorrect(note: NoteName) {
        when (note) {
            C -> btnC.setColor(context.getColor(R.color.incorrect))
            D -> btnD.setColor(context.getColor(R.color.incorrect))
            E -> btnE.setColor(context.getColor(R.color.incorrect))
            F -> btnF.setColor(context.getColor(R.color.incorrect))
            G -> btnG.setColor(context.getColor(R.color.incorrect))
            A -> btnA.setColor(context.getColor(R.color.incorrect))
            B -> btnB.setColor(context.getColor(R.color.incorrect))
        }
    }

    fun setGameFragmentListener(gameFragmentListener: OctavesGameFragmentListener) {
        this.gameFragmentListener = gameFragmentListener
    }

    override fun onDestroy() {
        super.onDestroy()

        guessFeedbackRemovalThread?.interrupt()
        newNoteThread?.interrupt()
    }
}
