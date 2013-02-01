package pl.netplus.wishesphone;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.OwnSpinnerAdapter;
import pl.netplus.appbase.asynctask.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.SpinnerObject;
import pl.netplus.appbase.enums.EDialogType;
import pl.netplus.appbase.managers.ObjectManager;
import pl.netplus.wishesbase.support.DialogHelper;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends AppBaseActivity {

	private Spinner spinner;

	@Override
	public void onResume() {
		super.onResume();
		reloadSpinner();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Button btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText();
			}
		});
	}

	private void reloadSpinner() {
		spinner = (Spinner) findViewById(R.id.spinner_category);

		ArrayList<Category> categories = NetPlusAppGlobals.getInstance()
				.getCategories();
		SpinnerObject[] content = new SpinnerObject[categories.size() + 1];

		content[0] = new SpinnerObject(0,
				getString(pl.netplus.appbase.R.string.all_categories));
		for (int i = 0; i < categories.size(); i++) {
			Category c = categories.get(i);
			content[i + 1] = new SpinnerObject(c.getId(), c.getName());
		}

		OwnSpinnerAdapter osp = new OwnSpinnerAdapter(this,
				android.R.layout.simple_spinner_item, content);

		osp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(osp);
	}

	protected void searchText() {
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_SEARCH, null);
		SpinnerObject value = (SpinnerObject) spinner.getSelectedItem();
		EditText edit_text = (EditText) findViewById(R.id.edt_search_text);

		if (edit_text.getText().toString().length() == 0)
			DialogHelper.createDialog(this, EDialogType.Information,
					"Nie podano kryterium wyszukiwania").show();
		else {
			ObjectManager manager = new ObjectManager();
			manager.searchContentObjects(this, value.id, edit_text.getText()
					.toString());
		}
	}

	@Override
	public void onTaskStart(String message) {
		super.onTaskStart(getString(R.string.progress_search));
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			if (((ArrayList<ContentObject>) response.bundle).size() > 0)
				startWishesIntent(NetPlusAppGlobals.ITEMS_SEARCH);
			else
				DialogHelper.createDialog(this, EDialogType.No_SearchResult)
						.show();
		}
	}

	private void startWishesIntent(int categoryId) {
		Intent intent = new Intent(this, WishesActivity.class);
		Bundle b = new Bundle();
		b.putInt(WishesActivity.BUNDLE_CATEGORY_ID, categoryId);
		intent.putExtras(b);
		startActivity(intent);
	}

}
