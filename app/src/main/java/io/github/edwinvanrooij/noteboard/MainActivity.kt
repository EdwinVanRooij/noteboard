package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.btnPlay

class MainActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val g = GameEngine()

    // Listen to new time
    g.setOnClockChangeListener(OnClockChangeListener { secondsLeft ->
      val str = "$secondsLeft seconds left"
      println(str)
      Toast.makeText(this, str, Toast.LENGTH_SHORT)
          .show()
    })

    // Listen to new score
    g.setOnScoreChangeListener(OnScoreChangeListener { newScore ->
      val str = "$newScore is the new score"
      println(str)
      Toast.makeText(this, str, Toast.LENGTH_SHORT)
          .show()
    })

    // Listen to new accuracy
    g.setOnAccuracyChangeListener(OnAccuracyChangeListener { newAccuracy ->
      val str = "$newAccuracy is the new accuracy"
      println(str)
      Toast.makeText(this, str, Toast.LENGTH_SHORT)
          .show()
    })

    // Listen to game started
    g.setOnGameStartedListener(OnGameStartedListener {
      println("Game started!")
    })

    // Listen to game ended
    g.setOnGameEndedListener(OnGameEndedListener {
      println("Game ended!")
    })

    btnPlay.setOnClickListener {
      g.start()
    }
  }
}
