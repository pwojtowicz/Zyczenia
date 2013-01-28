package pl.netplus.wishesphone;

import java.util.ArrayList;

import pl.netplus.wishesbase.adapters.FragmentAdapter;
import pl.netplus.wishesbase.entities.FragmentObject;
import pl.netplus.wishesbase.fragments.CategoriesFragment;
import pl.netplus.wishesbase.fragments.FavoritesFragment;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class RootActivity extends FragmentActivity {

	ViewPager mViewPager;
	pl.netplus.wishesbase.adapters.FragmentAdapter fAdapter;
	private ArrayList<FragmentObject> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
		
		fragments = new ArrayList<FragmentObject>();

		fragments.add(new FragmentObject(new CategoriesFragment(),"Kategorie",null));
		
		fragments.add(new FragmentObject(new FavoritesFragment(),"Ulubione",null));
		
		fragments.add(new FragmentObject(new CategoriesFragment(),"Inne",null));
		
		fAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments,
				0);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_root, menu);
		return true;
	}

}
