package io.github.edwinvanrooij.noteboard.engine

import io.github.edwinvanrooij.noteboard.engine.music.NoteName

interface IGameEngine {
    /**
     * Initialize the engine with user-specific settings.
     */
    fun initialize(gameSettings: GameSettings)

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
    fun setGameListener(gameListener: IGameListener)
}