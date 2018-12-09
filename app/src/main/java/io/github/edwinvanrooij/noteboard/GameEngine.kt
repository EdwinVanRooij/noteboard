package io.github.edwinvanrooij.noteboard

import java.security.InvalidParameterException
import java.util.LinkedList
import java.util.Queue

class GameEngine {

  private lateinit var gameListener: GameListener

  private var score: Int = 0
  private var accuracy: Double = 0.00

  private var unpickedNotes: Queue<Note>

  private var correctlyGuessedNotes: ArrayList<Note>
  private var incorrectlyGuessedNotes: ArrayList<Note>

  private var currentNote: Note? = null
  private var guessedNote: String? = null

  init {
    unpickedNotes = LinkedList() // create new queue
    unpickedNotes.addAll(generateUnpickedNotes().shuffled()) // add all notes, shuffled
    correctlyGuessedNotes = ArrayList()
    incorrectlyGuessedNotes = ArrayList()
  }

  fun start() {
    gameListener.gameStarted()

    nextNote()
  }

  /**
   * Called by the user: which note did the end user pick?
   */
  fun guess(noteName: String) {
    guessedNote = noteName

    handleGuess()
  }

  private fun handleGuess() {
    // Check if the guessed note is equal to the current note name
    if (guessedNote == currentNote!!.name) {

      // Note was guessed correctly
      gameListener.onCorrectGuess(currentNote)
      correctlyGuessedNotes.add(currentNote!!)

      recalculateScore()
      recalculateAccuracy()
    } else {

      // Note was guessed incorrectly
      gameListener.onIncorrectGuess(currentNote)
      incorrectlyGuessedNotes.add(currentNote!!)

      recalculateScore()
      recalculateAccuracy()
    }
  }

  private fun recalculateAccuracy() {
    val amountOfNotesGuessed = correctlyGuessedNotes.size + incorrectlyGuessedNotes.size
    accuracy = (correctlyGuessedNotes.size).toDouble() / (amountOfNotesGuessed).toDouble()
    println("${correctlyGuessedNotes.size} / $amountOfNotesGuessed = $accuracy")
    gameListener.onAccuracyChange((accuracy * 100).toInt())
  }

  private fun recalculateScore() {
    score = correctlyGuessedNotes.size
    gameListener.onScoreChange(score)
  }

  fun nextNote() {
    currentNote = unpickedNotes.poll()
    gameListener.onNewNote(currentNote)
  }

  fun setGameListener(gameListener: GameListener) {
    this.gameListener = gameListener
  }

  fun generateUnpickedNotes(): List<Note> {
    val result = ArrayList<Note>()

    val lowestNote = Note(name = "E", octave = 2) // lowest note on a standard-tuned guitar
    val highestNote =
      Note(name = "D", octave = 6) // highest note on a standard-tuned 22 fret guitar

    result.addAll(generateNotesInclusiveBetween(lowestNote, highestNote))

    return result
  }

  fun generateNotesInclusiveBetween(
    lowestNote: Note,
    highestNote: Note
  ): List<Note> {
    val result = ArrayList<Note>()

    // Add the lowest note + all notes above it in the current octave
    result.addAll(generateNotesInOctaveInclusiveAbove(lowestNote))

    // Check if there are any whole octaves between the lowest & highest, if so --> add every note in that octave
    val octaveNumbers: List<Int> = getOctaveNumbersBetween(highestNote.octave, lowestNote.octave)
    octaveNumbers.forEach { i ->
      result.addAll(generateAllNotesInOctave(i))
    }

    // Add the highest note and all notes below it in its octave (if any)
    result.addAll(generateNotesInOctaveInclusiveBelow(highestNote))

    return result
  }

  fun generateAllNotesInOctave(octave: Int): Collection<Note> {
    val result = ArrayList<Note>()
    val allNoteNames = arrayOf("A", "B", "C", "D", "E", "F", "G")
    allNoteNames.forEach { name ->
      result.add(Note(name = name, octave = octave))
    }
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

}
