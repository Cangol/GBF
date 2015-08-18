package com.azhuoinfo.gbf.fragment;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.actionbar.view.SearchView.OnSearchTextListener;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends BaseContentFragment {
	private AccountVerify mAccountVerify;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	protected void findViews(View view) {
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
		
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		actionMenu.add(new ActionMenuItem(1, R.string.action_menu_search, R.drawable.ic_action_search, 1));
		return true;
	}

	@Override
	protected boolean onMenuActionSelected(ActionMenuItem action) {
		switch (action.getId()) {
			case 1 :
				SearchView searchView=this.getCustomActionBar().startSearchMode();
				searchView.setOnSearchTextListener(new OnSearchTextListener(){

					@Override
					public boolean onSearchText(String keywords) {
						getCustomActionBar().stopSearchMode();
						Bundle bundle=new Bundle();
						bundle.putString("keywords",keywords);
						setContentFragment(SearchFragment.class,"SearchFragment",  bundle);
						return true;
					}
					
				});
				break;
		}
		return super.onMenuActionSelected(action);
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		this.getCustomActionBar().stopSearchMode();
		super.onDestroyView();

	}

	@Override
	public boolean isCleanStack() {
		return true;
	}

}
