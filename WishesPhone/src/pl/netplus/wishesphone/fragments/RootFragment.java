package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.CategoryListAdapter;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.enums.EDialogType;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.AboutActivity;
import pl.netplus.wishesphone.R;
import pl.netplus.wishesphone.WishesActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

		Button btn_random = (Button) footer.findViewById(R.id.btn_random);

		Button btn_webpage = (Button) footer.findViewById(R.id.btn_web);
		Button btn_otherpage = (Button) footer.findViewById(R.id.btn_other);

		Button btn_about = (Button) footer.findViewById(R.id.btn_about);

		listView.addHeaderView(header);
		listView.addFooterView(footer);

		listView.setOnItemClickListener(this);

		btn_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showFavoritesWish();
			}
		});

		btn_random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showRandomWish();
			}
		});

		OnClickListener page = new OnClickListener() {

			@Override
			public void onClick(View view) {
				String url = (String) view.getTag();

				showWebPage(url);
			}
		};

		btn_otherpage.setOnClickListener(page);
		btn_webpage.setOnClickListener(page);

		btn_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showAboutPage();
			}
		});

	}

	protected void showAboutPage() {
		Intent intent = new Intent(getActivity(), AboutActivity.class);
		startActivity(intent);
	}

	protected void showWebPage(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	@Override
	public void reload() {
		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();

		CategoryListAdapter adapter = new CategoryListAdapter(getActivity(),
				categories, R.layout.row_category_layout);
		listView.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

		Category cat = (Category) view.getTag();
		if (cat != null) {
			startWishesIntent(cat.getId());
		}
	}

	protected void showFavoritesWish() {
		if (NetPlusAppGlobals.getInstance().getFavoritesCount() > 0)
			startWishesIntent(NetPlusAppGlobals.ITEMS_FAVORITE);
		else {
			DialogHelper.createDialog(getActivity(), EDialogType.No_Favorites)
					.show();
		}
	}

	protected void showRandomWish() {
		startWishesIntent(NetPlusAppGlobals.ITEMS_ALL);
	}

	private void startWishesIntent(int categoryId) {
		Intent intent = new Intent(getActivity(), WishesActivity.class);
		Bundle b = new Bundle();
		b.putInt(WishesActivity.BUNDLE_CATEGORY_ID, categoryId);
		intent.putExtras(b);
		startActivity(intent);
	}

	public void setFavoritesCount(int totalCount) {
		if (totalCount < 0)
			totalCount = 0;
		btn_favorite.setText(String.format("%s (%d)",
				getText(R.string.favorites), totalCount));

	}

}
