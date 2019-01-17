package io.github.edwinvanrooij.noteboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.TextView
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.engine.GameEngine
import io.github.edwinvanrooij.noteboard.engine.GameSettings
import io.github.edwinvanrooij.noteboard.engine.IGameListener
import io.github.edwinvanrooij.noteboard.engine.exceptions.GameAlreadyStartedException
import io.github.edwinvanrooij.noteboard.engine.exceptions.GameNotStartedException
import io.github.edwinvanrooij.noteboard.engine.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.engine.music.Note
import io.github.edwinvanrooij.noteboard.engine.music.NoteName
import io.github.edwinvanrooij.noteboard.engine.music.NoteName.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(), IGameListener {

    private lateinit var gameEngine: GameEngine
    private lateinit var soundManager: SoundManager

    private var previousText: String = ""
    private var currentTextView: TextView? = null

    private var timerSeconds: Int = 0
    private var timerThread: Thread? = null

    private val newNoteDelay: Long = 1500L // in ms
    private var newNoteThread: Thread? = null

    private val guessFeedbackRemovalDelay: Long = 1200L // in ms
    private var guessFeedbackRemovalThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameEngine = GameEngine()
        gameEngine.initialize(GameSettings(15))
        gameEngine.setGameListener(this)

        soundManager = SoundManager(this)

        llBg.setOnClickListener {
            soundManager.repeatLastNote()
        }

        btnPlay.setOnClickListener {
            try {
                gameEngine.start()
            } catch (e: GameAlreadyStartedException) {
                Toast.makeText(this, R.string.game_already_started, Toast.LENGTH_SHORT).show()
            }
        }

        btnStop.setOnClickListener {
            if (currentTextView != null) {
                currentTextView!!.text = previousText
                currentTextView!!.visibility = View.INVISIBLE
            }
            try {
                gameEngine.stop()
            } catch (e: GameNotStartedException) {
                Toast.makeText(this, R.string.game_not_started, Toast.LENGTH_SHORT).show()
            }
        }

        setGuessButtonListeners()
    }

    @SuppressLint("SetTextI18n")
    override fun onIncorrectGuess(guessedNoteName: NoteName, correctNote: Note) {
        currentTextView!!.text = "${correctNote.noteName}${correctNote.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.incorrect))
        soundManager.playIncorrectSound()

        removeGuessFeedback()
    }

    private fun removeGuessFeedback() {
        guessFeedbackRemovalThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(guessFeedbackRemovalDelay)
                    runOnUiThread {
                        currentTextView!!.text = previousText
                        currentTextView!!.visibility = View.INVISIBLE
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        guessFeedbackRemovalThread!!.start()
    }

    override fun onGameStop() {
        Toast.makeText(this, "End!", Toast.LENGTH_LONG).show()
        stopTimer()
    }

    override fun onNewNote(note: Note, location: FretLocation) {
        newNoteThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(newNoteDelay)
                    runOnUiThread {
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
        currentTextView!!.text = "${note.noteName}"
        currentTextView!!.setTextColor(resources.getColor(R.color.correct))
        soundManager.playCorrectSound()

        removeGuessFeedback()
    }

    private fun setGuessButtonListeners() {
        fun guess(note: NoteName) {
            try {
                gameEngine.guess(note)
            } catch (e: GameNotStartedException) {
                Toast.makeText(this, R.string.game_not_started, Toast.LENGTH_SHORT).show()
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
                return getChildByFret(findViewById(R.id.string1), location.fretNumber)
            }
            location.stringNumber == 2 -> {
                return getChildByFret(findViewById(R.id.string2), location.fretNumber)
            }
            location.stringNumber == 3 -> {
                return getChildByFret(findViewById(R.id.string3), location.fretNumber)
            }
            location.stringNumber == 4 -> {
                return getChildByFret(findViewById(R.id.string4), location.fretNumber)
            }
            location.stringNumber == 5 -> {
                return getChildByFret(findViewById(R.id.string5), location.fretNumber)
            }
            location.stringNumber == 6 -> {
                return getChildByFret(findViewById(R.id.string6), location.fretNumber)
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
        Toast.makeText(this, "Start!", Toast.LENGTH_LONG).show()
        startTimer()
    }

    /**
     * Stops the timer thread and resets the timerSeconds.
     */
    private fun stopTimer() {
        timerThread!!.interrupt()
        timerSeconds = 0
    }

    /**
     * Initializes and starts the timer thread, updating the timer TextView on the ui thread every second.
     */
    private fun startTimer() {
        tvTime.text = secondsToHumanReadableString(timerSeconds)
        timerThread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        Thread.sleep(1000)
                        tvTime.text = secondsToHumanReadableString(++timerSeconds)
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
        val in100s = (accuracy * 100)
        val rounded = String.format("%.2f", in100s)
        tvAccuracy.text = "$rounded%"
    }

    override fun onScoreChange(score: Int) {
        tvScore.text = score.toString()
    }
}
