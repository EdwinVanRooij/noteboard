package io.github.edwinvanrooij.noteboard.lib.music

import io.github.edwinvanrooij.noteboard.lib.exceptions.NoteException
import io.github.edwinvanrooij.noteboard.lib.music.NoteName.*

class Note(
    val noteName: NoteName,
    val octave: Int
) {

    /**
     * Returns the distance in semitones between this and the next note.
     */
    fun getDistanceBetweenNoteAbove(): Int {
        return if (noteName == B || noteName == E) { // Only between B to C and E to F, the distance is 1 semitone
            1
        } else { // In all other cases, the distance between two notes is 2 semitones
            2
        }
    }

    /**
     * Returns the next [Note] relative to this note in the musical alphabet, including its [octave].
     */
    fun getNoteAbove(): Note {
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
    fun getNoteBelow(): Note {
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
        val otherNote = other as Note
        return this.noteName == otherNote.noteName && this.octave == otherNote.octave
    }

    operator fun compareTo(otherNote: Note): Int {
        // This note is in a lower octave, so we compare lower than the other note
        if (this.octave < otherNote.octave) {
            return -1
        }

        // This note is in a higher octave, so we compare higher than the other note
        if (this.octave > otherNote.octave) {
            return 1
        }

        // The notes are in an equal octave, compare the NoteNames
        if (this.octave == otherNote.octave) {
            if (this.noteName.ordinal > otherNote.noteName.ordinal) {
                // The ordinal of the NoteName goes from low to high; C is the lowest, B is the highest note in an octave.
                // This note's ordinal is higher, so we compare higher
                return 1
            } else if (this.noteName.ordinal < otherNote.noteName.ordinal) {
                // This note's ordinal is lower, so we compare lower
                return -1
            } else {
                // Ordinals are equal, so both notes are equal in pitch
                return 0
            }
        }

        throw Exception("Should be impossible to get here. Comparing pitch of notes to each other.")
    }
}