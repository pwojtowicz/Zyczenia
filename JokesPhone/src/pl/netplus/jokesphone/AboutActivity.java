package pl.netplus.jokesphone;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		TextView version = (TextView) findViewById(R.id.txtv_version);
		try {
			version.setText(String.format(
					"wersja: %s",
					this.getPackageManager().getPackageInfo(
							this.getPackageName(), 0).versionName));
		} catch (NameNotFoundException e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

}
