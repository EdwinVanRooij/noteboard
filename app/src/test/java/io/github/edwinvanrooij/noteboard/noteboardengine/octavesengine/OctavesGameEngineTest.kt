package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameAlreadyStartedException
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameListenerNotSetException
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameNotStartedException
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameSettingsNotSetException
import org.junit.Before
import org.junit.Test

class OctavesGameEngineTest {

    private lateinit var gameEngine: OctavesGameEngine

    @Before
    fun setUp() {
        this.gameEngine = OctavesGameEngine()
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
        val listenerFrets: IOctavesGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        verify(listenerFrets).onGameStart()
    }

    @Test
    fun `start -- good -- onNewNote is called`() {
        val listenerFrets: IOctavesGameListener = mock()
        gameEngine.setGameListener(listenerFrets)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        verify(listenerFrets).onNewNote(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `start -- bad -- Should not work without gameSettings initialized`() {
        gameEngine.setGameListener(mock())
        gameEngine.start() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `start -- bad -- Should not work without a game listener`() {
        gameEngine.initialize(mock())

        gameEngine.start() // exception
    }

    @Test(expected = GameAlreadyStartedException::class)
    fun `start -- bad -- Should not work after a game started`() {
        gameEngine.setGameListener(mock())
        gameEngine.initialize(mock())
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
        val gameListener: IOctavesGameListener = mock()
        gameEngine.setGameListener(gameListener)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start() // start the game first
        gameEngine.stop()
        verify(gameListener).onGameStop(any())
    }

    @Test(expected = GameSettingsNotSetException::class)
    fun `stop() -- bad -- Should not work without gameSettings initialized`() {
        val gameListener: IOctavesGameListener = mock()
        gameEngine.setGameListener(gameListener)

        gameEngine.stop() // exception
    }

    @Test(expected = GameListenerNotSetException::class)
    fun `stop() -- bad -- Should not work without a game listener`() {
        gameEngine.initialize(mockGameSettings())

        gameEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work before a game started`() {
        val gameListener: IOctavesGameListener = mock()
        gameEngine.setGameListener(gameListener)
        gameEngine.initialize(mockGameSettings())

        gameEngine.stop() // exception
    }

    @Test(expected = GameNotStartedException::class)
    fun `stop() -- bad -- Should not work after a game stopped`() {
        val gameListener: IOctavesGameListener = mock()
        gameEngine.setGameListener(gameListener)
        gameEngine.initialize(mockGameSettings())
        gameEngine.start()
        gameEngine.stop()

        gameEngine.stop()  // exception
    }
    //endregion
    //region Mock methods
    private fun mockGameSettings(): OctavesGameSettings {
        return OctavesGameSettings(time = 30)
    }
    //endregion
}