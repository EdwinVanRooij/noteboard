package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.NoteOutOfBoundsException

class GuitarString(
    private val stringNumber: Int, // on a guitar, string 1 is the 'high'/'thin' E4 string, while string 6 is the 'thick' or 'low' E2 string
    private val openNote: Note,
    private val frets: Int // amount of frets beneath the string
) {

    /**
     * Generates all notes that can be played on this [openNote] string with [frets] amount of frets.
     */
    fun getPlayableNotes(): Collection<Note> {
        val result = ArrayList<Note>()

        var currentNote = openNote
        var currentFret = 0

        result.add(currentNote)
        currentNote = openNote.getNoteAbove()

        // Keep adding notes as playable as long as they fit on the fretboard
        while (thisNoteFitsOnFretboard(currentFret, currentNote, frets)) {

            // The note fits on the fretboard, add it
            result.add(currentNote)

            // Check how many frets we just moved
            @Suppress("LiftReturnOrAssignment")
            if (currentNote.noteName == NoteName.C || currentNote.noteName == NoteName.F) {
                // If the currently placed note was a C or an F, we only moved one step
                currentFret += 1
            } else {
                // In all other cases, we moved 2 steps
                currentFret += 2
            }

            currentNote = currentNote.getNoteAbove()
        }

        return result
    }

    /**
     * Returns the location on this string where the given [note] can be played.
     * Throws a [NoteOutOfBoundsException] if the given [note] can not be played on this string.
     */
    fun getFretLocationByNote(note: Note): FretLocation {
        // Check if note is below lowest note
        if (note < openNote) {
            throw NoteOutOfBoundsException("Note '$note' is lower than the open note '$openNote' of this string.")
        }

        // Check if note is above highest note
        if (note > getHighestStringNote()) {
            throw NoteOutOfBoundsException("Note '$note' is lower than the open note '$openNote' of this string.")
        }

        // Note is on this string, get the location
        var currentFret = 0
        var currentNote = openNote
        while (true) {
            if (currentNote == note) {
                // Current note equals the required note, return the location where we're currently at
                return FretLocation(stringNumber, currentFret)
            } else {
                // We haven't reached the required note yet, move one note up
                currentFret += currentNote.getDistanceBetweenNoteAbove()
                currentNote = currentNote.getNoteAbove()
            }
        }
    }

    /**
     * Returns the highest playable note on this string.
     */
    fun getHighestStringNote(): Note {
        var currentlyHighestNote = openNote
        var currentFret = 0

        while (true) {
            val nextNoteFretDistance =
                currentlyHighestNote.getDistanceBetweenNoteAbove() // 1 semitone = 1 fret on the guitar
            val nextNote = currentlyHighestNote.getNoteAbove()

            // Only change the currently highest note if the distance it takes to move from the current fret to the next fret fits on the guitar
            val nextFretDistance = currentFret + nextNoteFretDistance
            if (nextFretDistance <= frets) { // frets = max amount of frets on this guitar
                currentlyHighestNote = nextNote
                currentFret += nextNoteFretDistance
            } else {
                break
            }
        }

        return currentlyHighestNote
    }

    /**
     * Checks whether or not one more note fits on the fretboard.
     */
    private fun thisNoteFitsOnFretboard(currentFret: Int, nextNote: Note, maxFrets: Int): Boolean {
        if (nextNote.noteName == NoteName.C || nextNote.noteName == NoteName.F) {
            // If the next note is C or F, there's only one more fret required to fit
            if (currentFret < maxFrets) {
                return true
            }
            return false
        } else {
            // For any other note, there should be at least 2 more frets available
            if (currentFret + 1 < maxFrets) {
                return true
            }
            return false
        }
    }
}
