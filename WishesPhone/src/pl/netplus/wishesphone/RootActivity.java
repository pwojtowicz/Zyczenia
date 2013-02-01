package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.fragments.RootFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class RootActivity extends AppBaseActivity {

	private RootFragment details;

	@Override
	public void onResume() {
		super.onResume();

		long nextUpdate = getNextUpdateDate();

		long actualTime = Calendar.getInstance().getTimeInMillis();

		if (nextUpdate < actualTime) {
			update(nextUpdate == 0 ? false : true);
		} else {
			ObjectManager manager = new ObjectManager();
			manager.readAll(this, ERepositoryTypes.Categories);
		}
	}

	private void update(boolean showQuestion) {
		if (showQuestion) {
			OnClickListener positiveListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					getUpdate();
				}
			};

			DialogHelper.createQuestionDialog(this,
					getString(R.string.update_question), positiveListener)
					.show();
		} else
			getUpdate();

	}

	private void getUpdate() {
		ObjectManager manager = new ObjectManager();
		manager.updateData(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		details = new RootFragment();

		if (details != null) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.details, details);

			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_root, menu);
		return true;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof Boolean) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_MONTH, 7);
			setUpdateDates(c.getTimeInMillis(), 1);
		}

		details.setFavoritesCount(NetPlusAppGlobals.getInstance()
				.getFavoritesCount());
		details.reload();

	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		super.onTaskInvalidResponse(exception);

		Category c = new Category(getString(R.string.get_data), 1);
		c.setId(NetPlusAppGlobals.ITEMS_NEET_UPDATE);
		ArrayList<Category> items = new ArrayList<Category>();
		items.add(c);
		NetPlusAppGlobals.getInstance().setCategories(items);
		details.reload();
	}

	@Override
	public void retryLastAction() {
		ObjectManager manager = new ObjectManager();
		manager.readFromServer(this, ERepositoryTypes.Categories);

	}
}
