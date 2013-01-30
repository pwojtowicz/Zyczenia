package pl.netplus.wishesphone.fragments;

import pl.netplus.wishesphone.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		EditTextPreference lastDate = (EditTextPreference) getPreferenceScreen()
				.findPreference("prop_last_update_date");
		EditTextPreference nextDate = (EditTextPreference) getPreferenceScreen()
				.findPreference("prop_next_update_date");
		this.getPreferenceScreen().removePreference(lastDate);
		this.getPreferenceScreen().removePreference(nextDate);
	}

}
