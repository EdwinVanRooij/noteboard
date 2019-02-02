package io.github.edwinvanrooij.noteboard.ui

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skyfishjy.library.RippleBackground
import io.github.edwinvanrooij.noteboard.*
import io.github.edwinvanrooij.noteboard.listeners.OctavesGameFragmentListener
import io.github.edwinvanrooij.noteboard.noteboardengine.Note
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import io.github.edwinvanrooij.noteboard.noteboardengine.exceptions.GameNotStartedException
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.IOctavesGameListener
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameEngine
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameResults
import io.github.edwinvanrooij.noteboard.noteboardengine.octavesengine.OctavesGameSettings
import kotlinx.android.synthetic.main.fragment_game_octaves.*
import net.gotev.speech.GoogleVoiceTypingDisabledException
import net.gotev.speech.Speech
import net.gotev.speech.SpeechDelegate
import net.gotev.speech.SpeechRecognitionNotAvailable

/**
 * A simple [Fragment] subclass.
 *
 */
class OctavesGameFragment : Fragment(), IOctavesGameListener {

    private lateinit var gameFragmentListener: OctavesGameFragmentListener

    private lateinit var gameEngine: OctavesGameEngine
    private lateinit var soundManager: SoundManager

    private lateinit var rbg: RippleBackground

    private val newNoteDelay: Long = 2500L // in ms
    private var newNoteThread: Thread? = null

    private val guessFeedbackRemovalDelay: Long = 2200L // in ms
    private var guessFeedbackRemovalThread: Thread? = null

    private val speechRecognitionDelay: Long = 2300L // in ms

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_octaves, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settings = arguments.getSerializable(KEY_GAME_SETTINGS) as OctavesGameSettings

        rbg = rbContent as RippleBackground

        gameEngine = OctavesGameEngine()
        gameEngine.initialize(settings)
        gameEngine.setGameListener(this)

        soundManager = SoundManager(activity)

        rbg.setOnClickListener {
            soundManager.repeatLastNote()
        }
        ivSpeechCircle.setOnClickListener {
            startListening()
        }

        gameEngine.start()
        setGuessButtonListeners()
    }

    private fun setGuessButtonListeners() {
        fun guess(note: NoteName) {
            try {
                gameEngine.guess(note)
            } catch (e: GameNotStartedException) {
                Toast.makeText(activity, R.string.game_not_started, Toast.LENGTH_SHORT).show()
            }
        }
        btnC.setOnClickListener {
            guess(C)
        }
        btnD.setOnClickListener {
            guess(D)
        }
        btnE.setOnClickListener {
            guess(E)
        }
        btnF.setOnClickListener {
            guess(F)
        }
        btnG.setOnClickListener {
            guess(G)
        }
        btnA.setOnClickListener {
            guess(A)
        }
        btnB.setOnClickListener {
            guess(B)
        }
    }

    override fun onGameStart() {
        rbg.startRippleAnimation()
    }

    override fun onGameStop(results: OctavesGameResults) {
        gameFragmentListener.onGameOver(results)
    }

    override fun onNewNote(note: Note) {
        newNoteThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(newNoteDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        soundManager.playSound(note)
                    }
                    Thread.sleep(speechRecognitionDelay)

                    activity.runOnUiThread {
                        startListening()
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        newNoteThread!!.start()
    }

    override fun onAccuracyChange(accuracy: Double) {
        tvAccuracy.text = accuracyToString(accuracy)
    }

    override fun onScoreChange(score: Int) {
        tvScore.text = score.toString()
    }

    override fun onCorrectGuess(note: Note) {
        setNoteButtonCorrect(note)
        soundManager.playCorrectSound()

        removeGuessFeedback(note)
    }

    override fun onIncorrectGuess(correctNote: Note, guessedNote: NoteName) {
        setNoteButtonIncorrect(guessedNote)
        setNoteButtonCorrect(correctNote)
        soundManager.playIncorrectSound()

        removeGuessFeedback(correctNote, guessedNote)
    }

    private fun removeGuessFeedback(correctNote: Note, incorrectNote: NoteName? = null) {
        guessFeedbackRemovalThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(guessFeedbackRemovalDelay)
                    if (activity == null) {
                        return
                    }
                    activity.runOnUiThread {
                        setNoteButtonNormal(correctNote)
                        if (incorrectNote != null) {
                            setNoteButtonNormal(incorrectNote)
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        guessFeedbackRemovalThread!!.start()
    }

    private fun setNoteButtonNormal(note: NoteName) {
        when (note) {
            C -> btnC.setColor(context.getColor(R.color.colorPrimary))
            D -> btnD.setColor(context.getColor(R.color.colorPrimary))
            E -> btnE.setColor(context.getColor(R.color.colorPrimary))
            F -> btnF.setColor(context.getColor(R.color.colorPrimary))
            G -> btnG.setColor(context.getColor(R.color.colorPrimary))
            A -> btnA.setColor(context.getColor(R.color.colorPrimary))
            B -> btnB.setColor(context.getColor(R.color.colorPrimary))
        }
    }

    private fun setNoteButtonNormal(note: Note) {
        when (note.noteName) {
            C -> btnC.setColor(context.getColor(R.color.colorPrimary))
            D -> btnD.setColor(context.getColor(R.color.colorPrimary))
            E -> btnE.setColor(context.getColor(R.color.colorPrimary))
            F -> btnF.setColor(context.getColor(R.color.colorPrimary))
            G -> btnG.setColor(context.getColor(R.color.colorPrimary))
            A -> btnA.setColor(context.getColor(R.color.colorPrimary))
            B -> btnB.setColor(context.getColor(R.color.colorPrimary))
        }
    }

    private fun setNoteButtonCorrect(note: Note) {
        when (note.noteName) {
            C -> btnC.setColor(context.getColor(R.color.correct))
            D -> btnD.setColor(context.getColor(R.color.correct))
            E -> btnE.setColor(context.getColor(R.color.correct))
            F -> btnF.setColor(context.getColor(R.color.correct))
            G -> btnG.setColor(context.getColor(R.color.correct))
            A -> btnA.setColor(context.getColor(R.color.correct))
            B -> btnB.setColor(context.getColor(R.color.correct))
        }
    }

    private fun setNoteButtonIncorrect(note: NoteName) {
        when (note) {
            C -> btnC.setColor(context.getColor(R.color.incorrect))
            D -> btnD.setColor(context.getColor(R.color.incorrect))
            E -> btnE.setColor(context.getColor(R.color.incorrect))
            F -> btnF.setColor(context.getColor(R.color.incorrect))
            G -> btnG.setColor(context.getColor(R.color.incorrect))
            A -> btnA.setColor(context.getColor(R.color.incorrect))
            B -> btnB.setColor(context.getColor(R.color.incorrect))
        }
    }

    fun setGameFragmentListener(gameFragmentListener: OctavesGameFragmentListener) {
        this.gameFragmentListener = gameFragmentListener
    }

    private fun startListening() {
        val act: Activity? = activity
        if (act == null || context == null || ivSpeechCircle == null || resources == null) {
            return
        }

        // Request permission
        if (context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }

        ivSpeechCircle.drawable.setTint(resources.getColor(R.color.correct))

        Log.i("speech", "speech recognition is now active")
        try {
            // you must have android.permission.RECORD_AUDIO granted at this point
            Speech.getInstance().startListening(object : SpeechDelegate {
                override fun onStartOfSpeech() {
                    Log.i("speech", "speech recognition is now active")
                }

                override fun onSpeechRmsChanged(value: Float) {
                    Log.d("speech", "rms is now: $value")
                }

                override fun onSpeechPartialResults(results: List<String>) {
                    val str = StringBuilder()
                    for (res in results) {
                        str.append(res).append(" ")
                    }

                    Log.i("speech", "partial result: " + str.toString().trim { it <= ' ' })
                }

                override fun onSpeechResult(result: String) {
                    Log.i("speech", "result: $result")
                    ivSpeechCircle.drawable.setTint(resources.getColor(R.color.neutral))

                    val note = textToNoteName(result)
                    if (note == null) {
                        Toast.makeText(act, "Invalid guess, found '$result'", Toast.LENGTH_SHORT).show()
                    } else {
                        gameEngine.guess(note)
                    }
                }
            })
        } catch (exc: SpeechRecognitionNotAvailable) {
            Log.e("speech", "Speech recognition is not available on this device!")
            // You can prompt the user if he wants to install Google App to have
            // speech recognition, and then you can simply call:
            //
            // SpeechUtil.redirectUserToGoogleAppOnPlayStore(this);
            //
            // to redirect the user to the Google App page on Play Store
        } catch (exc: GoogleVoiceTypingDisabledException) {
            Log.e("speech", "Google voice typing must be enabled!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        guessFeedbackRemovalThread?.interrupt()
        newNoteThread?.interrupt()
    }
}
