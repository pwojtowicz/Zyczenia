package pl.netplus.appbase.entities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentObject {
	private String title;
	private Fragment fragment;	
	
	public FragmentObject(Fragment fragment, String title, Bundle bundle){
		setTitle(title);
		fragment.setArguments(bundle);
		setFragment(fragment);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Fragment getFragment() {
		return fragment;
	}
	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
}
