package io.github.edwinvanrooij.noteboard

import org.junit.After
import org.junit.Before
import org.junit.Test

class GameEngineTest {

  private lateinit var gameEngine: GameEngine

  @Before
  fun setUp() {
    this.gameEngine = GameEngine()
    gameEngine.start()
  }

  @After
  fun tearDown() {
  }

  @Test
  fun randomNote() {
    val note = gameEngine.getNextNote()
  }

  @Test
  fun generateUnpickedNotes() {
    val lowestNote = Note(name = "F", octave = 2)
    val highestNote = Note(name = "D", octave = 5)

    val octaveNumbers = gameEngine.getOctaveNumbersBetween(
        lowestOctave = lowestNote.octave, highestOctave = highestNote.octave
    )

    println(octaveNumbers)
  }

  @Test
  fun generateNotesInclusiveBetween() {
  }

  @Test
  fun getOctaveNumbersBetween() {
  }

  @Test
  fun generateNotesInOctaveInclusiveAbove() {
    val lowestNote = Note(name = "C", octave = 2)

    val notes = gameEngine.generateNotesInOctaveInclusiveAbove(lowestNote)

    println(notes.size)
    println(notes)
  }

  @Test
  fun generateNotesInOctaveInclusiveBelow() {
    val highestNote = Note(name = "C", octave = 5)

    val notes = gameEngine.generateNotesInOctaveInclusiveBelow(highestNote)

    println(notes.size)
    println(notes)
  }

  @Test
  fun getNextNote() {
  }
}