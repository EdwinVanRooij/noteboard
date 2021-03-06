package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar

import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.NoteOutOfBoundsException
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import org.junit.Test

import org.junit.Assert.*

class GuitarTest {

    /**
     * Test if a 19-fret guitar has 72 playable whole notes
     *
     * 6: 12
     * 5: 12
     * 4: 12
     * 3: 12
     * 2: 12
     * 1: 12
     *
     * Should be 72
     */
    @Test
    fun getAllNotes() {
        val guitar = Guitar(19)
        val amountOfNotes = guitar.getAllNotes().size
        assertEquals(72, amountOfNotes)
    }

    /**
     * Test if we correctly get one of the locations where some notes are played.
     */
    @Test
    fun getFretLocation() {
        val guitar = Guitar(19)

        val e2 = Note(NoteName.E, 2) // lowest
        val b5 = Note(NoteName.B, 5) // highest
        val c4 = Note(NoteName.C, 4) // middle

        val expected1 = FretLocation(6, 0)
        val actual1 = guitar.getFretLocation(e2)

        val expected2 = FretLocation(1, 19)
        val actual2 = guitar.getFretLocation(b5)

        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)

        // Check if the returned location is one of the actually possible locations
        val fretLocation = guitar.getFretLocation(c4)
        var found = false
        val possibleLocations = arrayOf(
            FretLocation(2, 1),
            FretLocation(3, 5),
            FretLocation(4, 10),
            FretLocation(5, 15)
        )
        for (l in possibleLocations) {
            if (fretLocation == l) {
                found = true
            }
        }
        assertTrue(found)
    }

    @Test(expected = NoteOutOfBoundsException::class)
    fun `getFretLocation -- check if exception is thrown if note is unplayable on this guitar`() {
        val guitar = Guitar(19)
        guitar.getFretLocation(Note(NoteName.D, 2)) // lower than the lowest
        guitar.getFretLocation(Note(NoteName.C, 6)) // higher than the highest
    }
}