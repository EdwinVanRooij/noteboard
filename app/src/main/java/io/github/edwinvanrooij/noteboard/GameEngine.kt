package io.github.edwinvanrooij.noteboard

class GameEngine {


  fun start() {
    println("Game has started.")

    // start timer
    //
  }

  private lateinit var onClockChangeListener: OnClockChangeListener
  private lateinit var onScoreChangeListener: OnScoreChangeListener
  private lateinit var onAccuracyChangeListener: OnAccuracyChangeListener
  private lateinit var onGameStartedListener: OnGameStartedListener
  private lateinit var onGameEndedListener: OnGameEndedListener

  fun setOnClockChangeListener(onClockChangeListener: OnClockChangeListener) {
    this.onClockChangeListener = onClockChangeListener
  }

  fun setOnScoreChangeListener(onScoreChangeListener: OnScoreChangeListener) {
    this.onScoreChangeListener = onScoreChangeListener
  }

  fun setOnAccuracyChangeListener(onAccuracyChangeListener: OnAccuracyChangeListener) {
    this.onAccuracyChangeListener = onAccuracyChangeListener
  }

  fun setOnGameStartedListener(onGameStartedListener: OnGameStartedListener) {
    this.onGameStartedListener = onGameStartedListener
  }

  fun setOnGameEndedListener(onGameEndedListener: OnGameEndedListener) {
    this.onGameEndedListener = onGameEndedListener
  }
}
