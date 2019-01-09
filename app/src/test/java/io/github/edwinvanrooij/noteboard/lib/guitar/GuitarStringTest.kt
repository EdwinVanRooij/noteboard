package io.github.edwinvanrooij.noteboard.lib.guitar

import org.junit.Test

import org.junit.Assert.*
import io.github.edwinvanrooij.noteboard.lib.music.Note
import io.github.edwinvanrooij.noteboard.lib.music.NoteName

class GuitarStringTest {

    @Test
    fun getPlayableNotes() {
        // On a standard tuned io.github.edwinvanrooij.guitar, the E2 string with 20 frets should have 13 playable notes
        val e2String = GuitarString(Note(NoteName.E, 2), 20)
        assertTrue(e2String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the A2 string with 20 frets should have 13 playable notes
        val a2String = GuitarString(Note(NoteName.A, 2), 20)
        assertTrue(a2String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the D3 string with 20 frets should have 12 playable notes
        val d3String = GuitarString(Note(NoteName.D, 3), 20)
        assertTrue(d3String.getPlayableNotes().size == 12)

        // On a standard tuned io.github.edwinvanrooij.guitar, the G3 string with 20 frets should have 12 playable notes
        val g3String = GuitarString(Note(NoteName.G, 3), 20)
        assertTrue(g3String.getPlayableNotes().size == 12)

        // On a standard tuned io.github.edwinvanrooij.guitar, the B3 string with 20 frets should have 12 playable notes
        val b3String = GuitarString(Note(NoteName.B, 3), 20)
        assertTrue(b3String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the B3 string with 20 frets should have 12 playable notes
        val e4String = GuitarString(Note(NoteName.E, 4), 20)
        assertTrue(e4String.getPlayableNotes().size == 13)
    }
}