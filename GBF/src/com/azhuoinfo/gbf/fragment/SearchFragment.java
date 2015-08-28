package com.azhuoinfo.gbf.fragment;

import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.actionbar.view.SearchView.OnSearchTextListener;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.fragment.adapter.TagAdapter;
import com.azhuoinfo.gbf.view.AllGridView;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class SearchFragment extends BaseContentFragment{
	
	private PromptView mPromptView;
	private ListView mListView;
	private AllGridView mAllGridView;
	private TagAdapter mTagAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_search_list,container,false);
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
		mPromptView=(PromptView) findViewById(R.id.promptView);
		mListView=(ListView) findViewById(R.id.listView);
		mAllGridView=(AllGridView) this.findViewById(R.id.gridView);
		mTagAdapter=new TagAdapter(getActivity());
		mAllGridView.setAdapter(mTagAdapter);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_search);
		
		mPromptView.setOnPromptClickListener(new OnPromptClickListener(){

			@Override
			public void onClick(View v, int action) {
				if(mPromptView.ACTION_RETRY==action){
					
				}
			}
			
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		mAllGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
	private void doSearch(){
		SearchView searchView=this.getCustomActionBar().startSearchMode();
		searchView.setOnSearchTextListener(new OnSearchTextListener(){

			@Override
			public boolean onSearchText(String keywords) {
				getCustomActionBar().stopSearchMode();
				getSearchList(keywords);
				return true;
			}
			
		});
	}
	protected boolean onMenuActionSelected(ActionMenuItem action) {
		switch (action.getId()) {
			case 1 :
				
				break;
		}
		return super.onMenuActionSelected(action);
	}

	private void getSearchList(String keywords){
		
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
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public boolean isCleanStack() {
		if(this.getArguments().getBoolean("isTop")){
			return true;
		}
		return false;
	}
}
