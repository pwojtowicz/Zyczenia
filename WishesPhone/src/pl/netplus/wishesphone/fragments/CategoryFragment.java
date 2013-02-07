package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.CategoryListAdapter;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.MainActivity;
import pl.netplus.wishesphone.R;
import pl.netplus.wishesphone.WishesActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment<Category> implements
		OnItemClickListener {

	private static final String TAG = CategoryFragment.class.getSimpleName();

	private ListView lv_categories;
	private boolean reloadAfterActivityCreated;

	public CategoryFragment() {
		super(R.layout.fragment_categories, ERepositoryTypes.Categories);

	}

	// @Override
	// public void onResume() {
	// super.onResume();
	// reload();
	// }

	@Override
	public void linkViews(View convertView) {
		lv_categories = (ListView) convertView.findViewById(R.id.lv_categories);

		lv_categories.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

		Category cat = (Category) view.getTag();
		if (cat != null) {
			if (cat.getId() == NetPlusAppGlobals.ITEMS_NEET_UPDATE)
				((MainActivity) getActivity()).retryLastAction();
			else
				startWishesIntent(cat.getId(), cat.getName());
		}
	}

	private void startWishesIntent(int categoryId, String title) {
		Intent intent = new Intent(getActivity(), WishesActivity.class);
		Bundle b = new Bundle();
		b.putInt(WishesActivity.BUNDLE_CATEGORY_ID, categoryId);
		b.putString(WishesActivity.BUNDLE_TITLE, title);
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public void reload() {
		System.out.println("reload Categories");
		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();

		if (categories != null && categories.size() > 0
				&& getActivity() != null) {
			CategoryListAdapter adapter = new CategoryListAdapter(
					getActivity(), categories, R.layout.row_category_layout);
			lv_categories.setAdapter(adapter);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.d(TAG, this + ": onViewCreated()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, this + ": onDestroyView()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, this + ": onDetach()");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, this + ": onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, this + ": onResume()");
		reload();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, this + ": onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, this + ": onStop()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, this + ": onDestroy()");
	}

}
