package io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine

import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameListenerAlreadySetException

class OctavesGameEngine: IOctavesGameEngine {

    private lateinit var gameListener: IOctavesGameListener

    override fun initialize(gameSettings: OctavesGameSettings) {
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun guess(noteName: NoteName) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setGameListener(gameListener: IOctavesGameListener) {
        if (this::gameListener.isInitialized) {
            throw GameListenerAlreadySetException("There is already a GameListener set.")
        }
        this.gameListener = gameListener
    }
}
