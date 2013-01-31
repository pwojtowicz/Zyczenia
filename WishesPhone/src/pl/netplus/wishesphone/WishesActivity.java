package pl.netplus.wishesphone;

import java.util.ArrayList;
import java.util.Collections;

import pl.netplus.appbase.adapters.FragmentAdapter;
import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.FragmentObject;
import pl.netplus.appbase.entities.ModelBase;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.fragments.WishesFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WishesActivity extends AppBaseActivity {

	public static final String BUNDLE_CATEGORY_ID = "catId";
	private static final String BUNGLE_ACTUAL_PAGE_ID = "actualPageId";

	private ViewPager mViewPager;
	private FragmentAdapter fAdapter;
	private ArrayList<FragmentObject> fragments;

	private int categoryId;
	private int allItemCount = 0;
	private Button btn_next;
	private Button btn_previous;
	private Button btn_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wishes);

		btn_next = (Button) findViewById(R.id.btn_next);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_previous = (Button) findViewById(R.id.btn_previous);

		fragments = new ArrayList<FragmentObject>();
		fAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments,
				0);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);

		configureViews();

	}

	private void configureViews() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE)
					changeActivityTitle();

			}
		});

		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				share();
			}
		});

		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
				changeActivityTitle();
			}
		});

		btn_previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
				changeActivityTitle();

			}
		});

	}

	protected void changeActivityTitle() {
		this.setTitle(String.format("(%d/%d)", mViewPager.getCurrentItem() + 1,
				allItemCount));
	}

	@Override
	public void onPause() {
		super.onPause();

		SharedPreferences prefs = this.getSharedPreferences(
				"pl.netplus.wishphone", Context.MODE_PRIVATE);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putInt(BUNGLE_ACTUAL_PAGE_ID, mViewPager.getCurrentItem());
		ed.commit();
	}

	@Override
	public void onResume() {
		super.onResume();

		Bundle b = getIntent().getExtras();

		ArrayList<ContentObject> items = NetPlusAppGlobals.getInstance()
				.getCategoriesContentObjects(categoryId);

		if (items == null && b != null) {
			categoryId = b.getInt(BUNDLE_CATEGORY_ID);

			ObjectManager manager = new ObjectManager();

			if (categoryId > 0) {
				manager.readById(this, ERepositoryTypes.ContentObject,
						new ModelBase(categoryId));
			} else if (categoryId == NetPlusAppGlobals.ITEMS_FAVORITE) {
				manager.readObjectsWithoutSendItem(this,
						ERepositoryTypes.ContentObject,
						ERepositoryManagerMethods.ReadFavorites);
			} else if (categoryId == NetPlusAppGlobals.ITEMS_ALL) {
				manager.readObjectsWithoutSendItem(this,
						ERepositoryTypes.ContentObject,
						ERepositoryManagerMethods.ReadAll);
			} else if (categoryId == NetPlusAppGlobals.ITEMS_SEARCH) {
				items = NetPlusAppGlobals.getInstance()
						.getCategoriesContentObjects(categoryId);
			}
			// manager.readFromServer(this, ERepositoryTypes.ContentObject);
		}

		if (items != null) {
			if (categoryId == NetPlusAppGlobals.ITEMS_ALL)
				Collections.shuffle(items);
			ReloadAllItems(items);
		}

		SharedPreferences prefs = this.getSharedPreferences(
				"pl.netplus.wishphone", Context.MODE_PRIVATE);
		int actualPage = prefs.getInt(BUNGLE_ACTUAL_PAGE_ID, 0);

		mViewPager.setCurrentItem(actualPage);

	}

	private void ReloadAllItems(ArrayList<ContentObject> items) {
		fragments = new ArrayList<FragmentObject>();

		allItemCount = items.size();

		for (ContentObject contentObject : items) {

			WishesFragment fragment = new WishesFragment();
			fragment.setContentObject(contentObject);

			fragments.add(new FragmentObject(fragment));

		}
		fAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments,
				0);

		mViewPager.setAdapter(fAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_wishes, menu);
		return true;
	}

	private void share() {
		FragmentObject fo = fragments.get(mViewPager.getCurrentItem());
		if (fo != null) {
			ContentObject content = ((WishesFragment) fo.getFragment())
					.getContentObject();

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT,
					charsReplace(content.getText() + getSignature()));
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}

	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			// ArrayList<ContentObject> objects = (ArrayList<ContentObject>)
			// response.bundle;
			// WishesGlobals.getInstance().setObjectsDictionary(categoryId,
			// objects);

			ReloadAllItems(NetPlusAppGlobals.getInstance()
					.getCategoriesContentObjects(categoryId));
		}

	}

}
