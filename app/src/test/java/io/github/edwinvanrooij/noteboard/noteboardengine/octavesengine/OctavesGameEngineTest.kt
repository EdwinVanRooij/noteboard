package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
    //endregion

    //region Mock methods
    private fun mockGameSettings(): OctavesGameSettings {
        return OctavesGameSettings(time = 30)
    }
    //endregion
}