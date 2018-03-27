package at.shockbytes.weekly.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment

import at.shockbytes.weekly.R

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.prefs)
    }
}
