package at.shockbytes.weekly.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import at.shockbytes.weekly.R;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Load preferences from the file
		addPreferencesFromResource(R.xml.prefs);
	}
}
