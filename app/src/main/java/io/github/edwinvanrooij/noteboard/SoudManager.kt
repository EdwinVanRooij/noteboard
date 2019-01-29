package io.github.edwinvanrooij.noteboard

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import java.lang.Exception

@Suppress("LiftReturnOrAssignment")
class SoundManager(private val context: Context) {

    private var mPlayer: MediaPlayer? = null

    private var mPlayerCorrect: MediaPlayer = MediaPlayer.create(context, R.raw.correct)
    private var mPlayerIncorrect: MediaPlayer = MediaPlayer.create(context, R.raw.incorrect)

    private var mPlayerButtonClick: MediaPlayer = MediaPlayer.create(context, R.raw.click)

    fun playSound(note: Note) {
        if (mPlayer != null) {
            mPlayer!!.reset()
            mPlayer!!.release()
        }

        when {
            note.octave == 2 ->
                when {
                    note.noteName == E -> mPlayer = MediaPlayer.create(context, R.raw.e2)
                    note.noteName == F -> mPlayer = MediaPlayer.create(context, R.raw.f2)
                    note.noteName == G -> mPlayer = MediaPlayer.create(context, R.raw.g2)
                    note.noteName == A -> mPlayer = MediaPlayer.create(context, R.raw.a2)
                    note.noteName == B -> mPlayer = MediaPlayer.create(context, R.raw.b2)
                    else -> throw Exception("Could not figure out which note to play at octave ${note.octave} by note $note")
                }
            note.octave == 3 ->
                when {
                    note.noteName == C -> mPlayer = MediaPlayer.create(context, R.raw.c3)
                    note.noteName == D -> mPlayer = MediaPlayer.create(context, R.raw.d3)
                    note.noteName == E -> mPlayer = MediaPlayer.create(context, R.raw.e3)
                    note.noteName == F -> mPlayer = MediaPlayer.create(context, R.raw.f3)
                    note.noteName == G -> mPlayer = MediaPlayer.create(context, R.raw.g3)
                    note.noteName == A -> mPlayer = MediaPlayer.create(context, R.raw.a3)
                    note.noteName == B -> mPlayer = MediaPlayer.create(context, R.raw.b3)
                    else -> throw Exception("Could not figure out which note to play at octave ${note.octave} by note $note")
                }
            note.octave == 4 ->
                when {
                    note.noteName == C -> mPlayer = MediaPlayer.create(context, R.raw.c4)
                    note.noteName == D -> mPlayer = MediaPlayer.create(context, R.raw.d4)
                    note.noteName == E -> mPlayer = MediaPlayer.create(context, R.raw.e4)
                    note.noteName == F -> mPlayer = MediaPlayer.create(context, R.raw.f4)
                    note.noteName == G -> mPlayer = MediaPlayer.create(context, R.raw.g4)
                    note.noteName == A -> mPlayer = MediaPlayer.create(context, R.raw.a4)
                    note.noteName == B -> mPlayer = MediaPlayer.create(context, R.raw.b4)
                    else -> throw Exception("Could not figure out which note to play at octave ${note.octave} by note $note")
                }
            note.octave == 5 ->
                when {
                    note.noteName == C -> mPlayer = MediaPlayer.create(context, R.raw.c5)
                    note.noteName == D -> mPlayer = MediaPlayer.create(context, R.raw.d5)
                    note.noteName == E -> mPlayer = MediaPlayer.create(context, R.raw.e5)
                    note.noteName == F -> mPlayer = MediaPlayer.create(context, R.raw.f5)
                    note.noteName == G -> mPlayer = MediaPlayer.create(context, R.raw.g5)
                    note.noteName == A -> mPlayer = MediaPlayer.create(context, R.raw.a5)
                    note.noteName == B -> mPlayer = MediaPlayer.create(context, R.raw.b5)
                    else -> throw Exception("Could not figure out which note to play at octave ${note.octave} by note $note")
                }
            note.octave == 6 ->
                when {
                    note.noteName == C -> mPlayer = MediaPlayer.create(context, R.raw.c6)
                    note.noteName == D -> mPlayer = MediaPlayer.create(context, R.raw.d6)
                    else -> throw Exception("Could not figure out which note to play at octave ${note.octave} by note $note")
                }
            else -> throw Exception("Could not figure out which octave to look for by note $note, octave ${note.octave}")
        }

        if (mPlayer != null) {
            mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mPlayer!!.start()
        }
    }

    fun repeatLastNote() {
        if (mPlayer != null) {
            mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mPlayer!!.stop()
            mPlayer!!.prepare()
            mPlayer!!.start()
        }
    }

    fun playCorrectSound() {
        mPlayerCorrect.start()
    }

    fun playIncorrectSound() {
        mPlayerIncorrect.start()
    }

    fun playButtonClick() {
        mPlayerButtonClick.start()
    }
}
