package io.github.edwinvanrooij.noteboard


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_landing.*


/**
 * A simple [Fragment] subclass.
 *
 */
class LandingFragment : Fragment() {

    private lateinit var landingFragmentListener: LandingFragmentListener

    private lateinit var soundManager: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        btnStart.setOnClickListener {
            soundManager.playButtonClick()
            this.landingFragmentListener.onStartGame()
        }
    }

    fun setLandingFragmentListener(landingFragmentListener: LandingFragmentListener) {
        this.landingFragmentListener = landingFragmentListener;
    }
}
