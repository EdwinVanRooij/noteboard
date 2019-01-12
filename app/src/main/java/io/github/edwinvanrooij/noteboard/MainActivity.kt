package io.github.edwinvanrooij.noteboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.engine.GameEngine
import io.github.edwinvanrooij.noteboard.engine.GameSettings
import io.github.edwinvanrooij.noteboard.engine.IGameListener
import io.github.edwinvanrooij.noteboard.engine.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.engine.music.Note
import io.github.edwinvanrooij.noteboard.engine.music.NoteName
import io.github.edwinvanrooij.noteboard.engine.music.NoteName.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity(), IGameListener {

    private lateinit var gameEngine: GameEngine
    private lateinit var soundManager: SoundManager

    private var previousText: String = ""
    private var currentTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameEngine = GameEngine()
        gameEngine.initialize(GameSettings(15))
        gameEngine.setGameListener(this)

        soundManager = SoundManager(this)

        btnRepeat.setOnClickListener {
            soundManager.repeatLastSound()
        }

        btnPlay.setOnClickListener {
            gameEngine.start()
        }

        btnStop.setOnClickListener {
            if (currentTextView != null) {
                currentTextView!!.text = previousText
                currentTextView!!.visibility = View.INVISIBLE
            }
            gameEngine.stop()
        }

        setGuessButtonListeners()
    }

    @SuppressLint("SetTextI18n")
    override fun onIncorrectGuess(guessedNoteName: NoteName, correctNote: Note) {
        currentTextView!!.text = "${correctNote.noteName}${correctNote.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.incorrect))
        //sleep?
        currentTextView!!.text = previousText
        currentTextView!!.visibility = View.INVISIBLE

    }

    override fun onGameStop() {
        Toast.makeText(this, "End!", Toast.LENGTH_LONG).show()
        stopTimer()
    }

    override fun onNewNote(note: Note, location: FretLocation) {
        soundManager.playSound(note)
        showQuestionMark(location)
        println("Should have showed question mark on: $location")
    }

    @SuppressLint("SetTextI18n")
    override fun onCorrectGuess(note: Note) {
        currentTextView!!.text = "${note.noteName}${note.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.correct))
        //sleep?
        currentTextView!!.text = previousText
        currentTextView!!.visibility = View.INVISIBLE
    }

    private fun setGuessButtonListeners() {
        btnC.setOnClickListener {
            gameEngine.guess(C)
        }
        btnD.setOnClickListener {
            gameEngine.guess(D)
        }
        btnE.setOnClickListener {
            gameEngine.guess(E)
        }
        btnF.setOnClickListener {
            gameEngine.guess(F)
        }
        btnG.setOnClickListener {
            gameEngine.guess(G)
        }
        btnA.setOnClickListener {
            gameEngine.guess(A)
        }
        btnB.setOnClickListener {
            gameEngine.guess(B)
        }
    }

    private fun showQuestionMark(
        location: FretLocation
    ) {
        when {
            location.stringNumber == 1 -> {
                val parent = findViewById<View>(R.id.string1)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.stringNumber == 2 -> {
                val parent = findViewById<View>(R.id.string2)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.stringNumber == 3 -> {
                val parent = findViewById<View>(R.id.string3)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.stringNumber == 4 -> {
                val parent = findViewById<View>(R.id.string4)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.stringNumber == 5 -> {
                val parent = findViewById<View>(R.id.string5)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.stringNumber == 6 -> {
                val parent = findViewById<View>(R.id.string6)
                val child: TextView? = getChildByFret(parent, location.fretNumber)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
        }

        previousText = currentTextView!!.text.toString()

        currentTextView!!.visibility = View.VISIBLE
        currentTextView!!.setTextColor(resources.getColor(R.color.black))
        currentTextView!!.setShadowLayer(5F, 0F, 0F, resources.getColor(R.color.green1))
        currentTextView!!.text = "?"
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
        Thread(Runnable {
            startTimer()
        }).start()
//        txtTime.text = 0
//        val str = "Game started!"
//        println(str)
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()
    }

    fun stopTimer() {
        // todo; stop the timer
    }

    fun startTimer() {
        var seconds = 0

        run {
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        txtTime.text = secondsToHumanReadableString(seconds)
                    }
                    seconds++
                }
            }, 0, 1000)
        }
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
        txtAccuracy.text = "$rounded%"
    }

    override fun onScoreChange(score: Int) {
        txtScore.text = score.toString()
    }
}
