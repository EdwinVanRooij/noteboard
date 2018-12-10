package io.github.edwinvanrooij.noteboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

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

        setGuessButtonListeners()
    }

    private fun setGuessButtonListeners() {
        btnC.setOnClickListener {
            gameEngine.guess("C")
        }
        btnD.setOnClickListener {
            gameEngine.guess("D")
        }
        btnE.setOnClickListener {
            gameEngine.guess("E")
        }
        btnF.setOnClickListener {
            gameEngine.guess("F")
        }
        btnG.setOnClickListener {
            gameEngine.guess("G")
        }
        btnA.setOnClickListener {
            gameEngine.guess("A")
        }
        btnB.setOnClickListener {
            gameEngine.guess("B")
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
        guess: String,
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

        val locations: List<GuitarLocation> = gameEngine.getNoteLocationsOnGuitar(note)
        val locationToVisualize = locations[0] // TODO: Randomly pick one from the locations (if more than 0 locations)
        println("To visualize: $locationToVisualize")

        showQuestionMark(locationToVisualize)
        println("Showed (or not) question mark on: $locationToVisualize")
    }

    private fun showQuestionMark(
        location: GuitarLocation
    ) {
        when {
            location.string == 1 -> {
                val parent = findViewById<View>(R.id.string1)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    previousText = child.text.toString()
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 2 -> {
                val parent = findViewById<View>(R.id.string2)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 3 -> {
                val parent = findViewById<View>(R.id.string3)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 4 -> {
                val parent = findViewById<View>(R.id.string4)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 5 -> {
                val parent = findViewById<View>(R.id.string5)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
            }
            location.string == 6 -> {
                val parent = findViewById<View>(R.id.string6)
                val child: TextView? = getChildByFret(parent, location.fret)
                if (child != null) {
                    child.visibility = View.VISIBLE
                    child.text = "?"
                    currentTextView = child
                } else {
                    println("Could not find a textview for guitarLocation $location")
                }
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

    override fun gameStarted() {
//        val str = "Game started!"
//        println(str)
//        Toast.makeText(this, str, Toast.LENGTH_SHORT)
//            .show()
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
