package pl.netplus.jokesphone;

import pl.netplus.jokesphone.fragments.SettingsFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class PreferencesNewActivity extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();

	}
}
