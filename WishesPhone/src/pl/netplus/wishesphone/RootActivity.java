package pl.netplus.wishesphone;

import java.util.ArrayList;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesphone.fragments.RootFragment;
import pl.netplus.wishesphone.support.WishesGlobals;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class RootActivity extends AppBaseActivity {

	private RootFragment details;

	@Override
	public void onResume() {
		super.onResume();
		ObjectManager manager = new ObjectManager();
		manager.readAll(this, ERepositoryTypes.Categories);
		// manager.readFromServer(this, ERepositoryTypes.Categories);
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
			WishesGlobals.getInstance().setCategories(
					(ArrayList<Category>) response.bundle);
			details.reload();
		}
	}
}
