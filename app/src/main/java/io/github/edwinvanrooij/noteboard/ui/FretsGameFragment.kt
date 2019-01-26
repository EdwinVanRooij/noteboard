package io.github.edwinvanrooij.noteboard.ui


import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.TextView
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameEngine
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.FretsGameSettings
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.IFretsGameListener
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameNotStartedException
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.listeners.GameFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import kotlinx.android.synthetic.main.fragment_game.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FretsGameFragment : Fragment(), IFretsGameListener {

    private lateinit var gameFragmentListener: GameFragmentListener

    private lateinit var fretsGameEngine: FretsGameEngine
    private lateinit var soundManager: SoundManager

    private var previousText: String = ""
    private var currentTextView: TextView? = null

    private var timerSeconds: Int = 0 // is set on game start
    private var timerThread: Thread? = null

    private val newNoteDelay: Long = 1500L // in ms
    private var newNoteThread: Thread? = null

    private val guessFeedbackRemovalDelay: Long = 1200L // in ms
    private var guessFeedbackRemovalThread: Thread? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settings = arguments.getSerializable(KEY_GAME_SETTINGS) as FretsGameSettings

        fretsGameEngine = FretsGameEngine()
        fretsGameEngine.initialize(settings)
        timerSeconds = settings.time
        fretsGameEngine.setGameListener(this)

        soundManager = SoundManager(activity)

        llBg.setOnClickListener {
            soundManager.repeatLastNote()
        }

        fretsGameEngine.start()
        setGuessButtonListeners()
    }

    @SuppressLint("SetTextI18n")
    override fun onIncorrectGuess(guessedNoteName: NoteName, correctNote: Note) {
        if (currentTextView == null) {
            return
        }

        currentTextView!!.text = "${correctNote.noteName}"
        currentTextView!!.setTextColor(resources.getColor(R.color.incorrect))
        soundManager.playIncorrectSound()

        removeGuessFeedback()
    }

    private fun removeGuessFeedback() {
        guessFeedbackRemovalThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(guessFeedbackRemovalDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        if (currentTextView != null) {
                            currentTextView!!.text = previousText
                            currentTextView!!.visibility = View.INVISIBLE
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        guessFeedbackRemovalThread!!.start()
    }

    override fun onGameStop(resultsFrets: FretsGameResults) {
        gameFragmentListener.onGameOver(resultsFrets)
    }

    override fun onNewNote(note: Note, location: FretLocation) {
        newNoteThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(newNoteDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        soundManager.playSound(note)
                        showQuestionMark(location)
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        newNoteThread!!.start()
    }

    @SuppressLint("SetTextI18n")
    override fun onCorrectGuess(note: Note) {
        if (currentTextView == null) {
            return
        }

        currentTextView!!.text = "${note.noteName}"
        currentTextView!!.setTextColor(resources.getColor(R.color.correct))
        soundManager.playCorrectSound()

        removeGuessFeedback()
    }

    private fun setGuessButtonListeners() {
        fun guess(note: NoteName) {
            try {
                if (currentTextView == null) {
                    Toast.makeText(activity, R.string.wait_before_display, Toast.LENGTH_SHORT).show()
                    return
                }
                fretsGameEngine.guess(note)
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

    private fun showQuestionMark(location: FretLocation) {
        currentTextView = getTextViewByFretLocation(location)

        if (currentTextView == null) {
            return
        }

        previousText = currentTextView!!.text.toString()

        currentTextView!!.visibility = View.VISIBLE
        currentTextView!!.setTextColor(resources.getColor(R.color.black))
        currentTextView!!.text = "?"

        val scale = ScaleAnimation(3F, 1.0F, 3F, 1.0F)
        scale.duration = 250
        currentTextView!!.startAnimation(scale)
    }

    private fun getTextViewByFretLocation(location: FretLocation): TextView? {
        when {
            location.stringNumber == 1 -> {
                return getChildByFret(activity.findViewById(R.id.string1), location.fretNumber)
            }
            location.stringNumber == 2 -> {
                return getChildByFret(activity.findViewById(R.id.string2), location.fretNumber)
            }
            location.stringNumber == 3 -> {
                return getChildByFret(activity.findViewById(R.id.string3), location.fretNumber)
            }
            location.stringNumber == 4 -> {
                return getChildByFret(activity.findViewById(R.id.string4), location.fretNumber)
            }
            location.stringNumber == 5 -> {
                return getChildByFret(activity.findViewById(R.id.string5), location.fretNumber)
            }
            location.stringNumber == 6 -> {
                return getChildByFret(activity.findViewById(R.id.string6), location.fretNumber)
            }
            else -> {
                throw Exception("Are you sure this guitar has a string number ${location.stringNumber}?")
            }
        }
    }

    private fun getChildByFret(parent: View?, fret: Int): TextView? {
        when (fret) {
            0 -> return parent!!.findViewById(R.id.fret0)
            1 -> return parent!!.findViewById(R.id.fret1)
            2 -> return parent!!.findViewById(R.id.fret2)
            3 -> return parent!!.findViewById(R.id.fret3)
            4 -> return parent!!.findViewById(R.id.fret4)
            5 -> return parent!!.findViewById(R.id.fret5)
            6 -> return parent!!.findViewById(R.id.fret6)
            7 -> return parent!!.findViewById(R.id.fret7)
            8 -> return parent!!.findViewById(R.id.fret8)
            9 -> return parent!!.findViewById(R.id.fret9)
            10 -> return parent!!.findViewById(R.id.fret10)
            11 -> return parent!!.findViewById(R.id.fret11)
            12 -> return parent!!.findViewById(R.id.fret12)
            13 -> return parent!!.findViewById(R.id.fret13)
            14 -> return parent!!.findViewById(R.id.fret14)
            15 -> return parent!!.findViewById(R.id.fret15)
            16 -> return parent!!.findViewById(R.id.fret16)
            17 -> return parent!!.findViewById(R.id.fret17)
            18 -> return parent!!.findViewById(R.id.fret18)
            19 -> return parent!!.findViewById(R.id.fret19)
            20 -> return parent!!.findViewById(R.id.fret20)
        }
        return null
    }

    override fun onGameStart() {
        startTimer()
    }

    /**
     * Initializes and starts the timer thread, updating the timer TextView on the ui thread every second.
     */
    private fun startTimer() {
//        timerSeconds = 60
        tvTime.text = secondsToHumanReadableString(timerSeconds)
        timerThread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        Thread.sleep(1000)
                        timerSeconds -= 1
                        if (activity == null) {
                            return
                        }
                        activity.runOnUiThread {
                            tvTime.text = secondsToHumanReadableString(timerSeconds)
                        }

                        if (timerSeconds == 0) {
                            interrupt() // interrupting this thread means stopping this game
                            timerThread!!.interrupt() // interrupting this thread means stopping this game

                            // Thread was interrupted, stop the game
                            if (activity == null) {
                                return
                            }
                            activity.runOnUiThread {
                                if (currentTextView != null) {
                                    currentTextView!!.text = previousText
                                    currentTextView!!.visibility = View.INVISIBLE
                                }
                                try {
                                    fretsGameEngine.stop()
                                } catch (e: GameNotStartedException) {
                                    Toast.makeText(activity,
                                        R.string.game_not_started, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        timerThread!!.start()
    }

    /**
     * This is probably the ugliest worst method I've ever written. I could honestly not care less :).
     */
    private fun secondsToHumanReadableString(seconds: Int): String {
        return "%d:%02d".format(seconds / 60, seconds % 60)
    }

    @SuppressLint("SetTextI18n")
    override fun onAccuracyChange(accuracy: Double) {
        tvAccuracy.text = accuracyToString(accuracy)
    }

    override fun onScoreChange(score: Int) {
        tvScore.text = score.toString()
    }

    fun setGameFragmentListener(gameFragmentListener: GameFragmentListener) {
        this.gameFragmentListener = gameFragmentListener
    }
}
