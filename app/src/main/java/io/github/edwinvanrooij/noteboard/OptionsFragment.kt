package io.github.edwinvanrooij.noteboard


import android.os.Bundle
import android.app.Fragment
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_options.*


/**
 * A simple [Fragment] subclass.
 */
class OptionsFragment : Fragment() {

    private lateinit var optionsFragmentListener: OptionsFragmentListener

    private lateinit var soundManager: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = SoundManager(activity)

        fragmentManager.beginTransaction().replace(R.id.fragment_container_options, OptionsPreferenceFragment()).commit()

        btnMenu.setOnClickListener {
            soundManager.playButtonClick()
            this.optionsFragmentListener.onOptionsDone()
        }
    }

    fun setOptionsFragmentListener(optionsFragmentListener: OptionsFragmentListener) {
        this.optionsFragmentListener = optionsFragmentListener
    }

}
