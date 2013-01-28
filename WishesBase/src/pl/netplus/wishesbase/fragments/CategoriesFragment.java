package pl.netplus.wishesbase.fragments;

import java.util.ArrayList;

import pl.netplus.wishesbase.R;
import pl.netplus.wishesbase.adapters.CategoryListAdapter;
import pl.netplus.wishesbase.entities.Category;
import pl.netplus.wishesbase.enums.ERepositoryTypes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

public class CategoriesFragment extends BaseFragment {

	private ListView lv;

	public CategoriesFragment() {
		super(R.layout.fragment_categories_layout, ERepositoryTypes.Categories);
	}

	@Override
	public void linkViews(View convertView) {
		lv=(ListView)convertView.findViewById(R.id.listView);
		
		ArrayList<Category> items=new ArrayList<Category>();
		items.add(new Category("Noworoczne (123)"));
		items.add(new Category("Walentynkowe (433)"));
		items.add(new Category("Bo¿o Narodzeniowe (230)"));
		items.add(new Category("Dzieñ dziadka (40)"));
		items.add(new Category("Dzieñ babci (50)"));
		
		CategoryListAdapter adapter = new CategoryListAdapter(getActivity(), items);
		
		lv.setAdapter(adapter);
	}
	
}
