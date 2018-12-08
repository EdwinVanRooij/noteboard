package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.btnPlay

class MainActivity : Activity(), GameListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val g = GameEngine()
    g.setGameListener(this)

    btnPlay.setOnClickListener {
      g.start()
    }
  }

  override fun onNewNote(note: Note?) {
    val str = "New note picked: $note"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }

  override fun gameStarted() {
    val str = "Game started!"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onClockChange(secondsLeft: Int) {
    val str = "$secondsLeft seconds left"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onAccuracyChange(accuracy: Int) {
    val str = "$accuracy is the new accuracy"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onScoreChange(newScore: Int) {
    val str = "$newScore is the new score"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }

  override fun gameEnded() {
    val str = "Game ended!"
    println(str)
    Toast.makeText(this, str, Toast.LENGTH_SHORT)
        .show()
  }
}
