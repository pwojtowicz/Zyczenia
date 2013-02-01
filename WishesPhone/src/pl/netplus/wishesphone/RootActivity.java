package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Date;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.fragments.RootFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class RootActivity extends AppBaseActivity {

	private RootFragment details;

	@Override
	public void onResume() {
		super.onResume();

		int lastUpdate = getLast_update_date();

		ObjectManager manager = new ObjectManager();
		// if (lastUpdate == 0)
		manager.readFromServer(this, ERepositoryTypes.Categories);
		// else
		// manager.readAll(this, ERepositoryTypes.Categories);
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
		if (response.bundle instanceof ArrayList<?>) {
			setUpdateDates(1, new Date().getTime());

			details.setFavoritesCount(NetPlusAppGlobals.getInstance()
					.getFavoritesCount());
			details.reload();
		}
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
