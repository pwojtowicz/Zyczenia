package pl.netplus.wishesphone;

import java.util.ArrayList;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesphone.fragments.WishesFragment;
import pl.netplus.wishesphone.support.WishesGlobals;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class WishesActivity extends AppBaseActivity {

	public static final String BUNDLE_CATEGORY_ID = "catId";
	private WishesFragment details;
	private int categoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wishes);

		Bundle b = getIntent().getExtras();

		details = new WishesFragment();
		details.setArguments(b);

		if (details != null) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		if (b != null) {
			categoryId = b.getInt(BUNDLE_CATEGORY_ID);
			ObjectManager manager = new ObjectManager();
			manager.readAll(this, ERepositoryTypes.ContentObject);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_wishes, menu);
		return true;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			ArrayList<ContentObject> objects = (ArrayList<ContentObject>) response.bundle;
			WishesGlobals.getInstance().setObjectsDictionary(categoryId,
					objects);
			details.reload();
		}

	}

}
