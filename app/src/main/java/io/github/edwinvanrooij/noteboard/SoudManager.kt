package io.github.edwinvanrooij.noteboard

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import io.github.edwinvanrooij.noteboard.engine.music.Note
import io.github.edwinvanrooij.noteboard.engine.music.NoteName.*

class SoundManager(val context: Context) {

    private var mPlayer: MediaPlayer? = null

    fun playSound(note: Note) {
        if (note.octave == 2) {
            if (note.noteName == A) {
//                mPlayer = MediaPlayer.create(context, R.raw.A2)
            }
        }
//        when {
//            l.stringNumber == 6 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_6_15)
////                    else -> Toast.makeText(
////                        this,
////                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
////                        Toast.LENGTH_SHORT
////                    ).show()
//                }
//            }
//            l.stringNumber == 5 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_5_15)
////                    else -> Toast.makeText(
////                        this,
////                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
////                        Toast.LENGTH_SHORT
////                    ).show()
//                }
//            }
//            l.stringNumber == 4 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_4_15)
////                    else ->
////                        Toast.makeText(
////                        this,
////                        "Error! Could not find fret ${l.fret} with string number ${l.string}",
////                        Toast.LENGTH_SHORT
////                    ).show()
//                }
//            }
//            l.stringNumber == 3 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_3_15)
//                }
//            }
//            l.stringNumber == 2 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_2_15)
//                }
//            }
//            l.stringNumber == 1 -> {
//                when {
//                    l.fretNumber == 0 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_0)
//                    l.fretNumber == 1 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_1)
//                    l.fretNumber == 2 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_2)
//                    l.fretNumber == 3 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_3)
//                    l.fretNumber == 4 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_4)
//                    l.fretNumber == 5 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_5)
//                    l.fretNumber == 6 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_6)
//                    l.fretNumber == 7 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_7)
//                    l.fretNumber == 8 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_8)
//                    l.fretNumber == 9 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_9)
//                    l.fretNumber == 10 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_10)
//                    l.fretNumber == 11 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_11)
//                    l.fretNumber == 12 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_12)
//                    l.fretNumber == 13 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_13)
//                    l.fretNumber == 14 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_14)
//                    l.fretNumber == 15 -> mPlayer = MediaPlayer.create(this, R.raw.n_1_15)
//                }
//            }
//            else -> Toast.makeText(
//                this,
//                "Error! Could not find string with number ${l.stringNumber}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        if (mPlayer != null) {
            mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mPlayer!!.start()
            mPlayer!!.reset()
            mPlayer!!.release()
        }
    }

    fun repeatLastSound() {
        if (mPlayer != null) {
            mPlayer!!.start()
        }
    }
}
