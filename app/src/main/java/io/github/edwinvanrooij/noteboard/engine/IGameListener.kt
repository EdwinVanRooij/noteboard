package io.github.edwinvanrooij.noteboard.engine

import io.github.edwinvanrooij.noteboard.engine.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.engine.music.Note
import io.github.edwinvanrooij.noteboard.engine.music.NoteName

interface IGameListener {

    /**
     * Occurs when a new game starts.
     */
    fun onGameStart()

    /**
     * Occurs when a game has stopped.
     */
    fun onGameStop(results: GameResults)

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