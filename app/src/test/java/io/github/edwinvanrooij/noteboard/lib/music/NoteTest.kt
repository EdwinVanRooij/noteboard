package io.github.edwinvanrooij.noteboard.lib.music

import io.github.edwinvanrooij.noteboard.lib.exceptions.NoteException
import org.junit.Test
import io.kotlintest.shouldThrow

import io.github.edwinvanrooij.noteboard.lib.music.NoteName.*

import org.junit.Assert.*

class NoteTest {

    @Test
    fun `test getNextNote`() {
        // Every note should go to the correct next note and stay in the same octave
        assertTrue(Note(C, 4).getNextNote() == Note(NoteName.D, 4))
        assertTrue(Note(D, 4).getNextNote() == Note(NoteName.E, 4))
        assertTrue(Note(E, 4).getNextNote() == Note(NoteName.F, 4))
        assertTrue(Note(F, 4).getNextNote() == Note(NoteName.G, 4))
        assertTrue(Note(G, 4).getNextNote() == Note(NoteName.A, 4))
        assertTrue(Note(A, 4).getNextNote() == Note(NoteName.B, 4))
        assertTrue(Note(B, 4).getNextNote() == Note(NoteName.C, 5)) // B is an exception, goes to next octave

        // Notes in this app can't go above C8 (highest playable note on the piano)
        shouldThrow<NoteException> { Note(C, 8).getNextNote() }
    }

    @Test
    fun `test getPreviousNote`() {
        // Every note should go to the correct previous note and stay in the same octave
        assertTrue(Note(C, 4).getPreviousNote() == Note(NoteName.B, 3)) // c is an exception, goes to previous octave
        assertTrue(Note(D, 4).getPreviousNote() == Note(NoteName.C, 4))
        assertTrue(Note(E, 4).getPreviousNote() == Note(NoteName.D, 4))
        assertTrue(Note(F, 4).getPreviousNote() == Note(NoteName.E, 4))
        assertTrue(Note(G, 4).getPreviousNote() == Note(NoteName.F, 4))
        assertTrue(Note(A, 4).getPreviousNote() == Note(NoteName.G, 4))
        assertTrue(Note(B, 4).getPreviousNote() == Note(NoteName.A, 4))

        // Notes can't go below the C in the octave 0 (C0)
        shouldThrow<NoteException> { Note(C, 0).getPreviousNote() }
    }
}