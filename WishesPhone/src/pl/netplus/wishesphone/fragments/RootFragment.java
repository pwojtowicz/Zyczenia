package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.CategoryListAdapter;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.wishesphone.R;
import pl.netplus.wishesphone.WishesActivity;
import pl.netplus.wishesphone.support.WishesGlobals;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class RootFragment extends BaseFragment<Object> implements
		OnItemClickListener {

	private ListView listView;
	private Button btn_favorite;

	public RootFragment() {
		super(R.layout.fragment_root_layout, ERepositoryTypes.Categories);
	}

	@Override
	public void linkViews(View convertView) {
		listView = (ListView) convertView.findViewById(R.id.listView);

		LayoutInflater layoutInflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View header = layoutInflater.inflate(R.layout.root_header_layout, null);

		btn_favorite = (Button) header.findViewById(R.id.button_favorite);

		View footer = layoutInflater.inflate(R.layout.root_footer_layout, null);

		listView.addHeaderView(header);
		listView.addFooterView(footer);

		listView.setOnItemClickListener(this);

	}

	@Override
	public void reload() {
		ArrayList<Category> categories = WishesGlobals.getInstance()
				.getCategories();

		CategoryListAdapter adapter = new CategoryListAdapter(getActivity(),
				categories, R.layout.row_category_layout);
		listView.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

		Category cat = (Category) view.getTag();
		if (cat != null) {
			Intent intent = new Intent(getActivity(), WishesActivity.class);
			Bundle b = new Bundle();
			b.putInt(WishesActivity.BUNDLE_CATEGORY_ID, cat.getId());
			intent.putExtras(b);
			startActivity(intent);
		}
	}

	public void setFavoritesCount(int totalCount) {
		if (totalCount < 0)
			totalCount = 0;
		btn_favorite.setText(String.format("%s (%d)",
				getText(R.string.favorites), totalCount));

	}

}
