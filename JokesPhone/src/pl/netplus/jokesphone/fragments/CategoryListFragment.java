package pl.netplus.jokesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.CategoryListAdapter;
import pl.netplus.appbase.entities.Category;
import pl.netplus.jokesphone.JokesActivity;
import pl.netplus.jokesphone.MainActivity;
import pl.netplus.jokesphone.R;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CategoryListFragment extends ListFragment {

	public static CategoryListFragment newInstance(int index) {
		CategoryListFragment f = new CategoryListFragment();
		return f;
	}

	private Context context = null;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_categories, container,
				false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getActivity() != null) {
			this.context = getActivity();
		}
		reload();
	}

	public void reload() {
		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();

		if (categories != null && categories.size() > 0 && context != null) {
			CategoryListAdapter adapter = new CategoryListAdapter(context,
					categories, R.layout.row_category_layout);
			setListAdapter(adapter);
			System.out.println("reload success");
		}
	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		Category cat = (Category) view.getTag();
		if (cat != null) {
			if (cat.getId() == NetPlusAppGlobals.ITEMS_NEET_UPDATE)
				((MainActivity) getActivity()).retryLastAction();
			else
				startWishesIntent(cat.getId(), cat.getName());
		}
	}

	private void startWishesIntent(int categoryId, String title) {
		Intent intent = new Intent(getActivity(), JokesActivity.class);
		Bundle b = new Bundle();
		b.putInt(JokesActivity.BUNDLE_CATEGORY_ID, categoryId);
		b.putString(JokesActivity.BUNDLE_TITLE, title);
		intent.putExtras(b);
		startActivity(intent);
	}

}
