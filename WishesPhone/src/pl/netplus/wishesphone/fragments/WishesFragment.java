package pl.netplus.wishesphone.fragments;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesphone.AppBaseActivity;
import pl.netplus.wishesphone.R;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WishesFragment extends BaseFragment<ContentObject> implements
		IReadRepository {

	private ContentObject contentObject;

	private TextView tv_wish;
	private TextView tv_rating;
	private Button btn_favorite;
	private TextView tv_lenght;

	public WishesFragment() {
		super(R.layout.fragment_single_wish_layout, ERepositoryTypes.SingleWish);

	}

	@Override
	public void onResume() {
		super.onResume();
		linkViews(super.convertView);
		reloadContent();

		System.out.println("ContentObjectId: "
				+ String.valueOf(contentObject.getId()));
	}

	@Override
	public void linkViews(View convertView) {
		// tv_actual = (TextView) convertView
		// .findViewById(R.id.txtv_actual_element_number);

		tv_wish = (TextView) convertView.findViewById(R.id.txtv_wish);
		tv_lenght = (TextView) convertView.findViewById(R.id.txtv_lenght);

		tv_rating = (TextView) convertView.findViewById(R.id.txtv_rating);

		btn_favorite = (Button) convertView.findViewById(R.id.btn_favorite);

		btn_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeFavoriteState();
			}
		});
	}

	protected void changeFavoriteState() {
		if (contentObject != null) {
			contentObject.setFavorites(!contentObject.isFavorites());
			ObjectManager manager = new ObjectManager();
			manager.insertOrUpdate(this, ERepositoryTypes.Favorite,
					contentObject);
			reloadContent();
		}
	}

	public void reloadContent() {
		if (contentObject != null) {

			// tv_actual.setText(String.format("%d/%d", actualWishId + 1,
			// contentObject.size()));

			tv_lenght.setText(String.format("znaki: %d", contentObject
					.getText().length()));

			tv_wish.setText(contentObject.getText());

			tv_rating.setText(String.format("%.2f", contentObject.getRating()));

			Drawable drawable = null;
			String text = "";
			if (contentObject.isFavorites()) {
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

	public ContentObject getContentObject() {
		return contentObject;
	}

	public void setContentObject(ContentObject contentObject) {
		this.contentObject = contentObject;
	}

}
