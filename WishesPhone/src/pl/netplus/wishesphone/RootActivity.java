package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Date;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.Favorite;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesphone.fragments.RootFragment;
import pl.netplus.wishesphone.support.WishesGlobals;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class RootActivity extends AppBaseActivity {

	private static final int READ_CATEGORIES = 1;
	private static final int READ_FAVORITES = 2;
	private int state;

	private RootFragment details;

	@Override
	public void onResume() {
		super.onResume();

		int lastUpdate = getLast_update_date();

		ObjectManager manager = new ObjectManager();
		state = READ_CATEGORIES;
		if (lastUpdate == 0)
			manager.readFromServer(this, ERepositoryTypes.Categories);
		else
			manager.readAll(this, ERepositoryTypes.Categories);
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
			if (state == READ_CATEGORIES) {
				WishesGlobals.getInstance().setCategories(
						(ArrayList<Category>) response.bundle);
				setUpdateDates(1, new Date().getTime());

				ObjectManager manager = new ObjectManager();
				state = READ_FAVORITES;
				manager.readAll(this, ERepositoryTypes.Favorite);
			} else if (state == READ_FAVORITES) {
				ArrayList<Favorite> items = (ArrayList<Favorite>) response.bundle;
				WishesGlobals.getInstance().setFavorites(items);

				details.setFavoritesCount(items.size());
				details.reload();
			}
		}
	}
}
