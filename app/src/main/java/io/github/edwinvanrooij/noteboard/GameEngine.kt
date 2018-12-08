package io.github.edwinvanrooij.noteboard

import java.security.InvalidParameterException

class GameEngine {

  private lateinit var gameListener: GameListener

  private var unpickedNotes: List<Note> = ArrayList()
  private var pickedNotes: List<Note> = ArrayList()

  private var currentNote: Note? = null

  init {
    // TODO: Initialize unpicked notes
    unpickedNotes = generateUnpickedNotes()
  }

  fun generateUnpickedNotes(): List<Note> {
    val result = ArrayList<Note>()

    val lowestNote = Note(name = "F", octave = 2)
    val highestNote = Note(name = "D", octave = 6)

    result.addAll(generateNotesInclusiveBetween(lowestNote, highestNote))

    return result
  }

  fun generateNotesInclusiveBetween(
    lowestNote: Note,
    highestNote: Note
  ): List<Note> {
    val result = ArrayList<Note>()

    var allNoteNames = arrayOf("A", "B", "C", "D", "E", "F", "G")
    // TODO: Add the lowest note + all notes above it in the current octave (if any)
    result.addAll(generateNotesInOctaveInclusiveAbove(lowestNote))

    // TODO: Check if there are any whole octaves between the lowest & highest, if so --> add every note in that octave
    val octaveNumbers: List<Int> = getOctaveNumbersBetween(highestNote.octave, lowestNote.octave)

    // TODO: Add the highest note and all notes below it in its octave (if any)
    result.addAll(generateNotesInOctaveInclusiveBelow(highestNote))

    return result
  }

  fun getOctaveNumbersBetween(
    highestOctave: Int,
    lowestOctave: Int
  ): List<Int> {
    val octaveNumbers = ArrayList<Int>()
    val numberOfOctavesBetween = highestOctave - lowestOctave
    if (numberOfOctavesBetween > 1) {
      for (i in 1 until numberOfOctavesBetween) {
        octaveNumbers.add(lowestOctave + i)
      }
    }
    return octaveNumbers
  }

  fun generateNotesInOctaveInclusiveAbove(lowestNote: Note): Collection<Note> {
    val result = ArrayList<Note>()
    result.add(lowestNote)

    // Highest possible note is G, return empty list if the lowest given note is G
    if (lowestNote.name == "G") {
      return result
    }

    // Check for each other character in the musical alphabet
    if (lowestNote.name == "F") {
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }
    if (lowestNote.name == "E") {
      result.add(Note(name = "F", octave = lowestNote.octave))
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }
    if (lowestNote.name == "D") {
      result.add(Note(name = "E", octave = lowestNote.octave))
      result.add(Note(name = "F", octave = lowestNote.octave))
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }
    if (lowestNote.name == "C") {
      result.add(Note(name = "D", octave = lowestNote.octave))
      result.add(Note(name = "E", octave = lowestNote.octave))
      result.add(Note(name = "F", octave = lowestNote.octave))
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }
    if (lowestNote.name == "B") {
      result.add(Note(name = "C", octave = lowestNote.octave))
      result.add(Note(name = "D", octave = lowestNote.octave))
      result.add(Note(name = "E", octave = lowestNote.octave))
      result.add(Note(name = "F", octave = lowestNote.octave))
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }
    if (lowestNote.name == "A") {
      result.add(Note(name = "B", octave = lowestNote.octave))
      result.add(Note(name = "C", octave = lowestNote.octave))
      result.add(Note(name = "D", octave = lowestNote.octave))
      result.add(Note(name = "E", octave = lowestNote.octave))
      result.add(Note(name = "F", octave = lowestNote.octave))
      result.add(Note(name = "G", octave = lowestNote.octave))
      return result
    }

    throw InvalidParameterException("No recognized note name '${lowestNote.name}'")
  }

  fun generateNotesInOctaveInclusiveBelow(highestNote: Note): Collection<Note> {
    val result = ArrayList<Note>()
    result.add(highestNote)

    // Lowest possible note is A, return empty list if the lowest given note is A
    if (highestNote.name == "A") {
      return result
    }

    // Check for each other character in the musical alphabet
    if (highestNote.name == "B") {
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }
    if (highestNote.name == "C") {
      result.add(Note(name = "B", octave = highestNote.octave))
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }
    if (highestNote.name == "D") {
      result.add(Note(name = "C", octave = highestNote.octave))
      result.add(Note(name = "B", octave = highestNote.octave))
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }
    if (highestNote.name == "E") {
      result.add(Note(name = "D", octave = highestNote.octave))
      result.add(Note(name = "C", octave = highestNote.octave))
      result.add(Note(name = "B", octave = highestNote.octave))
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }
    if (highestNote.name == "F") {
      result.add(Note(name = "E", octave = highestNote.octave))
      result.add(Note(name = "D", octave = highestNote.octave))
      result.add(Note(name = "C", octave = highestNote.octave))
      result.add(Note(name = "B", octave = highestNote.octave))
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }
    if (highestNote.name == "G") {
      result.add(Note(name = "F", octave = highestNote.octave))
      result.add(Note(name = "E", octave = highestNote.octave))
      result.add(Note(name = "D", octave = highestNote.octave))
      result.add(Note(name = "C", octave = highestNote.octave))
      result.add(Note(name = "B", octave = highestNote.octave))
      result.add(Note(name = "A", octave = highestNote.octave))
      return result
    }

    throw InvalidParameterException("No recognized note name '${highestNote.name}'")
  }

  fun start() {
    println("Game has started.")

    // TODO: start timer
    // TODO: (re)set score
    // TODO: (re)set accuracy
    // TODO: pick a random note which is on the guitar fretboard
  }

  fun setGameListener(gameListener: GameListener) {
    this.gameListener = gameListener
  }

  fun getNextNote(): Note {
    return Note(name = "F", octave = 2)
  }
}
