package pl.netplus.wishesbase.adapters;

import java.util.ArrayList;

import pl.netplus.wishesbase.R;
import pl.netplus.wishesbase.entities.Favorite;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class FavoriteListAdapter extends BaseListAdapter<Favorite> {

	public FavoriteListAdapter(Context context, ArrayList<Favorite> items) {
		super(context, items,R.layout.row_favorite_layout);
	}

	@Override
	protected View fillRow(Object item, View convertView) {
		TextView name = (TextView)convertView.findViewById(R.id.itemName);
		
		if(item!=null){
			Favorite cat = (Favorite)item;			
			name.setText(cat.getShortText());	
		}		
		return convertView;
	}

}
