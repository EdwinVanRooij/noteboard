package io.github.edwinvanrooij.noteboard.ui

import android.os.Bundle
import android.preference.PreferenceFragment
import io.github.edwinvanrooij.noteboard.R


class OptionsPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.options)
    }
}

