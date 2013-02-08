package pl.netplus.jokesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.OwnSpinnerAdapter;
import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.SpinnerObject;
import pl.netplus.appbase.enums.EDialogType;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.jokesphone.AppBaseActivity;
import pl.netplus.jokesphone.JokesActivity;
import pl.netplus.jokesphone.R;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchFragment extends Fragment implements IReadRepository {

	private Spinner spinner;
	private EditText textToSearch;

	public static SearchFragment newInstance() {
		SearchFragment f = new SearchFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search, container, false);
		spinner = (Spinner) v.findViewById(R.id.spinner_category);

		textToSearch = (EditText) v.findViewById(R.id.edt_search_text);

		Button btn_search = (Button) v.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText();
			}
		});

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		reload();
	}

	public void reload() {
		if (getActivity() != null) {
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
	}

	protected void searchText() {
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_SEARCH, null);
		SpinnerObject value = (SpinnerObject) spinner.getSelectedItem();

		String text = textToSearch.getText().toString().trim();

		if (text.length() == 0)
			DialogHelper.createInfoDialog(getActivity(),
					"Nie podano kryterium wyszukiwania").show();
		else {
			ObjectManager manager = new ObjectManager();
			manager.searchContentObjects(this, value.id, text);
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
		Intent intent = new Intent(getActivity(), JokesActivity.class);
		Bundle b = new Bundle();
		b.putInt(JokesActivity.BUNDLE_CATEGORY_ID, categoryId);
		b.putString(JokesActivity.BUNDLE_TITLE,
				getString(pl.netplus.appbase.R.string.title_search));
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public void onTaskEnd() {
		((AppBaseActivity) getActivity()).onTaskEnd();
	}

	@Override
	public void onTaskStart(String message, boolean showProgress) {
		((AppBaseActivity) getActivity()).onTaskStart(
				getString(R.string.progress_search), showProgress);
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
