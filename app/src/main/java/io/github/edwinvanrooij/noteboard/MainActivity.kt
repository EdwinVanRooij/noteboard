package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.btnC
import kotlinx.android.synthetic.main.activity_main.btnD
import kotlinx.android.synthetic.main.activity_main.btnE
import kotlinx.android.synthetic.main.activity_main.btnF
import kotlinx.android.synthetic.main.activity_main.btnG
import kotlinx.android.synthetic.main.activity_main.btnA
import kotlinx.android.synthetic.main.activity_main.btnB
import kotlinx.android.synthetic.main.activity_main.btnPlay

class MainActivity : Activity(), GameListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val gameEngine = GameEngine()
    gameEngine.setGameListener(this)

    btnPlay.setOnClickListener {
      gameEngine.start()
    }

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

  override fun onCorrectGuess(note: Note?) {
  }

  override fun onIncorrectGuess(note: Note?) {
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
}
