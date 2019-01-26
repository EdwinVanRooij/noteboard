package io.github.edwinvanrooij.noteboard.noteboardengine

import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.NoteException
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*

import org.junit.Assert.*
import org.junit.Test

class NoteTest {

    @Test(expected = NoteException::class)
    fun `test getNextNote`() {
        // Every note should go to the correct next note and stay in the same octave
        assertTrue(Note(C, 4).getNoteAbove() == Note(NoteName.D, 4))
        assertTrue(Note(D, 4).getNoteAbove() == Note(NoteName.E, 4))
        assertTrue(Note(E, 4).getNoteAbove() == Note(NoteName.F, 4))
        assertTrue(Note(F, 4).getNoteAbove() == Note(NoteName.G, 4))
        assertTrue(Note(G, 4).getNoteAbove() == Note(NoteName.A, 4))
        assertTrue(Note(A, 4).getNoteAbove() == Note(NoteName.B, 4))
        assertTrue(Note(B, 4).getNoteAbove() == Note(NoteName.C, 5)) // B is an exception, goes to next octave

        // Notes in this app can't go above C8 (highest playable note on the piano)
        Note(C, 8).getNoteAbove() // exception
    }

    @Test(expected = NoteException::class)
    fun `test getPreviousNote`() {
        // Every note should go to the correct previous note and stay in the same octave
        assertTrue(Note(C, 4).getNoteBelow() == Note(NoteName.B, 3)) // c is an exception, goes to previous octave
        assertTrue(Note(D, 4).getNoteBelow() == Note(NoteName.C, 4))
        assertTrue(Note(E, 4).getNoteBelow() == Note(NoteName.D, 4))
        assertTrue(Note(F, 4).getNoteBelow() == Note(NoteName.E, 4))
        assertTrue(Note(G, 4).getNoteBelow() == Note(NoteName.F, 4))
        assertTrue(Note(A, 4).getNoteBelow() == Note(NoteName.G, 4))
        assertTrue(Note(B, 4).getNoteBelow() == Note(NoteName.A, 4))

        // Notes can't go below the C in the octave 0 (C0)
        Note(C, 0).getNoteBelow() // exception
    }
}