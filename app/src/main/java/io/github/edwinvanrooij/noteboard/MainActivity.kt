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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity(), GameListener {

    private var gameEngine: GameEngine = GameEngine()

    private var previousText: String = ""
    private var currentTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameEngine.setGameListener(this)

        btnPlay.setOnClickListener {
            gameEngine.start()
        }

        btnReplay.setOnClickListener {
            if (currentTextView != null) {
                currentTextView!!.text = previousText
                currentTextView!!.visibility = View.INVISIBLE
            }
            gameEngine.restart()
        }

        setGuessButtonListeners()
    }

    private fun setGuessButtonListeners() {
        btnC.setOnClickListener {
            gameEngine.guess(NoteName.C)
        }
        btnD.setOnClickListener {
            gameEngine.guess(NoteName.D)
        }
        btnE.setOnClickListener {
            gameEngine.guess(NoteName.E)
        }
        btnF.setOnClickListener {
            gameEngine.guess(NoteName.F)
        }
        btnG.setOnClickListener {
            gameEngine.guess(NoteName.G)
        }
        btnA.setOnClickListener {
            gameEngine.guess(NoteName.A)
        }
        btnB.setOnClickListener {
            gameEngine.guess(NoteName.B)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCorrectGuess(note: Note) {
//        val str = "Correctly guessed $note"
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()

        currentTextView!!.text = "${note.name}${note.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.correct))

        val handler = Handler()
        handler.postDelayed({
            Thread.sleep(1000) // sleep 1s on correct guess

            currentTextView!!.text = previousText
            currentTextView!!.visibility = View.INVISIBLE

            gameEngine.nextNote()
        }, 1000)
    }

    @SuppressLint("SetTextI18n")
    override fun onIncorrectGuess(
        guess: NoteName,
        correct: Note
    ) {
//        val str = "Incorrectly guessed: Guessed $guess, but was $correct"
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()

        currentTextView!!.text = "${correct.name}${correct.octave}"
        currentTextView!!.setTextColor(resources.getColor(R.color.incorrect))

        val handler = Handler()
        handler.postDelayed({
            Thread.sleep(1500) // sleep 1.5s on incorrect guess

            currentTextView!!.text = previousText
            currentTextView!!.visibility = View.INVISIBLE

            gameEngine.nextNote()
        }, 1000)
    }

    override fun onNewNote(note: Note) {
//        val str = "New note picked: $note"
//        println(str)
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()

        val locations: List<Fret> = gameEngine.getNoteLocationsOnGuitar(note).shuffled()
        val locationToVisualize = locations[0]
        println("To visualize: $locationToVisualize")

        playSound(locationToVisualize)
        showQuestionMark(locationToVisualize)
        println("Showed (or not) question mark on: $locationToVisualize")
    }

    private fun playSound(l: Fret) {
        var mPlayer: MediaPlayer? = null

        when {
            l.string == 6 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_15)
//                    else -> Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.string == 5 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_15)
//                    else -> Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.string == 4 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_15)
//                    else ->
//                        Toast.makeText(
//                        this,
//                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
            l.string == 3 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_15)
                }
            }
            l.string == 2 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_15)
                }
            }
            l.string == 1 -> {
                when {
                    l.fret == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_0)
                    l.fret == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_1)
                    l.fret == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_2)
                    l.fret == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_3)
                    l.fret == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_4)
                    l.fret == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_5)
                    l.fret == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_6)
                    l.fret == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_7)
                    l.fret == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_8)
                    l.fret == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_9)
                    l.fret == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_10)
                    l.fret == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_11)
                    l.fret == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_12)
                    l.fret == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_13)
                    l.fret == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_14)
                    l.fret == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_15)
                }
            }
            else -> Toast.makeText(
                this,
                "Error! Could not find string with number ${l.string}",
                Toast.LENGTH_SHORT
            ).show()
        }

        mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mPlayer.start()
    }

    private fun showQuestionMark(
        location: Fret
    ) {
        when {
            location.string == 1 -> {
                val parent = findViewById<View>(R.id.string1)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 2 -> {
                val parent = findViewById<View>(R.id.string2)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 3 -> {
                val parent = findViewById<View>(R.id.string3)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 4 -> {
                val parent = findViewById<View>(R.id.string4)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 5 -> {
                val parent = findViewById<View>(R.id.string5)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 6 -> {
                val parent = findViewById<View>(R.id.string6)
                val child: TextView? = getChildByFret(parent, location.fret)
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
