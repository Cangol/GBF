package com.azhuoinfo.gbf.fragment.publish;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.azhuoinfo.gbf.R;

public class PublishCategoryFragment extends BaseContentFragment{
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_publish_category,container,false);
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
		this.setTitle(R.string.title_publish);
		this.findViewById(R.id.textview_publish_category_bag).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("category", 1);
				replaceFragment(PublishQualityFragment.class,"PublishQualityFragment",bundle);
			}
			
		});
		this.findViewById(R.id.textview_publish_category_watch).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("category", 2);
				replaceFragment(PublishQualityFragment.class,"PublishQualityFragment",bundle);
			}
			
		});
		this.findViewById(R.id.textview_publish_category_jewelry).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("category", 3);
				replaceFragment(PublishQualityFragment.class,"PublishQualityFragment",bundle);
			}
			
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
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
