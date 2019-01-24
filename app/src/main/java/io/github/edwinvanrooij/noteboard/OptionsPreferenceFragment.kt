package io.github.edwinvanrooij.noteboard

import android.os.Bundle
import android.preference.PreferenceFragment


class OptionsPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.options)
    }
}

