package io.github.edwinvanrooij.noteboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.btnA
import kotlinx.android.synthetic.main.activity_main.btnB
import kotlinx.android.synthetic.main.activity_main.btnC
import kotlinx.android.synthetic.main.activity_main.btnD
import kotlinx.android.synthetic.main.activity_main.btnE
import kotlinx.android.synthetic.main.activity_main.btnF
import kotlinx.android.synthetic.main.activity_main.btnG
import kotlinx.android.synthetic.main.activity_main.btnPlay
import kotlinx.android.synthetic.main.activity_main.txtAccuracy
import kotlinx.android.synthetic.main.activity_main.txtScore

class MainActivity : Activity(), GameListener {

  private var gameEngine: GameEngine = GameEngine()

  private var previousText: String = ""

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
      Toast.makeText(this, "Guessing C", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("C")
    }
    btnD.setOnClickListener {
      Toast.makeText(this, "Guessing D", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("D")
    }
    btnE.setOnClickListener {
      Toast.makeText(this, "Guessing E", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("E")
    }
    btnF.setOnClickListener {
      Toast.makeText(this, "Guessing F", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("F")
    }
    btnG.setOnClickListener {
      Toast.makeText(this, "Guessing G", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("G")
    }
    btnA.setOnClickListener {
      Toast.makeText(this, "Guessing A", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("A")
    }
    btnB.setOnClickListener {
      Toast.makeText(this, "Guessing B", Toast.LENGTH_SHORT)
          .show()
      gameEngine.guess("B")
    }
  }

  override fun onCorrectGuess(note: Note) {
    val str = "Correctly guessed $note"
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()

    Thread.sleep(1000) // sleep 1s on correct guess
    gameEngine.nextNote()
  }

  override fun onIncorrectGuess(
    guess: String,
    correct: Note
  ) {
    val str = "Incorrectly guessed: Guessed $guess, but was $correct"
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()

    Thread.sleep(1500) // sleep 1.5s on incorrect guess
    gameEngine.nextNote()
  }

  override fun onNewNote(note: Note) {
    val str = "New note picked: $note"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()

    // TODO: Check on which location(s) this note can be played on the guitar
    val locations: List<GuitarLocation> =  gameEngine.getNoteLocationsOnGuitar(note)

    // TODO: If more than one location, choose one of them at random

    // TODO: Put a question mark on the location
    showQuestionMark(string = 6, fret = 2)
  }

  private fun showQuestionMark(
    string: Int,
    fret: Int
  ) {
    // Find
    val textView = findViewById<View>(R.id.stringE2).findViewById<TextView>(R.id.fret4)
    previousText = textView.text.toString()
    textView.text = "?"
    textView.visibility = View.VISIBLE
  }

  override fun gameStarted() {
    val str = "Game started!"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
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
