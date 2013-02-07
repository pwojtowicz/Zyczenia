package pl.netplus.wishesphone.adapters;

import java.util.ArrayList;

import pl.netplus.wishesphone.fragments.CategoryListFragment;
import pl.netplus.wishesphone.fragments.SearchFragment;
import pl.netplus.wishesphone.fragments.StartFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class StartActivityFragmentAdapter extends FragmentStatePagerAdapter {

	int NUM_ITEMS = 3;

	ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	public StartActivityFragmentAdapter(FragmentManager fm) {
		super(fm);
		fragments.add(SearchFragment.newInstance());
		fragments.add(StartFragment.newInstance());
		fragments.add(CategoryListFragment.newInstance());
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Wyszukiwanie";
		case 1:
			return "Start";
		case 2:
			return "Kategorie";
		default:
			return "";
		}
	}

}
