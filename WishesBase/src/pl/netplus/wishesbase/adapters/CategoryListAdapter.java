package pl.netplus.wishesbase.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import pl.netplus.wishesbase.R;
import pl.netplus.wishesbase.entities.Category;

public class CategoryListAdapter extends BaseListAdapter<Category> {

	public CategoryListAdapter(Context context, ArrayList<Category> items) {
		super(context, items,R.layout.row_category_layout);
	}


	@Override
	protected View fillRow(Object item, View convertView) {

		TextView name = (TextView)convertView.findViewById(R.id.itemName);
		
		if(item!=null){
			Category cat = (Category)item;			
			name.setText(cat.getName());	
		}
		
		return convertView;
	}

	

}
