package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName

interface IOctavesGameListener {
    /**
     * Occurs when a new game starts.
     */
    fun onGameStart()

    /**
     * Occurs when a game has stopped.
     */
    fun onGameStop(results: OctavesGameResults)

    /**
     * Occurs when a new [note] was picked.
     */
    fun onNewNote(note: Note)

    /**
     * Occurs when the user's [accuracy] has changed.
     */
    fun onAccuracyChange(accuracy: Double)

    /**
     * Occurs when the user's [score] has changed.
     */
    fun onScoreChange(score: Int)

    /**
     * Occurs when the user guessed last [note] correctly.
     */
    fun onCorrectGuess(note: Note)

    /**
     * Occurs when the user guessed last [correctNote] incorrectly, by guessing [guessedNote].
     */
    fun onIncorrectGuess(correctNote: Note, guessedNote: NoteName)
}
