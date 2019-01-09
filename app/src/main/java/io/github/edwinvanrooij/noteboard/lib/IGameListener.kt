package io.github.edwinvanrooij.noteboard.lib

import io.github.edwinvanrooij.noteboard.lib.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.lib.music.Note
import io.github.edwinvanrooij.noteboard.lib.music.NoteName

interface IGameListener {

    /**
     * Occurs when a new game starts.
     */
    fun onGameStart()

    /**
     * Occurs when a game has stopped.
     */
    fun onGameStop()

    /**
     * Occurs when a new fret [location] was picked for the user to guess the note for.
     */
    fun onNewFretLocation(location: FretLocation)

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
     * Occurs when the user guessed last [correctNote] incorrectly, by guessing [guessedNoteName].
     */
    fun onIncorrectGuess(guessedNoteName: NoteName, correctNote: Note)
}