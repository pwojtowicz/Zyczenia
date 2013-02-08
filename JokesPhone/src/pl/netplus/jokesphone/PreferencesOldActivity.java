package pl.netplus.jokesphone;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

public class PreferencesOldActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(pl.netplus.appbase.R.xml.base_preferences);
		addPreferencesFromResource(pl.netplus.jokesphone.R.xml.preferences);

		EditTextPreference lastDate = (EditTextPreference) getPreferenceScreen()
				.findPreference("prop_last_update_return_date");
		EditTextPreference nextDate = (EditTextPreference) getPreferenceScreen()
				.findPreference("prop_next_update_date");
		this.getPreferenceScreen().removePreference(lastDate);
		this.getPreferenceScreen().removePreference(nextDate);
	}

}
