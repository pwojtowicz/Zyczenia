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
	private boolean isFirstTime = true;

	@Override
	public void onStart() {
		super.onStart();
		long nextUpdate = getNextUpdateDate();
		long actualTime = Calendar.getInstance().getTimeInMillis();

		if (nextUpdate < actualTime) {
			update(nextUpdate == 0 ? false : true);
		} else if (isFirstTime) {
			ObjectManager manager = new ObjectManager();
			manager.readAll(this, ERepositoryTypes.Categories);
		}

		if (details != null) {
			details.setFavoritesCount(NetPlusAppGlobals.getInstance()
					.getFavoritesCount());
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		isFirstTime = false;
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void update(boolean showQuestion) {
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

	@Override
	public void retryLastAction() {
		update(false);
	}

	private void getUpdate() {
		Bundle b = new Bundle();
		b.putString("CategoryLink", super.getCategoryAddress());
		b.putString("ObjectsLink", super.getContentAddress());
		b.putString("ObjectsToDeleteLink", super.getContentToDeleteAddress());

		ObjectManager manager = new ObjectManager();
		manager.updateData(b, this);
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
		if (response.bundle instanceof Long) {
			long returnFromServerTime = (Long) response.bundle;
			if (returnFromServerTime > 0) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH, 7);
				setUpdateDates(c.getTimeInMillis(), returnFromServerTime);
			}
		}

		details.setFavoritesCount(NetPlusAppGlobals.getInstance()
				.getFavoritesCount());
		details.reload();

	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		super.onTaskInvalidResponse(exception);
		if (NetPlusAppGlobals.getInstance().getCategories().size() == 0) {
			Category c = new Category(getString(R.string.get_data), 1);
			c.setId(NetPlusAppGlobals.ITEMS_NEET_UPDATE);
			ArrayList<Category> items = new ArrayList<Category>();
			items.add(c);
			NetPlusAppGlobals.getInstance().setCategories(items);
		}
		details.reload();
	}

}
