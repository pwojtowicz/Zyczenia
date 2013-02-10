package pl.netplus.jokesphone.adapters;

import java.util.ArrayList;

import pl.netplus.jokesphone.fragments.CategoryListFragment;
import pl.netplus.jokesphone.fragments.SearchFragment;
import pl.netplus.jokesphone.fragments.StartFragment;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StartActivityFragmentAdapter extends FragmentPagerAdapter {

	int NUM_ITEMS = 3;

	ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	private String titleSearch;

	private String titleStart;

	private String titleCategories;

	public StartActivityFragmentAdapter(FragmentManager fm, String titleSearch,
			String titleStart, String titleCategories) {
		super(fm);
		this.titleSearch = titleSearch;
		this.titleStart = titleStart;
		this.titleCategories = titleCategories;
		fragments.add(SearchFragment.newInstance(1));
		fragments.add(StartFragment.newInstance(2));
		fragments.add(CategoryListFragment.newInstance(3));
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
		Resources res = Resources.getSystem();
		switch (position) {
		case 0:
			return titleSearch;
		case 1:
			return titleStart;
		case 2:
			return titleCategories;
		default:
			return "";
		}
	}

}
