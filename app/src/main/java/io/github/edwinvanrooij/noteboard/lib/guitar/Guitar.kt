package io.github.edwinvanrooij.noteboard.lib.guitar

import io.github.edwinvanrooij.noteboard.lib.music.Note
import io.github.edwinvanrooij.noteboard.lib.music.NoteName

class Guitar(
    val frets: Int // amount of frets on the io.github.edwinvanrooij.guitar
) {
    val guitarStrings = ArrayList<GuitarString>()

    init {
        // Add all 6 io.github.edwinvanrooij.guitar strings on standard tuning
        guitarStrings.addAll(
            arrayOf(
                GuitarString(Note(NoteName.E, 4), frets), // string 1
                GuitarString(Note(NoteName.B, 3), frets), // string 2
                GuitarString(Note(NoteName.G, 3), frets), // string 3
                GuitarString(Note(NoteName.D, 3), frets), // string 4
                GuitarString(Note(NoteName.A, 2), frets), // string 5
                GuitarString(Note(NoteName.E, 2), frets) // string 6
            )
        )
    }

    /**
     * Returns an array of all playable notes using the fretboard of this guitar.
     * This means there may be multiple items of the same note in the list, if they can be played on multiple frets.
     */
    fun getAllNotes() : List<Note>{
        return arrayListOf(
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4),
            Note(NoteName.C, 4)
        )
        // todo; implement
    }

    /**
     * Returns the [FretLocation] where the given note can be played on this [Guitar].
     * Picks a random location if it can be played on multiple locations.
     */
    fun getFretLocation(note: Note): FretLocation {
        return FretLocation(1, 1)
        // todo; implement
    }
}