package pl.netplus.appbase.entities;

import android.support.v4.app.Fragment;

public class FragmentObject {
	private Fragment fragment;
	private String title;

	public FragmentObject(Fragment fragment) {
		setFragment(fragment);
	}

	public FragmentObject(Fragment fragment, String title) {
		setFragment(fragment);
		this.title = title;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public String getTitle() {
		return title;
	}
}
