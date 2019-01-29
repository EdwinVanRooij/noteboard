package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import org.junit.Test

import org.junit.Assert.*

class GuitarStringTest {

    @Test
    fun getHighestStringNote() {
        // My guitar/string
        val e4String = GuitarString(1, Note(E, 4), 20)
        val expectedNote1 = Note(C, 6)
        val actualNote1 = e4String.getHighestStringNote()
        assertEquals(actualNote1, expectedNote1)

        // My guitar/string
        val e2String = GuitarString(6, Note(E, 2), 20)
        val expectedNote2 = Note(C, 4)
        val actualNote2 = e2String.getHighestStringNote()
        assertEquals(actualNote2, expectedNote2)

        // Random guitar/string
        val g3String = GuitarString(3, Note(G, 3), 19)
        val expectedNote3 = Note(D, 5)
        val actualNote3 = g3String.getHighestStringNote()
        assertEquals(actualNote3, expectedNote3)

        // Random guitar/string
        val g3String2 = GuitarString(3, Note(G, 3), 20)
        val expectedNote4 = Note(D, 5)
        val actualNote4 = g3String2.getHighestStringNote()
        assertEquals(actualNote4, expectedNote4)
    }

    @Test
    fun getFretLocationByNote() {
        // Init some Guitar strings
        val e4String = GuitarString(1, Note(E, 4), 20)
        val g3String = GuitarString(3, Note(G, 3), 20)
        val e2String = GuitarString(6, Note(E, 2), 20)

        // At the e4 string, we should get the e5 location at fret 12
        val expected1 = e4String.getFretLocationByNote(Note(E, 5))
        val actual1 = FretLocation(1, 12)
        assertEquals(actual1, expected1)

        // At the g3 string, we should get the e5 location at fret 12
        val expected2 = g3String.getFretLocationByNote(Note(A, 3))
        val actual2 = FretLocation(3, 2)
        assertEquals(actual2, expected2)

        // At the e2 string, we should get the a3 location at fret 17
        val expected3 = e2String.getFretLocationByNote(Note(A, 3))
        val actual3 = FretLocation(6, 17)
        assertEquals(actual3, expected3)
    }

    @Test
    fun getPlayableNotes() {
        // On a standard tuned io.github.edwinvanrooij.guitar, the E2 string with 20 frets should have 13 playable notes
        val e2String = GuitarString(6, Note(E, 2), 20)
        assertTrue(e2String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the A2 string with 20 frets should have 13 playable notes
        val a2String = GuitarString(5, Note(A, 2), 20)
        assertTrue(a2String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the D3 string with 20 frets should have 12 playable notes
        val d3String = GuitarString(4, Note(D, 3), 20)
        assertTrue(d3String.getPlayableNotes().size == 12)

        // On a standard tuned io.github.edwinvanrooij.guitar, the G3 string with 20 frets should have 12 playable notes
        val g3String = GuitarString(3, Note(G, 3), 20)
        assertTrue(g3String.getPlayableNotes().size == 12)

        // On a standard tuned io.github.edwinvanrooij.guitar, the B3 string with 20 frets should have 12 playable notes
        val b3String = GuitarString(2, Note(B, 3), 20)
        assertTrue(b3String.getPlayableNotes().size == 13)

        // On a standard tuned io.github.edwinvanrooij.guitar, the B3 string with 20 frets should have 12 playable notes
        val e4String = GuitarString(1, Note(E, 4), 20)
        assertTrue(e4String.getPlayableNotes().size == 13)
    }
}