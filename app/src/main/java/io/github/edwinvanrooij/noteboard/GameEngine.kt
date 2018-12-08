package io.github.edwinvanrooij.noteboard

class GameEngine {


  fun start() {
    println("Game has started.")

    // start timer
  }

  private lateinit var gameListener: GameListener

  fun setGameListener(gameListener: GameListener) {
    this.gameListener = gameListener
  }
}
