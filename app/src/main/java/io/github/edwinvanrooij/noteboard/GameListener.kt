package io.github.edwinvanrooij.noteboard;

interface GameListener {
    fun onGameStart()
    fun onAccuracyChange(accuracy: Double)
    fun onScoreChange(newScore: Int)
    fun onNewNote(note: Note)
    fun onCorrectGuess(note: Note)
    fun onIncorrectGuess(guess: NoteName, correct: Note)
}
