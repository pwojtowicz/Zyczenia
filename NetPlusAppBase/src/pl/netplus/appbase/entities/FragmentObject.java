package pl.netplus.appbase.entities;

import android.support.v4.app.Fragment;

public class FragmentObject {
	private Fragment fragment;

	public FragmentObject(Fragment fragment) {
		setFragment(fragment);
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
}
