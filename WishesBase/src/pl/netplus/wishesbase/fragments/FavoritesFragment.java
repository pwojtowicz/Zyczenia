package pl.netplus.wishesbase.fragments;

import java.util.ArrayList;

import pl.netplus.wishesbase.R;
import pl.netplus.wishesbase.adapters.FavoriteListAdapter;
import pl.netplus.wishesbase.entities.Favorite;
import pl.netplus.wishesbase.enums.ERepositoryTypes;
import android.view.View;
import android.widget.ListView;

public class FavoritesFragment extends BaseFragment<Favorite> {

	private ListView lv;

	public FavoritesFragment() {
		super(R.layout.fragment_favorites_layout, ERepositoryTypes.Favorite);
	}

	@Override
	public void linkViews(View convertView) {
		lv=(ListView)convertView.findViewById(R.id.listView);
		
		ArrayList<Favorite> items=new ArrayList<Favorite>();
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());
		
		FavoriteListAdapter adapter = new FavoriteListAdapter(getActivity(), items);
		
		lv.setAdapter(adapter);
	}
}
