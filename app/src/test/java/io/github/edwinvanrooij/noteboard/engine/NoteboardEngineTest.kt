package io.github.edwinvanrooij.noteboard.engine

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.github.edwinvanrooij.noteboard.engine.exceptions.*
import io.github.edwinvanrooij.noteboard.engine.guitar.FretLocation
import io.github.edwinvanrooij.noteboard.engine.music.NoteName
import org.junit.Before
import org.junit.Test

/**
 * No actions can be performed on the [NoteboardEngine] before setting a [IGameListener]
 * No game actions can be performed on the [NoteboardEngine] before game start
 */
class NoteboardEngineTest {

    private lateinit var noteboardEngine: NoteboardEngine

    @Before
    fun setUp() {
        this.noteboardEngine = NoteboardEngine()
    }

    // onGameStart is called
    // onNewNote is called
    // ---
    // Should not work without gameSettings initialized
    // Should not work without a game listener
    // Should not work after a game started
    //region start
    @Test
    fun `start -- good -- onGameStart is called`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()
        verify(listener).onGameStart()
    }


    @Test
    fun `start -- good -- onNewFretLocation is called`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()
        verify(listener).onNewNote(any(), any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `start -- bad -- Should not work without gameSettings initialized`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)

        noteboardEngine.start() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `start -- bad -- Should not work without a game listener`() {
        noteboardEngine.initialize(mockGameSettings())

        noteboardEngine.start() // exception
    }

    @Test(expected = GameAlreadyStartedException::class)
    fun `start -- bad -- Should not work after a game started`() {
        noteboardEngine.setGameListener(mock())
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()

        noteboardEngine.start() // exception
    }
    //endregion

    // onGameStop is called
    // ---
    // Should not work without gameSettings initialized
    // Should not work without a game listener
    // Should not work before a game started
    // Should not work after a game stopped
    //region stop
    @Test
    fun `stop -- good -- onGameStop is called`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start() // start the game first
        noteboardEngine.stop()
        verify(listener).onGameStop(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `stop() -- bad -- Should not work without gameSettings initialized`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)

        noteboardEngine.stop() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `stop() -- bad -- Should not work without a game listener`() {
        noteboardEngine.initialize(mockGameSettings())

        noteboardEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work before a game started`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())

        noteboardEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work after a game stopped`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()
        noteboardEngine.stop()

        noteboardEngine.stop()  // exception
    }
    //endregion

    // onCorrectGuess is called on correct guess
    // onIncorrectGuess is called on incorrect guess
    // onAccuracyChange is called
    // onScoreChange is called
    // ---
    // Should not work without gameSettings initialized
    // Should not work without a game listener
    // Should not work before a game started
    // Should not work after a game stopped
    //region guess
    @Test
    fun `guess -- good -- onCorrectGuess is called on correct guess`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()

        // Capture the prompted fretLocation
        argumentCaptor<FretLocation>().apply {
            verify(listener).onNewNote(any(), any()) // capture()

            // We can't test if we guessed correctly, because the picked note is random. Below is what it'd look like.
            // 'firstValue' is the fretLocation we have to guess
            // noteboardEngine.guess(NoteName.C)
            // Guessed correctly, check if onCorrectGuess fired
            // verify(listener).onCorrectGuess(any())
        }
    }

    @Test
    fun `guess -- good -- onIncorrectGuess is called on incorrect guess`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()

        // Capture the prompted fretLocation
        argumentCaptor<FretLocation>().apply {
            verify(listener).onNewNote(any(), any()) // capture()

            // We can't test if we guessed incorrectly, because the picked note is random. Below is what it'd look like.
            // 'firstValue' is the fretLocation we have to guess
            // noteboardEngine.guess(NoteName.A)
            // verify(listener).onIncorrectGuess(any(), any())
        }
    }

    @Test
    fun `guess -- good -- onAccuracyChange is called`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start() // start the game first
        noteboardEngine.guess(NoteName.C)
        verify(listener).onAccuracyChange(any())
    }

    @Test
    fun `guess -- good -- onScoreChange is called`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start() // start the game first
        noteboardEngine.guess(NoteName.C)
        verify(listener).onScoreChange(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `guess -- bad -- Should not work without gameSettings initialized`() {
        val listener: IGameListener = mock()
        noteboardEngine.setGameListener(listener)

        noteboardEngine.guess(NoteName.C) // Randomly picked; could be any other note. // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `guess -- bad -- Should not work without a game listener`() {
        noteboardEngine.initialize(mockGameSettings())

        noteboardEngine.guess(NoteName.C) // Randomly picked; could be any other note. // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `guess -- bad -- Should not work before a game started`() {
        noteboardEngine.setGameListener(mock())
        noteboardEngine.initialize(mockGameSettings())

        noteboardEngine.guess(NoteName.C) // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `guess -- bad -- Should not work after a game stopped`() {
        noteboardEngine.setGameListener(mock())
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.start()
        noteboardEngine.stop()

        noteboardEngine.guess(NoteName.C)  // exception
    }
    //endregion

    // Should work if there's no listener set yet
    // ---
    // Should not work if there's already a listener set
    //region setGameListener
    @Test
    fun `setGameListener -- good -- should work if there's no listener set yet`() {
        val gameListener: IGameListener = mock()
        noteboardEngine.setGameListener(gameListener)
    }

    @Test(expected = GameListenerAlreadySetException::class)
    fun `setGameListener -- bad -- should not work if there's already a listener set`() {
        noteboardEngine.setGameListener(mock())

        noteboardEngine.setGameListener(mock())  // exception
    }
    //endregion


    // Should work if the engine is not initialized yet
    // Should work if the engine is already initialized
    // ---
    //
    //region initialize
    @Test
    fun `initialize -- good -- Should work if the engine is not initialized yet`() {
        noteboardEngine.initialize(mockGameSettings())
    }

    @Test
    fun `initialize -- good -- Should work if the engine is already initialized`() {
        noteboardEngine.initialize(mockGameSettings())
        noteboardEngine.initialize(mockGameSettings())
    }
    //endregion

    //region Mock methods
    private fun mockGameSettings(): GameSettings {
        return GameSettings(guitarFrets = 20, time = 30)
    }
    //endregion
}
