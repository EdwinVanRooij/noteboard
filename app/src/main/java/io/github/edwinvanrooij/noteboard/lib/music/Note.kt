package io.github.edwinvanrooij.noteboard.lib.music

import io.github.edwinvanrooij.noteboard.lib.exceptions.NoteException
import io.github.edwinvanrooij.noteboard.lib.music.NoteName.*

class Note(
    val noteName: NoteName,
    val octave: Int
) {

    /**
     * Returns the next [Note] relative to this note in the musical alphabet, including its [octave].
     */
    fun getNextNote(): Note {
        if (noteName == C && octave == 8) {
            throw NoteException("Notes in this app can't go above C8 (highest playable note on the piano)")
        }

        // C is the first note of the musical alphabet.
        when (noteName) {
            C -> return Note(D, octave)
            D -> return Note(E, octave)
            E -> return Note(F, octave)
            F -> return Note(G, octave)
            G -> return Note(A, octave)
            A -> return Note(B, octave)
            B -> return Note(C, octave + 1)
            else -> throw NoteException("Could not find the next note in the musical alphabet for noteName $noteName")
        }
    }

    /**
     * Returns the previous [Note] relative to this note in the musical alphabet, including its [octave].
     */
    fun getPreviousNote(): Note {
        if (noteName == C && octave == 0) {
            throw NoteException("Notes can't go below the C in the octave 0 (C0)")
        }

        // C is the first note of the musical alphabet.
        when (noteName) {
            C -> return Note(B, octave - 1)
            B -> return Note(A, octave)
            A -> return Note(G, octave)
            G -> return Note(F, octave)
            F -> return Note(E, octave)
            E -> return Note(D, octave)
            D -> return Note(C, octave)
            else -> throw NoteException("Could not find the next note in the musical alphabet for noteName $noteName")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (noteName != other.noteName) return false
        if (octave != other.octave) return false

        return true
    }

    operator fun compareTo(openNote: Note): Int {
        TODO("implement")
    }
}