package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.netplus.appbase.adapters.FragmentAdapter;
import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.FragmentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.fragments.CategoryFragment;
import pl.netplus.wishesphone.fragments.SearchFragment;
import pl.netplus.wishesphone.fragments.StartFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends AppBaseActivity implements IReadRepository {

	ViewPager mViewPager;
	private FragmentAdapter mPageAdapter;
	private ArrayList<FragmentObject> pages;

	private boolean isFirstTime = true;
	private CategoryFragment fr_categories;
	private SearchFragment fr_search;
	private StartFragment fr_start;

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

		if (fr_start != null)
			fr_start.reload();
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

	private void getUpdate() {
		if (super.checkIsOnline()) {
			Bundle b = new Bundle();
			b.putString("CategoryLink", super.getCategoryAddress());
			b.putString("ObjectsLink", super.getContentAddress());
			b.putString("ObjectsToDeleteLink",
					super.getContentToDeleteAddress());

			ObjectManager manager = new ObjectManager();
			manager.updateData(b, this);
		} else {
			DialogHelper.createInfoDialog(this,
					getString(R.string.dialog_no_internet_connection)).show();
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		isFirstTime = false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pages = new ArrayList<FragmentObject>();

		fr_categories = new CategoryFragment();

		fr_search = new SearchFragment();

		fr_start = new StartFragment();

		pages.add(new FragmentObject(fr_search,
				getString(R.string.title_fragment_search)));
		pages.add(new FragmentObject(fr_start,
				getString(R.string.title_fragment_start)));
		pages.add(new FragmentObject(fr_categories,
				getString(R.string.title_fragment_categories)));

		mPageAdapter = new FragmentAdapter(getSupportFragmentManager(), pages,
				1);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setCurrentItem(1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
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

		fr_categories.reload();
		fr_search.reload();
		fr_start.reload();
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
		fr_categories.reload();
	}

	@Override
	public void retryLastAction() {
		update(false);
	}

}
