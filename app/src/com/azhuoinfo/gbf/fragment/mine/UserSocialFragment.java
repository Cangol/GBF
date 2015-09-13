package com.azhuoinfo.gbf.fragment.mine;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;
import com.azhuoinfo.gbf.view.TabManager;

public class UserSocialFragment extends BaseContentFragment{
	
	private TabManager mTabManager;
	private TabHost mTabHost;
	private String mCurTab = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_social,container,false);
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
		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabManager = new TabManager(this.getChildFragmentManager(), mTabHost, R.id.realtabcontent);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurTab = savedInstanceState.getString("curTab");
		}
		mTabManager.addTab(mTabHost.newTabSpec("UserMessageFragment").setIndicator(tabView(getString(R.string.social_message),true)), UserMessageFragment.class, null);
		mTabManager.addTab(mTabHost.newTabSpec("UserFriendFragment").setIndicator(tabView(getString(R.string.social_friend),false)), UserFriendFragment.class, null);
		if (mCurTab != null) {
			mTabHost.setCurrentTabByTag(mCurTab);
		} else {
			mCurTab = mTabHost.getCurrentTabTag();
		}
	}
	private View tabView(String title,boolean isLeft) {
		View indicatorview = android.view.LayoutInflater.from(this.getActivity()).inflate(R.layout.corner_tab_view, null);
		TextView text = (TextView) indicatorview.findViewById(R.id.tabsText);
		if(isLeft)
			text.setBackgroundResource(R.drawable.corner_tab_left_bg);
		else 
			text.setBackgroundResource(R.drawable.corner_tab_right_bg);
		text.setText(title);
		return indicatorview;
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public boolean isCleanStack() {
		return false;
	}
}
