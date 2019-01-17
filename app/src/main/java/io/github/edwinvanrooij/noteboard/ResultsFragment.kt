package io.github.edwinvanrooij.noteboard


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_results.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ResultsFragment : Fragment() {

    private lateinit var resultsFragmentListener: ResultsFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDone.setOnClickListener {
            this.resultsFragmentListener.onDone()
        }
    }

    fun setResultsFragmentListener(resultsFragmentListener: ResultsFragmentListener) {
        this.resultsFragmentListener = resultsFragmentListener
    }
}
