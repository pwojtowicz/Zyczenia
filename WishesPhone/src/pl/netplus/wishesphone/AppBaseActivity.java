package pl.netplus.wishesphone;

import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IReadRepository;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

public abstract class AppBaseActivity extends FragmentActivity implements
		IReadRepository {

	private ProgressDialog dialog;

	@Override
	public void onResume() {
		super.onResume();
		DataBaseManager.inicjalizeInstance(this);
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
}
