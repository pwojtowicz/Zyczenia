package pl.netplus.wishesbase.fragments;

import pl.netplus.wishesbase.enums.ERepositoryTypes;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T> extends Fragment {
		
	private int resources;
	private ERepositoryTypes repositoryType;

	public BaseFragment(int resources,
			ERepositoryTypes repositoryType) {
		
		this.resources = resources;
		this.repositoryType = repositoryType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(this.resources, null);
		linkViews(convertView);
		return convertView;
	}

	public abstract void linkViews(View convertView);
}
