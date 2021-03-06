package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.NoteOutOfBoundsException

class Guitar(
    frets: Int // amount of frets on the io.github.edwinvanrooij.guitar
) {
    private val guitarStrings = ArrayList<GuitarString>()

    init {
        // Add all 6 io.github.edwinvanrooij.guitar strings on standard tuning
        guitarStrings.addAll(
            arrayOf(
                GuitarString(1, Note(NoteName.E, 4), frets), // string 1
                GuitarString(2, Note(NoteName.B, 3), frets), // string 2
                GuitarString(3, Note(NoteName.G, 3), frets), // string 3
                GuitarString(4, Note(NoteName.D, 3), frets), // string 4
                GuitarString(5, Note(NoteName.A, 2), frets), // string 5
                GuitarString(6, Note(NoteName.E, 2), frets) // string 6
            )
        )
    }

    /**
     * Returns an array of all playable notes using the fretboard of this guitar.
     * This means there may be multiple items of the same note in the list, if they can be played on multiple frets.
     */
    fun getAllNotes(): List<Note> {
        val result = ArrayList<Note>()
        for (string in guitarStrings) {
            result.addAll(string.getPlayableNotes())
        }
        return result
    }

    /**
     * Returns the [FretLocation] where the given note can be played on this [Guitar].
     * Picks a random location if it can be played on multiple locations.
     */
    fun getFretLocation(note: Note): FretLocation {
        val locations = ArrayList<FretLocation>()
        for (string in guitarStrings) {
            try {
                // Try to get the note from each string
                locations.add(string.getFretLocationByNote(note))
            } catch (ignored: NoteOutOfBoundsException) {}
        }

        if (locations.size == 0) {
            // No strings could find a location for this note, throw exception
            throw NoteOutOfBoundsException("No string on this guitar could play the note $note")
        }

        // We have at least one FretLocation, pick one and return it
        return locations.shuffled().take(1)[0]
    }
}