package io.github.edwinvanrooij.noteboard.lib.guitar

import io.github.edwinvanrooij.noteboard.lib.music.Note
import io.github.edwinvanrooij.noteboard.lib.exceptions.NoteOutOfBoundsException
import io.github.edwinvanrooij.noteboard.lib.music.NoteName

class GuitarString(
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
        currentNote = openNote.getNextNote()

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

            currentNote = currentNote.getNextNote()
        }

        return result
    }

    /**
     * Returns the location on this string where the given [note] can be played.
     * Throws a [NoteOutOfBoundsException] if the given [note] can not be played on this string.
     */
    fun getFretLocationByNote(note: Note): FretLocation {
        if (note < openNote) {
            throw NoteOutOfBoundsException("Note '$note' is lower than the open note '$openNote' of this string.")
        }
        // todo; if note is higher than highest playable note on this string, throw outofbounds
        
        // todo; success case; return the correct location
        TODO()
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
