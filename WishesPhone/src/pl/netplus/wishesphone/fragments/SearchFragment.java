package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.OwnSpinnerAdapter;
import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.SpinnerObject;
import pl.netplus.appbase.enums.EDialogType;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.AppBaseActivity;
import pl.netplus.wishesphone.R;
import pl.netplus.wishesphone.WishesActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchFragment extends BaseFragment<Object> implements
		IReadRepository {

	private Spinner spinner;

	public SearchFragment() {
		super(R.layout.fragment_search, null);
	}

	@Override
	public void linkViews(View convertView) {
		spinner = (Spinner) convertView.findViewById(R.id.spinner_category);

		Button btn_search = (Button) convertView.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		reload();
	}

	@Override
	public void reload() {

		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();
		SpinnerObject[] content = new SpinnerObject[categories.size() + 1];

		content[0] = new SpinnerObject(0,
				getString(pl.netplus.appbase.R.string.all_categories));
		for (int i = 0; i < categories.size(); i++) {
			Category c = categories.get(i);
			content[i + 1] = new SpinnerObject(c.getId(), c.getName());
		}

		OwnSpinnerAdapter osp = new OwnSpinnerAdapter(getActivity(),
				android.R.layout.simple_spinner_item, content);

		osp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(osp);
	}

	protected void searchText() {
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_SEARCH, null);
		SpinnerObject value = (SpinnerObject) spinner.getSelectedItem();
		EditText edit_text = (EditText) convertView
				.findViewById(R.id.edt_search_text);

		if (edit_text.getText().toString().length() == 0)
			DialogHelper.createInfoDialog(getActivity(),
					"Nie podano kryterium wyszukiwania").show();
		else {
			ObjectManager manager = new ObjectManager();
			manager.searchContentObjects(this, value.id, edit_text.getText()
					.toString());
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			if (((ArrayList<ContentObject>) response.bundle).size() > 0)
				startWishesIntent(NetPlusAppGlobals.ITEMS_SEARCH);
			else
				DialogHelper.createDialog(getActivity(),
						EDialogType.No_SearchResult).show();
		}
	}

	private void startWishesIntent(int categoryId) {
		Intent intent = new Intent(getActivity(), WishesActivity.class);
		Bundle b = new Bundle();
		b.putInt(WishesActivity.BUNDLE_CATEGORY_ID, categoryId);
		b.putString(WishesActivity.BUNDLE_TITLE,
				getString(pl.netplus.appbase.R.string.title_search));
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public void onTaskEnd() {
		((AppBaseActivity) getActivity()).onTaskEnd();
	}

	@Override
	public void onTaskStart(String message) {
		((AppBaseActivity) getActivity())
				.onTaskStart(getString(R.string.progress_search));
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
