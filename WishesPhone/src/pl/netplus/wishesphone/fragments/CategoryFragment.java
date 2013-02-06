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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment<Category> implements
		OnItemClickListener {

	private ListView lv_categories;

	public CategoryFragment() {
		super(R.layout.fragment_categories, ERepositoryTypes.Categories);
	}

	@Override
	public void onResume() {
		super.onResume();
		reload();

	}

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
		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();

		CategoryListAdapter adapter = new CategoryListAdapter(getActivity(),
				categories, R.layout.row_category_layout);
		lv_categories.setAdapter(adapter);
	}

}
