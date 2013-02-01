package pl.netplus.appbase.adapters;

import java.util.ArrayList;

import pl.netplus.appbase.R;
import pl.netplus.appbase.entities.Category;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class CategoryListAdapter extends BaseListAdapter<Category> {

	public CategoryListAdapter(Context context, ArrayList<Category> items,
			int rowCategoryLayout) {
		super(context, items, rowCategoryLayout);
	}

	@Override
	protected View fillRow(Object item, View convertView) {

		TextView name = (TextView) convertView.findViewById(R.id.itemName);
		if (item != null) {
			Category cat = (Category) item;

			name.setText(cat.getName()
					+ (cat.getId() > 0 ? " (" + String.valueOf(cat.getCount())
							+ ")" : ""));

			convertView.setTag(cat);
		}

		return convertView;
	}

}
