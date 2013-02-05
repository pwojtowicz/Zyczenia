package pl.netplus.wishesphone.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.adapters.OwnSpinnerAdapter;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.SpinnerObject;
import pl.netplus.appbase.fragments.BaseFragment;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import pl.netplus.wishesphone.R;
import android.view.View;
import android.widget.Spinner;

public class SearchFragment extends BaseFragment<Object> {

	private Spinner spinner;

	public SearchFragment() {
		super(R.layout.fragment_search, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void linkViews(View convertView) {
		spinner = (Spinner) convertView.findViewById(R.id.spinner_category);
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

}
