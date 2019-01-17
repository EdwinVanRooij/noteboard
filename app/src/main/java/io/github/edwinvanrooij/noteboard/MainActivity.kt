package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.app.Fragment
import android.os.Bundle


class MainActivity : Activity(), GameFragmentListener, LandingFragmentListener, ResultsFragmentListener {

    private val landingFragment = LandingFragment()
    private val gameFragment = GameFragment()
    private val resultsFragment = ResultsFragment()

    init {
        landingFragment.setLandingFragmentListener(this)
        gameFragment.setGameFragmentListener(this)
        resultsFragment.setResultsFragmentListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment(landingFragment)
    }

    override fun onGameOver() {
        startFragment(resultsFragment)
    }

    override fun onStartGame() {
        startFragment(gameFragment)
    }

    override fun onDone() {
        startFragment(landingFragment)
    }

    private fun startFragment(fragment: Fragment) {
        fragment.arguments = intent.extras
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
