package io.github.edwinvanrooij.noteboard

import android.annotation.SuppressLint
import android.app.Activity
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.github.edwinvanrooij.noteboard.lib.GameSettings
import io.github.edwinvanrooij.noteboard.lib.guitar.FretLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity(), io.github.edwinvanrooij.noteboard.lib.IGameListener {
    override fun onGameStop() {
        Toast.makeText(this, "End!", Toast.LENGTH_LONG).show()
    }

    override fun onNewFretLocation(location: FretLocation) {
        playSound(location)
        showQuestionMark(location)
        println("Showed (or not) question mark on: $location")
    }

    override fun onCorrectGuess(note: io.github.edwinvanrooij.noteboard.lib.music.Note) {
        currentTextView!!.text = "${note.noteName}${note.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.correct))

//        val handler = Handler()
//        handler.postDelayed({
//            Thread.sleep(0) // sleep 1s on correct guess
//            Thread.sleep(1000) // sleep 1s on correct guess

            currentTextView!!.text = previousText
            currentTextView!!.visibility = View.INVISIBLE

//            gameEngine.n  enextNote()
//        }, 0)
//    }, 1000)
    }

    override fun onIncorrectGuess(
        guessedNoteName: io.github.edwinvanrooij.noteboard.lib.music.NoteName,
        correctNote: io.github.edwinvanrooij.noteboard.lib.music.Note
    ) {
        currentTextView!!.text = "${correctNote.noteName}${correctNote.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.incorrect))

//        val handler = Handler()
//        handler.postDelayed({
//            Thread.sleep(0) // sleep 1.5s on incorrect guess
//            Thread.sleep(1500) // sleep 1.5s on incorrect guess

            currentTextView!!.text = previousText
            currentTextView!!.visibility = View.INVISIBLE
//        }, 0)
//    }, 1000)

    }

    private var gameEngine: io.github.edwinvanrooij.noteboard.lib.GameEngine = io.github.edwinvanrooij.noteboard.lib.GameEngine()

    private var previousText: String = ""
    private var currentTextView: TextView? = null

    private var mPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameEngine.initialize(GameSettings(15))
        gameEngine.setGameListener(this)

        btnRepeat.setOnClickListener {
            if (mPlayer != null) {
                mPlayer!!.start()
            }
        }

        btnPlay.setOnClickListener {
            gameEngine.start()
        }

        btnReplay.setOnClickListener {
            if (currentTextView != null) {
                currentTextView!!.text = previousText
                currentTextView!!.visibility = View.INVISIBLE
            }
            gameEngine.start()
        }

        setGuessButtonListeners()
    }

    private fun setGuessButtonListeners() {
        btnC.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.C)
        }
        btnD.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.D)
        }
        btnE.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.E)
        }
        btnF.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.F)
        }
        btnG.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.G)
        }
        btnA.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.A)
        }
        btnB.setOnClickListener {
            gameEngine.guess(io.github.edwinvanrooij.noteboard.lib.music.NoteName.B)
        }
    }

    private fun playSound(l: FretLocation) {
        when {
            l.stringNumber == 6 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_15)
//                    else -> Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.stringNumber == 5 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_15)
//                    else -> Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.stringNumber == 4 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_15)
//                    else ->
//                        Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.stringNumber == 3 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_15)
                }
            }
            l.stringNumber == 2 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_15)
                }
            }
            l.stringNumber == 1 -> {
                when {
                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_0)
                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_1)
                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_2)
                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_3)
                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_4)
                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_5)
                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_6)
                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_7)
                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_8)
                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_9)
                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_10)
                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_11)
                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_12)
                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_13)
                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_14)
                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_15)
                }
            }
            else -> Toast.makeText(
                this,
                "Error! Could not find string with number ${l.stringNumber}",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (mPlayer != null) {
            mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mPlayer!!.start()
            mPlayer!!.reset()
            mPlayer!!.release()
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
        currentTextView!!.setTextColor(resources.getColor(R.color.neutral))
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
        Thread(Runnable {
            startTimer()
        }).start()
//        txtTime.text = 0
//        val str = "Game started!"
//        println(str)
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()
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

    override fun onScoreChange(newScore: Int) {
        txtScore.text = newScore.toString()
    }
}
