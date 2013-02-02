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
import pl.netplus.wishesphone.RootActivity;
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
		OnItemClickListener, OnClickListener {

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

		Button btn_random = (Button) footer.findViewById(R.id.btn_random);
		Button btn_webpage = (Button) footer.findViewById(R.id.btn_web);
		Button btn_otherpage = (Button) footer.findViewById(R.id.btn_other);
		Button btn_add_wish = (Button) footer.findViewById(R.id.btn_add_wish);
		Button btn_about = (Button) footer.findViewById(R.id.btn_about);
		Button btn_update = (Button) footer.findViewById(R.id.btn_update);

		btn_favorite.setTag(EButtonType.Favorite);
		btn_favorite.setOnClickListener(this);

		btn_random.setTag(EButtonType.Random);
		btn_random.setOnClickListener(this);

		btn_update.setTag(EButtonType.Update);
		btn_update.setOnClickListener(this);

		btn_about.setTag(EButtonType.About);
		btn_about.setOnClickListener(this);

		btn_otherpage.setOnClickListener(this);
		btn_webpage.setOnClickListener(this);
		btn_add_wish.setOnClickListener(this);
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
			if (cat.getId() == NetPlusAppGlobals.ITEMS_NEET_UPDATE)
				((RootActivity) getActivity()).retryLastAction();
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

	public void setFavoritesCount(int totalCount) {
		if (totalCount < 0)
			totalCount = 0;
		btn_favorite.setText(String.format("%s (%d)",
				getText(R.string.favorites), totalCount));

	}

	@Override
	public void onClick(View view) {
		Object o = view.getTag();
		if (o instanceof EButtonType) {
			switch ((EButtonType) o) {
			case Favorite:
				showFavoritesWish();
				break;
			case Random:
				showRandomWish();
				break;
			case Update:
				upddate();
				break;
			case About:
				showAboutPage();
				break;
			default:
				break;
			}
		} else if (o instanceof String) {
			showWebPage((String) o);
		}
	}

	protected void showAboutPage() {
		Intent intent = new Intent(getActivity(), AboutActivity.class);
		startActivity(intent);
	}

	protected void upddate() {
		((RootActivity) getActivity()).update(true);
	}

	protected void showFavoritesWish() {
		if (NetPlusAppGlobals.getInstance().getFavoritesCount() > 0)
			startWishesIntent(NetPlusAppGlobals.ITEMS_FAVORITE,
					getText(pl.netplus.appbase.R.string.favorites).toString());
		else {
			DialogHelper.createDialog(getActivity(), EDialogType.No_Favorites)
					.show();
		}
	}

	protected void showRandomWish() {
		startWishesIntent(NetPlusAppGlobals.ITEMS_ALL,
				getText(pl.netplus.appbase.R.string.randomObjects).toString());
	}

	protected void showWebPage(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	private enum EButtonType {
		Favorite, Update, About, Random
	}

}
