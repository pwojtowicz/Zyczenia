package pl.netplus.appbase.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import pl.netplus.appbase.entities.FragmentObject;
public class FragmentAdapter extends FragmentPagerAdapter {

	protected ArrayList<FragmentObject> fragments = new ArrayList<FragmentObject>();


	public FragmentAdapter(FragmentManager fm,
			ArrayList<FragmentObject> fragments, int startIndex) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int i) {

		FragmentObject fragmentObject = fragments.get(i);
		
		return fragmentObject.getFragment();
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return FragmentPagerAdapter.POSITION_NONE;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		FragmentObject fo = fragments.get(position);
		if (fo != null) {
			return fo.getTitle();
		}
		return "";
	}

	public void addFragment(FragmentObject newFragment) {
		fragments.add(newFragment);
		this.notifyDataSetChanged();
	}

	public void removeFragment(int fragmentIndex) {
		fragments.remove(fragmentIndex);
		this.notifyDataSetChanged();
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}
}
