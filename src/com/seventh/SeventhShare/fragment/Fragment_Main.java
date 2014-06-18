package com.seventh.SeventhShare.fragment;

import com.seventh.SeventhShare.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 *
 */
public class Fragment_Main extends Fragment {
	private View rootView = null;

	private static int num = 0;

	public Fragment_Main() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView(inflater, container);
		return rootView;
	}

	private void initView(LayoutInflater i, ViewGroup c) {
		num = getArguments().getInt("type");
		Fragment fragment=null;
		switch (num){
		case 0:
			fragment=new Fragment_lv();
			break;
		case 1:
			fragment=new Fragment_gv();
			break;
		case 2:
			fragment=new Fragment_Login();
			break;
		}
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		String planet = getResources().getStringArray(R.array.planets_array)[num];
		getActivity().setTitle(planet);
	}
}
