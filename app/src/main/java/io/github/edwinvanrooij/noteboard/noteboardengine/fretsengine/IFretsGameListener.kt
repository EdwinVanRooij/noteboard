package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar.FretLocation

interface IFretsGameListener {

    /**
     * Occurs when a new game starts.
     */
    fun onGameStart()

    /**
     * Occurs when a game has stopped.
     */
    fun onGameStop(resultsFrets: FretsGameResults)

    /**
     * Occurs when a new [note] was picked, to be guessed at the given fret [location].
     */
    fun onNewNote(note: Note, location: FretLocation)

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