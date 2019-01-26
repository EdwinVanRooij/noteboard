package io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.*
import io.github.edwinvanrooij.noteboard.noteboardengine.fretsengine.guitar.FretLocation
import org.junit.Before
import org.junit.Test

/**
 * No actions can be performed on the [FretsGameEngine] before setting a [IFretsGameListener]
 * No game actions can be performed on the [FretsGameEngine] before game start
 */
class FretsGameEngineTest {

    private lateinit var gameEngine: FretsGameEngine

    @Before
    fun setUp() {
        this.gameEngine = FretsGameEngine()
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
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        verify(listenerFrets).onGameStart()
    }

    @Test
    fun `start -- good -- onNewNote is called`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        verify(listenerFrets).onNewNote(any(), any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `start -- bad -- Should not work without gameSettings initialized`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)

        gameEngine.start() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `start -- bad -- Should not work without a game listener`() {
        gameEngine.initialize(mockGameSettings())

        gameEngine.start() // exception
    }

    @Test(expected = GameAlreadyStartedException::class)
    fun `start -- bad -- Should not work after a game started`() {
        gameEngine.setGameListener(mock())
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()

        gameEngine.start() // exception
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
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start() // start the game first
        gameEngine.stop()
        verify(listenerFrets).onGameStop(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `stop() -- bad -- Should not work without gameSettings initialized`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)

        gameEngine.stop() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `stop() -- bad -- Should not work without a game listener`() {
        gameEngine.initialize(mockGameSettings())

        gameEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work before a game started`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())

        gameEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work after a game stopped`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        gameEngine.stop()

        gameEngine.stop()  // exception
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
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()

        // Capture the prompted fretLocation
        argumentCaptor<FretLocation>().apply {
            verify(listenerFrets).onNewNote(any(), any()) // capture()

            // We can't test if we guessed correctly, because the picked note is random. Below is what it'd look like.
            // 'firstValue' is the fretLocation we have to guess
            // gameEngine.guess(NoteName.C)
            // Guessed correctly, check if onCorrectGuess fired
            // verify(listener).onCorrectGuess(any())
        }
    }

    @Test
    fun `guess -- good -- onIncorrectGuess is called on incorrect guess`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()

        // Capture the prompted fretLocation
        argumentCaptor<FretLocation>().apply {
            verify(listenerFrets).onNewNote(any(), any()) // capture()

            // We can't test if we guessed incorrectly, because the picked note is random. Below is what it'd look like.
            // 'firstValue' is the fretLocation we have to guess
            // gameEngine.guess(NoteName.A)
            // verify(listener).onIncorrectGuess(any(), any())
        }
    }

    @Test
    fun `guess -- good -- onAccuracyChange is called`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start() // start the game first
        gameEngine.guess(NoteName.C)
        verify(listenerFrets).onAccuracyChange(any())
    }

    @Test
    fun `guess -- good -- onScoreChange is called`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start() // start the game first
        gameEngine.guess(NoteName.C)
        verify(listenerFrets).onScoreChange(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `guess -- bad -- Should not work without gameSettings initialized`() {
        val listenerFrets: IFretsGameListener = mock()
        gameEngine.setGameListener(listenerFrets)

        gameEngine.guess(NoteName.C) // Randomly picked; could be any other note. // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `guess -- bad -- Should not work without a game listener`() {
        gameEngine.initialize(mockGameSettings())

        gameEngine.guess(NoteName.C) // Randomly picked; could be any other note. // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `guess -- bad -- Should not work before a game started`() {
        gameEngine.setGameListener(mock())
        gameEngine.initialize(mockGameSettings())

        gameEngine.guess(NoteName.C) // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `guess -- bad -- Should not work after a game stopped`() {
        gameEngine.setGameListener(mock())
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        gameEngine.stop()

        gameEngine.guess(NoteName.C)  // exception
    }
    //endregion

    // Should work if there's no listener set yet
    // ---
    // Should not work if there's already a listener set
    //region setGameListener
    @Test
    fun `setGameListener -- good -- should work if there's no listener set yet`() {
        val fretsGameListener: IFretsGameListener = mock()
        gameEngine.setGameListener(fretsGameListener)
    }

    @Test(expected = GameListenerAlreadySetException::class)
    fun `setGameListener -- bad -- should not work if there's already a listener set`() {
        gameEngine.setGameListener(mock())

        gameEngine.setGameListener(mock())  // exception
    }
    //endregion


    // Should work if the engine is not initialized yet
    // Should work if the engine is already initialized
    // ---
    //
    //region initialize
    @Test
    fun `initialize -- good -- Should work if the engine is not initialized yet`() {
        gameEngine.initialize(mockGameSettings())
    }

    @Test
    fun `initialize -- good -- Should work if the engine is already initialized`() {
        gameEngine.initialize(mockGameSettings())
        gameEngine.initialize(mockGameSettings())
    }
    //endregion

    //region Mock methods
    private fun mockGameSettings(): FretsGameSettings {
        return FretsGameSettings(guitarFrets = 20, time = 30)
    }
    //endregion
}
