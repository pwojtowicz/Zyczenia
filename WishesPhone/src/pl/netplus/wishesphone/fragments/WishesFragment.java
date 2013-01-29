package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesphone.AppBaseActivity;
import pl.netplus.wishesphone.R;
import pl.netplus.wishesphone.WishesActivity;
import pl.netplus.wishesphone.support.WishesGlobals;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WishesFragment extends BaseFragment<ContentObject> implements
		IReadRepository {

	private ContentObject item;
	private int currentCategoryId;

	private int actualWishId = 0;

	private ArrayList<ContentObject> objects;
	private TextView tv_actual;
	private TextView tv_wish;
	private TextView tv_rating;
	private Button btn_next;
	private Button btn_previous;
	private Button btn_favorite;
	private TextView tv_lenght;

	@Override
	public void onResume() {
		super.onResume();
		reload();
	}

	public WishesFragment() {
		super(R.layout.fragment_single_wish_layout, ERepositoryTypes.SingleWish);
	}

	@Override
	public void linkViews(View convertView) {
		tv_actual = (TextView) convertView
				.findViewById(R.id.txtv_actual_element_number);

		tv_wish = (TextView) convertView.findViewById(R.id.txtv_wish);
		tv_lenght = (TextView) convertView.findViewById(R.id.txtv_lenght);

		tv_rating = (TextView) convertView.findViewById(R.id.txtv_rating);

		btn_next = (Button) convertView.findViewById(R.id.btn_next);
		btn_previous = (Button) convertView.findViewById(R.id.btn_previous);
		btn_favorite = (Button) convertView.findViewById(R.id.btn_favorite);

		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nextItem();
			}
		});

		btn_previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				previousItem();
			}
		});

		btn_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeFavoriteState();
			}
		});
	}

	protected void changeFavoriteState() {
		if (objects != null && objects.size() > 0) {
			ContentObject object = objects.get(actualWishId);
			object.setFavorites(!object.isFavorites());

			ObjectManager manager = new ObjectManager();
			manager.insertOrUpdate(this, ERepositoryTypes.Favorite, object);
			reloadContent();
		}
	}

	protected void previousItem() {
		if (actualWishId > 0) {
			actualWishId--;
			reloadContent();
		}
	}

	protected void nextItem() {
		if (actualWishId < objects.size() - 1) {
			actualWishId++;
			reloadContent();
		}
	}

	public void reloadContent() {
		if (objects != null && objects.size() > 0) {
			ContentObject object = objects.get(actualWishId);

			tv_actual.setText(String.format("%d/%d", actualWishId + 1,
					objects.size()));

			tv_lenght.setText(String.format("znaki: %d", object.getText()
					.length()));

			tv_wish.setText(object.getText());

			tv_rating.setText(String.format("%.2f", object.getRating()));

			Drawable drawable = null;
			String text = "";
			if (object.isFavorites()) {
				drawable = getResources().getDrawable(
						R.drawable.cbx_star_enabled);
				text = getString(R.string.removed_from_favorites);
			} else {
				drawable = getResources().getDrawable(
						R.drawable.cbx_star_disabled);
				text = getString(R.string.add_to_favorites);
			}
			btn_favorite.setText(text);
			btn_favorite.setCompoundDrawablesWithIntrinsicBounds(drawable,
					null, null, null);
		}
	}

	@Override
	public void reload() {
		Bundle b = getArguments();
		if (b != null) {
			currentCategoryId = b.getInt(WishesActivity.BUNDLE_CATEGORY_ID);

			objects = WishesGlobals.getInstance().getCategoriesContentObjects(
					currentCategoryId);

			if (objects != null)
				reloadContent();
		}
	}

	@Override
	public void onTaskEnd() {
		((AppBaseActivity) getActivity()).onTaskEnd();
	}

	@Override
	public void onTaskStart(String message) {
		((AppBaseActivity) getActivity())
				.onTaskStart(getString(R.string.progress_save_changes));

	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {

	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		((AppBaseActivity) getActivity()).onTaskInvalidResponse(exception);

	}

	@Override
	public void onTaskProgressUpdate(int actualProgress) {
		((AppBaseActivity) getActivity()).onTaskProgressUpdate(actualProgress);

	}

}
