package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName

interface IOctavesGameEngine {
    /**
     * Initialize the engine with user-specific options.
     */
    fun initialize(gameSettings: OctavesGameSettings)

    /**
     * Starts a new game.
     */
    fun start()

    /**
     * Stops the currently ongoing game.
     */
    fun stop()

    /**
     * Guess the latest retrieved note, only name specification is needed for equality, instead of scientific note specification.
     */
    fun guess(noteName: NoteName)

    /**
     * Sets a game listener on this game engine.
     * This game listeners deals with events generated from the Game Engine, such as onStart(), onQuit(), onNewNote(...), etc.
     */
    fun setGameListener(gameListener: IOctavesGameListener)
}
