package pl.netplus.wishesphone;

import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.wishesbase.support.StringHelper;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

public abstract class AppBaseActivity extends FragmentActivity implements
		IReadRepository {

	private final static String categoryAddress = "http://zyczenia.tja.pl/api/android_bramka.php?co=lista_kategorii";
	private final static String contentAddress = "http://zyczenia.tja.pl/api/android_bramka.php?co=lista_obekty&data=";

	private ProgressDialog dialog;
	private String pref_last_update_date;
	private String pref_next_update_date;

	private boolean pref_replaceChars;
	private boolean pref_addSignature;
	private String pref_signature;

	@Override
	public void onResume() {
		super.onResume();
		DataBaseManager.inicjalizeInstance(this);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		pref_last_update_date = prefs.getString("prop_last_update_date", "0");
		pref_next_update_date = prefs.getString("prop_next_update_date", "0");

		pref_replaceChars = (prefs.getBoolean("prop_replace", false));
		pref_addSignature = prefs.getBoolean("prop_add_signature", false);
		if (pref_addSignature)
			pref_signature = "\n" + (prefs.getString("prop_signature", ""));
		else
			pref_signature = ("");
	}

	@Override
	public void onTaskStart(String message) {
		dialog = new ProgressDialog(this);

		if (message.length() == 0)
			message = getString(R.string.progress_download_data);
		dialog.setMessage(message);
		// dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onTaskEnd() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskProgressUpdate(int actualProgress) {
		if (dialog != null)
			dialog.setProgress(actualProgress);

	}

	public static String getCategoryaddress() {
		return categoryAddress;
	}

	public static String getContentaddress(int lastUpdateDate) {
		return String.format(contentAddress + "%d", lastUpdateDate);
	}

	public int getLast_update_date() {
		return Integer.parseInt(pref_last_update_date);
	}

	public String getNext_update_date() {
		return pref_next_update_date;
	}

	public void setUpdateDates(int date, long nextDate) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("prop_last_update_date", String.valueOf(date));
		editor.putString("prop_next_update_date", String.valueOf(nextDate));
		editor.commit();
	}

	public String charsReplace(String text) {
		return pref_replaceChars ? StringHelper.removePolishChars(text) : text;
	}

	public String getSignature() {
		return pref_signature;
	}

}
