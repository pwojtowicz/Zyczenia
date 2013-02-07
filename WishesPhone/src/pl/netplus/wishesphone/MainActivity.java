package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.FragmentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.adapters.StartActivityFragmentAdapter;
import pl.netplus.wishesphone.fragments.CategoryListFragment;
import pl.netplus.wishesphone.fragments.SearchFragment;
import pl.netplus.wishesphone.fragments.StartFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends AppBaseActivity implements IReadRepository {

	ViewPager mViewPager;
	private StartActivityFragmentAdapter mPageAdapter;
	private ArrayList<FragmentObject> pages;

	private boolean isFirstTime = true;

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("onStart MainActivity");
		long nextUpdate = getNextUpdateDate();
		long actualTime = Calendar.getInstance().getTimeInMillis();

		if (nextUpdate < actualTime) {
			update(nextUpdate == 0 ? false : true);
		} else if (isFirstTime) {
			ObjectManager manager = new ObjectManager();
			manager.readAll(this, ERepositoryTypes.Categories);
		}
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

		mPageAdapter = new StartActivityFragmentAdapter(
				getSupportFragmentManager(),
				getString(R.string.title_fragment_search),
				getString(R.string.title_fragment_start),
				getString(R.string.title_fragment_categories));

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
		System.out.println("MainActivity onTaskResponse");
		((SearchFragment) mPageAdapter.getItem(0)).reload();
		((StartFragment) mPageAdapter.getItem(1)).reload();
		((CategoryListFragment) mPageAdapter.getItem(2)).reload();
		mPageAdapter.notifyDataSetChanged();

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
	}

	@Override
	public void retryLastAction() {
		update(false);
	}

}
