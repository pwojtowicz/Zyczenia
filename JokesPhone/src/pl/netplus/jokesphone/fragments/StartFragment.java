package pl.netplus.jokesphone.fragments;

import pl.netplus.appbase.enums.EDialogType;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.jokesphone.AboutActivity;
import pl.netplus.jokesphone.JokesActivity;
import pl.netplus.jokesphone.MainActivity;
import pl.netplus.jokesphone.R;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartFragment extends BaseFragment<Object> implements
		OnClickListener {

	private Button btn_favorite;

	public static StartFragment newInstance(int index) {
		StartFragment f = new StartFragment();
		// f.setRetainInstance(true);
		return f;
	}

	public StartFragment() {
		super(R.layout.fragment_start_layout, null);

	}

	@Override
	public void linkViews(View convertView) {

		btn_favorite = (Button) convertView.findViewById(R.id.button_favorite);

		Button btn_random = (Button) convertView.findViewById(R.id.btn_random);
		Button btn_webpage = (Button) convertView.findViewById(R.id.btn_web);
		Button btn_otherpage = (Button) convertView
				.findViewById(R.id.btn_other);
		Button btn_add_wish = (Button) convertView
				.findViewById(R.id.btn_add_wish);
		Button btn_about = (Button) convertView.findViewById(R.id.btn_about);
		Button btn_update = (Button) convertView.findViewById(R.id.btn_update);
		Button btn_facebook = (Button) convertView
				.findViewById(R.id.btn_facebook);

		btn_favorite.setTag(EButtonType.Favorite);
		btn_favorite.setOnClickListener(this);

		btn_random.setTag(EButtonType.Random);
		btn_random.setOnClickListener(this);

		btn_update.setTag(EButtonType.Update);
		btn_update.setOnClickListener(this);

		btn_about.setTag(EButtonType.About);
		btn_about.setOnClickListener(this);

		btn_otherpage.setTag(EButtonType.News);
		btn_otherpage.setOnClickListener(this);

		btn_webpage.setOnClickListener(this);
		btn_add_wish.setOnClickListener(this);
		btn_facebook.setOnClickListener(this);

	}

	@Override
	public void reload() {
		setFavoritesCount(NetPlusAppGlobals.getInstance().getFavoritesCount());

	}

	public void setFavoritesCount(int totalCount) {
		// if (totalCount < 0)
		// totalCount = 0;
		// if (btn_favorite != null)
		// btn_favorite.setText(String.format("%s (%d)",
		// getText(R.string.favorites), totalCount));

	}

	private enum EButtonType {
		Favorite, Update, About, Random, News
	}

	@Override
	public void onClick(View view) {
		Object o = view.getTag();
		if (o instanceof EButtonType) {
			switch ((EButtonType) o) {
			case Favorite:
				showFavoritesJokes();
				break;
			case News:
				showNewsJokes();
				break;
			case Random:
				showRandomJokes();
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
		((MainActivity) getActivity()).update(true);
	}

	protected void showFavoritesJokes() {
		if (NetPlusAppGlobals.getInstance().getFavoritesCount() > 0)
			startWishesIntent(NetPlusAppGlobals.ITEMS_FAVORITE,
					getText(pl.netplus.appbase.R.string.favorites).toString());
		else {
			DialogHelper.createDialog(getActivity(), EDialogType.No_Favorites)
					.show();
		}
	}

	protected void showRandomJokes() {
		startWishesIntent(NetPlusAppGlobals.ITEMS_ALL,
				getText(pl.netplus.appbase.R.string.randomObjects).toString());
	}

	protected void showNewsJokes() {
		startWishesIntent(NetPlusAppGlobals.ITEMS_LATEST,
				getText(pl.netplus.appbase.R.string.fromLatest).toString());
	}

	protected void showWebPage(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
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
