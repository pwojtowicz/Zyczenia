package pl.netplus.appbase.fragments;

import java.util.ArrayList;

import pl.netplus.appbase.R;
import pl.netplus.appbase.adapters.FavoriteListAdapter;
import pl.netplus.appbase.entities.Favorite;
import pl.netplus.appbase.enums.ERepositoryTypes;
import android.view.View;
import android.widget.ListView;

public class FavoritesFragment extends BaseFragment<Favorite> {

	private ListView lv;

	public FavoritesFragment() {
		super(R.layout.fragment_favorites_layout, ERepositoryTypes.Favorite);
	}

	@Override
	public void linkViews(View convertView) {
		lv = (ListView) convertView.findViewById(R.id.listView);

		ArrayList<Favorite> items = new ArrayList<Favorite>();
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());
		items.add(new Favorite());

		FavoriteListAdapter adapter = new FavoriteListAdapter(getActivity(),
				items);

		lv.setAdapter(adapter);
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}
}
