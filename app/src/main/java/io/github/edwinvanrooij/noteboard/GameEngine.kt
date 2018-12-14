package io.github.edwinvanrooij.noteboard

import java.security.InvalidParameterException
import java.util.LinkedList
import java.util.Queue
import kotlin.collections.ArrayList

class GameEngine {

    private lateinit var gameListener: GameListener

    private var score: Int = 0
    private var accuracy: Double = 0.0

    private var unpickedNotes: Queue<Note> = LinkedList()

    private var correctlyGuessedNotes: ArrayList<Note>
    private var incorrectlyGuessedNotes: ArrayList<Note>

    private lateinit var currentNote: Note
    private lateinit var guessedNote: NoteName

    private var isStarted: Boolean = false

    init {
        unpickedNotes.addAll(generateUnpickedNotes().shuffled()) // add all notes, shuffled
        correctlyGuessedNotes = ArrayList()
        incorrectlyGuessedNotes = ArrayList()
    }

    fun restart() {
        isStarted = false
        start()
    }

    fun start() {
        if (isStarted) {
            return
        }

        isStarted = true
        gameListener.onGameStart()

        resetScore()
        resetAccuracy()
        nextNote()
    }

    private fun resetAccuracy() {
        accuracy = 0.00
        gameListener.onAccuracyChange(accuracy)
    }

    private fun resetScore() {
        score = 0
        gameListener.onScoreChange(score)
    }

    /**
     * Called by the user: which note did the end user pick?
     */
    fun guess(noteName: NoteName) {
        if (!isStarted) {
            return
        }

        guessedNote = noteName

        handleGuess()
    }

    private fun handleGuess() {
        // Check if the guessed note is equal to the current note name
        if (guessedNote == currentNote.name) {

            // Note was guessed correctly
            gameListener.onCorrectGuess(currentNote)
            correctlyGuessedNotes.add(currentNote)

            recalculateScore()
            recalculateAccuracy()
        } else {

            // Note was guessed incorrectly
            gameListener.onIncorrectGuess(guessedNote, currentNote)
            incorrectlyGuessedNotes.add(currentNote)

            recalculateScore()
            recalculateAccuracy()
        }
    }

    private fun recalculateAccuracy() {
        val amountOfNotesGuessed = correctlyGuessedNotes.size + incorrectlyGuessedNotes.size
        accuracy = (correctlyGuessedNotes.size).toDouble() / (amountOfNotesGuessed).toDouble()
        gameListener.onAccuracyChange(accuracy)
    }

    private fun recalculateScore() {
        score = correctlyGuessedNotes.size
        gameListener.onScoreChange(score)
    }

    fun nextNote() {
        if (unpickedNotes.size == 0) {
            unpickedNotes.addAll(generateUnpickedNotes().shuffled()) // add all notes, shuffled
        }
        val pickedNote = unpickedNotes.poll()
        currentNote = pickedNote
        gameListener.onNewNote(pickedNote)
    }

    fun setGameListener(gameListener: GameListener) {
        this.gameListener = gameListener
    }

    fun generateUnpickedNotes(): List<Note> {
        val result = ArrayList<Note>()

        val lowestNote = Note(name = NoteName.E, octave = 2) // lowest note on a standard-tuned guitar
        val highestNote = Note(name = NoteName.C, octave = 6) // highest note on a standard-tuned 22 fret guitar

        val notes = generateNotesInclusiveBetween(lowestNote, highestNote)
        for (n in notes) {
            val amountOfLocations = getNoteLocationsOnGuitar(n).size
            for (j in 0 until amountOfLocations) {
                result.add(n.copy())
            }
        }

        return result
    }

    fun generateNotesInclusiveBetween(
        lowestNote: Note,
        highestNote: Note
    ): List<Note> {
        val result = ArrayList<Note>()

        // Add the lowest note + all notes above it in the current octave
        result.addAll(generateNotesInOctaveInclusiveAbove(lowestNote))

        // Check if there are any whole octaves between the lowest & highest, if so --> add every note in that octave
        val octaveNumbers: List<Int> = getOctaveNumbersBetween(highestNote.octave, lowestNote.octave)
        octaveNumbers.forEach { i ->
            result.addAll(generateAllNotesInOctave(i))
        }

        // Add the highest note and all notes below it in its octave (if any)
        result.addAll(generateNotesInOctaveInclusiveBelow(highestNote))

        return result
    }

    fun generateAllNotesInOctave(octave: Int): Collection<Note> {
        val result = ArrayList<Note>()
        val allNoteNames = arrayOf(NoteName.C, NoteName.D, NoteName.E, NoteName.F, NoteName.G, NoteName.A, NoteName.B)
        allNoteNames.forEach { name ->
            result.add(Note(name = name, octave = octave))
        }
        return result
    }

    fun getOctaveNumbersBetween(
        highestOctave: Int,
        lowestOctave: Int
    ): List<Int> {
        val octaveNumbers = ArrayList<Int>()
        val numberOfOctavesBetween = highestOctave - lowestOctave
        if (numberOfOctavesBetween > 1) {
            for (i in 1 until numberOfOctavesBetween) {
                octaveNumbers.add(lowestOctave + i)
            }
        }
        return octaveNumbers
    }

    fun generateNotesInOctaveInclusiveAbove(lowestNote: Note): Collection<Note> {
        val result = ArrayList<Note>()
        result.add(lowestNote)

        // Highest possible note is B, return empty list if the lowest given note is G
        if (lowestNote.name == NoteName.B) {
            return result
        }

        // Check for each other character in the musical alphabet
        if (lowestNote.name == NoteName.A) {
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }
        if (lowestNote.name == NoteName.G) {
            result.add(Note(name = NoteName.A, octave = lowestNote.octave))
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }
        if (lowestNote.name == NoteName.F) {
            result.add(Note(name = NoteName.G, octave = lowestNote.octave))
            result.add(Note(name = NoteName.A, octave = lowestNote.octave))
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }
        if (lowestNote.name == NoteName.E) {
            result.add(Note(name = NoteName.F, octave = lowestNote.octave))
            result.add(Note(name = NoteName.G, octave = lowestNote.octave))
            result.add(Note(name = NoteName.A, octave = lowestNote.octave))
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }
        if (lowestNote.name == NoteName.D) {
            result.add(Note(name = NoteName.E, octave = lowestNote.octave))
            result.add(Note(name = NoteName.F, octave = lowestNote.octave))
            result.add(Note(name = NoteName.G, octave = lowestNote.octave))
            result.add(Note(name = NoteName.A, octave = lowestNote.octave))
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }
        if (lowestNote.name == NoteName.C) {
            result.add(Note(name = NoteName.D, octave = lowestNote.octave))
            result.add(Note(name = NoteName.E, octave = lowestNote.octave))
            result.add(Note(name = NoteName.F, octave = lowestNote.octave))
            result.add(Note(name = NoteName.G, octave = lowestNote.octave))
            result.add(Note(name = NoteName.A, octave = lowestNote.octave))
            result.add(Note(name = NoteName.B, octave = lowestNote.octave))
            return result
        }

        throw InvalidParameterException("No recognized note name '${lowestNote.name}'")
    }

    fun generateNotesInOctaveInclusiveBelow(highestNote: Note): Collection<Note> {
        val result = ArrayList<Note>()
        result.add(highestNote)

        // Lowest possible note is C, return empty list if the lowest given note is A
        if (highestNote.name == NoteName.C) {
            return result
        }

        // Check for each other character in the musical alphabet
        if (highestNote.name == NoteName.D) {
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }
        if (highestNote.name == NoteName.E) {
            result.add(Note(name = NoteName.D, octave = highestNote.octave))
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }
        if (highestNote.name == NoteName.F) {
            result.add(Note(name = NoteName.E, octave = highestNote.octave))
            result.add(Note(name = NoteName.D, octave = highestNote.octave))
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }
        if (highestNote.name == NoteName.G) {
            result.add(Note(name = NoteName.F, octave = highestNote.octave))
            result.add(Note(name = NoteName.E, octave = highestNote.octave))
            result.add(Note(name = NoteName.D, octave = highestNote.octave))
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }
        if (highestNote.name == NoteName.A) {
            result.add(Note(name = NoteName.G, octave = highestNote.octave))
            result.add(Note(name = NoteName.F, octave = highestNote.octave))
            result.add(Note(name = NoteName.E, octave = highestNote.octave))
            result.add(Note(name = NoteName.D, octave = highestNote.octave))
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }
        if (highestNote.name == NoteName.B) {
            result.add(Note(name = NoteName.A, octave = highestNote.octave))
            result.add(Note(name = NoteName.G, octave = highestNote.octave))
            result.add(Note(name = NoteName.F, octave = highestNote.octave))
            result.add(Note(name = NoteName.E, octave = highestNote.octave))
            result.add(Note(name = NoteName.D, octave = highestNote.octave))
            result.add(Note(name = NoteName.C, octave = highestNote.octave))
            return result
        }

        throw InvalidParameterException("No recognized note name '${highestNote.name}'")
    }

    /**
     * Returns a list of locations where this note can be played on the guitar, using standard tuning.
     */
    fun getNoteLocationsOnGuitar(note: Note): List<Fret> {
        val result = ArrayList<Fret>()

        // Lowest notes playable are on the 2nd octave
        when {
            note.octave == 2 -> when {
                // The E2 (E in the 2nd octave) can be played only on the 6th string
                note.name == NoteName.E -> result.add(Fret(string = 6, fret = 0))

                // The F2 (F in the 2nd octave) can be played only on the 6th string
                note.name == NoteName.F -> result.add(Fret(string = 6, fret = 1))

                // The G2 (G in the 2nd octave) can be played only on the 6th string
                note.name == NoteName.G -> result.add(Fret(string = 6, fret = 3))

                // The A2 (E in the 2nd octave) can be played on both the 6th and the 5th string
                note.name == NoteName.A -> {
                    result.add(Fret(string = 6, fret = 5))
                    result.add(Fret(string = 5, fret = 0))
                }

                // The B2 (E in the 2nd octave) can be played on both the 6th and the 5th string
                note.name == NoteName.B -> {
                    result.add(Fret(string = 6, fret = 7))
                    result.add(Fret(string = 5, fret = 2))
                }
            }
            note.octave == 3 -> when {
                // The C3 can be played on both the 6th and the 5th string
                note.name == NoteName.C -> {
                    result.add(Fret(string = 6, fret = 8))
                    result.add(Fret(string = 5, fret = 3))
                }

                // D3
                note.name == NoteName.D -> {
                    result.add(Fret(string = 6, fret = 10))
                    result.add(Fret(string = 5, fret = 5))
                    result.add(Fret(string = 4, fret = 0))
                }

                // E3
                note.name == NoteName.E -> {
                    result.add(Fret(string = 6, fret = 12))
                    result.add(Fret(string = 5, fret = 7))
                    result.add(Fret(string = 4, fret = 2))
                }

                // F3
                note.name == NoteName.F -> {
                    result.add(Fret(string = 6, fret = 13))
                    result.add(Fret(string = 5, fret = 8))
                    result.add(Fret(string = 4, fret = 3))
                }

                // G3
                note.name == NoteName.G -> {
                    result.add(Fret(string = 6, fret = 15))
                    result.add(Fret(string = 5, fret = 10))
                    result.add(Fret(string = 4, fret = 5))
                    result.add(Fret(string = 3, fret = 0))
                }

                // A3
                note.name == NoteName.A -> {
                    result.add(Fret(string = 6, fret = 17))
                    result.add(Fret(string = 5, fret = 12))
                    result.add(Fret(string = 4, fret = 7))
                    result.add(Fret(string = 3, fret = 2))
                }

                // B3
                note.name == NoteName.B -> {
                    result.add(Fret(string = 6, fret = 19))
                    result.add(Fret(string = 5, fret = 14))
                    result.add(Fret(string = 4, fret = 9))
                    result.add(Fret(string = 3, fret = 4))
                    result.add(Fret(string = 2, fret = 0))
                }
            }
            note.octave == 4 -> when {
                // C4
                note.name == NoteName.C -> {
                    result.add(Fret(string = 6, fret = 20))
                    result.add(Fret(string = 5, fret = 15))
                    result.add(Fret(string = 4, fret = 10))
                    result.add(Fret(string = 3, fret = 5))
                    result.add(Fret(string = 2, fret = 1))
                }

                // D4
                note.name == NoteName.D -> {
                    result.add(Fret(string = 5, fret = 17))
                    result.add(Fret(string = 4, fret = 12))
                    result.add(Fret(string = 3, fret = 7))
                    result.add(Fret(string = 2, fret = 3))
                }

                // E4
                note.name == NoteName.E -> {
                    result.add(Fret(string = 5, fret = 19))
                    result.add(Fret(string = 4, fret = 14))
                    result.add(Fret(string = 3, fret = 9))
                    result.add(Fret(string = 2, fret = 5))
                    result.add(Fret(string = 1, fret = 0))
                }

                // F4
                note.name == NoteName.F -> {
                    result.add(Fret(string = 5, fret = 20))
                    result.add(Fret(string = 4, fret = 15))
                    result.add(Fret(string = 3, fret = 10))
                    result.add(Fret(string = 2, fret = 6))
                    result.add(Fret(string = 1, fret = 1))
                }

                // G4
                note.name == NoteName.G -> {
                    result.add(Fret(string = 4, fret = 17))
                    result.add(Fret(string = 3, fret = 12))
                    result.add(Fret(string = 2, fret = 8))
                    result.add(Fret(string = 1, fret = 3))
                }

                // A4
                note.name == NoteName.A -> {
                    result.add(Fret(string = 4, fret = 19))
                    result.add(Fret(string = 3, fret = 14))
                    result.add(Fret(string = 2, fret = 10))
                    result.add(Fret(string = 1, fret = 5))
                }

                // B4
                note.name == NoteName.B -> {
                    result.add(Fret(string = 3, fret = 16))
                    result.add(Fret(string = 2, fret = 12))
                    result.add(Fret(string = 1, fret = 7))
                }
            }
            note.octave == 5 -> when {
                // C5
                note.name == NoteName.C -> {
                    result.add(Fret(string = 3, fret = 17))
                    result.add(Fret(string = 2, fret = 13))
                    result.add(Fret(string = 1, fret = 8))
                }

                // D5
                note.name == NoteName.D -> {
                    result.add(Fret(string = 3, fret = 19))
                    result.add(Fret(string = 2, fret = 15))
                    result.add(Fret(string = 1, fret = 10))
                }

                // E5
                note.name == NoteName.E -> {
                    result.add(Fret(string = 2, fret = 17))
                    result.add(Fret(string = 1, fret = 12))
                }

                // F5
                note.name == NoteName.F -> {
                    result.add(Fret(string = 2, fret = 18))
                    result.add(Fret(string = 1, fret = 13))
                }

                // G5
                note.name == NoteName.G -> {
                    result.add(Fret(string = 2, fret = 20))
                    result.add(Fret(string = 1, fret = 15))
                }

                // A5
                note.name == NoteName.A -> {
                    result.add(Fret(string = 1, fret = 17))
                }

                // B5
                note.name == NoteName.B -> {
                    result.add(Fret(string = 1, fret = 19))
                }
            }
            note.octave == 6 -> when {
                // C6
                note.name == NoteName.C -> {
                    result.add(Fret(string = 1, fret = 20))
                }
            }
        }

        if (result.isEmpty()) {
            throw Exception(
                "Could not find any valid guitar positions for the note $note. Are you sure this is playable on a 20-fret standard-tuned guitar?"
            )
        }

        return result
    }
}
