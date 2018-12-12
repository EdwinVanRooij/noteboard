package io.github.edwinvanrooij.noteboard

import org.junit.After
import org.junit.Before
import org.junit.Test

class GameEngineTest {

    private lateinit var gameEngine: GameEngine

    @Before
    fun setUp() {
        this.gameEngine = GameEngine()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun randomNote() {
        gameEngine.setGameListener(object : GameListener {
            override fun onAccuracyChange(accuracy: Double) {
                println("accuracy change $accuracy")
            }

            override fun onNewNote(note: Note) {
                println("new note $note")
            }

            override fun onCorrectGuess(note: Note) {
                println("Correctly guessed $note")
            }

            override fun onIncorrectGuess(
                guess: String,
                correct: Note
            ) {
                println("Incorrectly guessed $correct")
            }

            override fun gameStarted() {
                println("game started")
            }

            override fun onScoreChange(newScore: Int) {
                println("score change $newScore")
            }
        })

        gameEngine.start()
        gameEngine.nextNote()
    }

    @Test
    fun generateUnpickedNotes() {
        val lowestNote = Note(name = "F", octave = 2)
        val highestNote = Note(name = "D", octave = 5)

        val octaveNumbers = gameEngine.getOctaveNumbersBetween(
            lowestOctave = lowestNote.octave, highestOctave = highestNote.octave
        )
        val notes = gameEngine.generateUnpickedNotes()
//        println(notes)
        notes.forEach {
            println(it)
        }
//        println(notes.size)

//        println(octaveNumbers)
    }

    @Test
    fun generateNotesInclusiveBetween() {
        val lowestNote = Note(name = "A", octave = 3)
        val highestNote = Note(name = "B", octave = 3)

        val notes = gameEngine.generateNotesInclusiveBetween(lowestNote, highestNote)

        println(notes.size)
        notes.forEach { note ->
            println(note)
        }
    }

    @Test
    fun getOctaveNumbersBetween() {
    }

    @Test
    fun generateAllNotesInOctave() {
        val lowestNote = Note(name = "A", octave = 3)

        val notes = gameEngine.generateAllNotesInOctave(lowestNote.octave)

        println(notes.size)
        println(notes)
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