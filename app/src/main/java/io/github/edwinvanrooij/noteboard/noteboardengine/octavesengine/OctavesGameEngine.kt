package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.*
import java.util.*

class OctavesGameEngine : IOctavesGameEngine {

    private lateinit var gameListener: IOctavesGameListener
    private lateinit var gameSettings: OctavesGameSettings

    private var gameStarted: Boolean = false
    private var score: Int = 0
    private var accuracy: Double = 0.00
    private var currentNote: Note? = null
    private lateinit var notesQueue: Queue<Note>
    private lateinit var correctlyGuessedNotes: ArrayList<Note>
    private lateinit var incorrectlyGuessedNotes: ArrayList<Note>

    override fun initialize(gameSettings: OctavesGameSettings) {
        this.gameSettings = gameSettings
    }

    override fun start() {
        checkStartPrerequisites()
        if (gameStarted) {
            throw GameAlreadyStartedException("Trying to start the game, but the game was already started.")
        }

        // (Re)set game stats
        notesQueue = LinkedList<Note>(
            arrayListOf(
                Note(C, 4), Note(D, 4), Note(E, 4), Note(F, 4),
                Note(G, 4), Note(A, 4), Note(B, 4)
            ).shuffled()
        )
        correctlyGuessedNotes = ArrayList()
        incorrectlyGuessedNotes = ArrayList()
        score = 0
        accuracy = 0.00

        // Start the game
        gameStarted = true
        gameListener.onGameStart()

        // Start the first round
        executeNextNoteRoutine()
    }

    /**
     * Picks a new note for the user to guess.
     * Occurs once game was started, and after every guess.
     */
    private fun executeNextNoteRoutine() {
        val polledNote = notesQueue.poll()
        if (polledNote == null) {
            // Polled note is null, so the queue is empty. We've had all notes, end the game.
            stopGame()
            return
        }

        currentNote = polledNote // set the just-picked non-null note as the new currentNote

        gameListener.onNewNote(currentNote!!)
    }

    private fun stopGame() {
        currentNote = null
        gameStarted = false
        val gameResults = generateGameResults()
        gameListener.onGameStop(gameResults)
    }

    private fun generateGameResults(): OctavesGameResults {
        val points = score * (accuracy * 100).toInt()
        return OctavesGameResults(score, accuracy, points)
    }

    override fun stop() {
        checkGamePrerequisites()
        stopGame()
    }

    override fun guess(noteName: NoteName) {
        checkGamePrerequisites()

        if (currentNote == null) {
            throw Exception("Should be impossible to reach here: currentNote is null upon guess method execution.")
        }

        handleGuess(noteName)
    }

    /**
     * Handle a user-submitted note guess.
     */
    private fun handleGuess(noteName: NoteName) {
        @Suppress("LiftReturnOrAssignment")
        if (currentNote!!.noteName == noteName) {
            // Correct guess (note name equals guessed note name, count as correct)
            onCorrectGuess()
        } else {
            // Incorrect guess (note name does not equal guessed note name count as incorrect)
            onIncorrectGuess(noteName)
        }
    }

    /**
     * Occurs when the user guessed [currentNote] incorrectly.
     */
    private fun onIncorrectGuess(noteName: NoteName) {
        incorrectlyGuessedNotes.add(currentNote!!)
        // score remains unchanged
        gameListener.onScoreChange(score)
        updateAccuracy()
        gameListener.onIncorrectGuess(correctNote = currentNote!!, guessedNote = noteName)
        executeNextNoteRoutine()
    }

    /**
     * Occurs when the user guessed [currentNote] correctly.
     */
    private fun onCorrectGuess() {
        correctlyGuessedNotes.add(currentNote!!)
        score++ // add one point to score
        gameListener.onScoreChange(score)
        updateAccuracy()
        gameListener.onCorrectGuess(currentNote!!)
        executeNextNoteRoutine()
    }

    private fun updateAccuracy() {
        // accuracy = correctGuesses / totalGuesses
        accuracy = correctlyGuessedNotes.size.toDouble() /
                (correctlyGuessedNotes.size.toDouble() + incorrectlyGuessedNotes.size.toDouble())
        gameListener.onAccuracyChange(accuracy)
    }

    override fun setGameListener(gameListener: IOctavesGameListener) {
        if (this::gameListener.isInitialized) {
            throw GameListenerAlreadySetException("There is already a GameListener set.")
        }
        this.gameListener = gameListener
    }

    /**
     * Checks if all necessary preparation is done prior to executing start().
     */
    private fun checkStartPrerequisites() {
        if (!::gameListener.isInitialized) {
            throw GameListenerNotSetException("There is no gameListener set. Did you forget to set the game listener before starting a game?")
        }
        if (!::gameSettings.isInitialized) {
            throw GameSettingsNotSetException("Guitar is not initialized. Did you forget to set the FretsGameSettings?")
        }
    }

    /**
     * Checks if all necessary preparation is done prior to executing methods in game.
     */
    private fun checkGamePrerequisites() {
        checkStartPrerequisites()
        if (!gameStarted) {
            throw GameNotStartedException("Game was not started yet! Did you forget to execute start() on the FretsGameEngine?")
        }
    }
}
