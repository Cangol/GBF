package com.azhuoinfo.gbf.fragment;

import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.actionbar.view.SearchView.OnSearchTextListener;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.fragment.adapter.BannerAdapter;
import com.azhuoinfo.gbf.fragment.adapter.GoodsAdapter;
import com.azhuoinfo.gbf.fragment.publish.PublishCategoryFragment;
import com.azhuoinfo.gbf.model.Banner;
import com.azhuoinfo.gbf.model.Goods;
import com.azhuoinfo.gbf.view.AutoGallery;
import com.azhuoinfo.gbf.view.HeaderGridView;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class HomeFragment extends BaseContentFragment {

	private PromptView mPromptView;
	private ViewGroup mHeaderView;
	private HeaderGridView mGridView;
	private AutoGallery mBannerGallery;
	private GoodsAdapter mGoodsAdapter;
	private BannerAdapter mBannerAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		mPromptView = (PromptView) findViewById(R.id.promptView);
		mGridView = (HeaderGridView) findViewById(R.id.gridView);

		mHeaderView = (ViewGroup) ViewGroup.inflate(getActivity(), R.layout.layout_home_header, null);
		mBannerGallery = (AutoGallery) mHeaderView.findViewById(R.id.autogallery_home);

		mGridView.addHeaderView(mHeaderView);
		mGoodsAdapter = new GoodsAdapter(getActivity());
		mBannerAdapter = new BannerAdapter(getActivity());
		mGridView.setAdapter(mGoodsAdapter);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
		mHeaderView.findViewById(R.id.textview_menu_checkup).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		mHeaderView.findViewById(R.id.textview_menu_local).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(NearSellerFragment.class);

			}
		});
		mHeaderView.findViewById(R.id.textview_menu_publish).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				replaceFragment(PublishCategoryFragment.class);
			}
		});
		mHeaderView.findViewById(R.id.textview_menu_guess).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(GuessQualityFragment.class);

			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
				Goods item = (Goods) parent.getItemAtPosition(position);
				Bundle bundle=new Bundle();
				bundle.putString("goodsId", item.getId());
				replaceFragment(GoodsDetailsFragment.class,"GoodsDetailsFragment",bundle);
			}
		});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener() {

			@Override
			public void onClick(View v, int action) {
				if (mPromptView.ACTION_RETRY == action) {
					getGoodsList();
				}
			}

		});
		getBannerList();
		getGoodsList();
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}

	@Override
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		actionMenu.add(new ActionMenuItem(1, R.string.action_menu_search, R.drawable.ic_action_search, 1));
		return true;
	}

	@Override
	protected boolean onMenuActionSelected(ActionMenuItem action) {
		switch (action.getId()) {
		case 1:
			SearchView searchView = this.getCustomActionBar().startSearchMode();
			/**
			 * searchView.setActionButtonEnable(true);
			 * searchView.setActioImageResource
			 * (R.drawable.actionbar_search_clear);
			 * searchView.setOnActionClickListener(new
			 * SearchView.OnActionClickListener() {
			 * 
			 * @Override public boolean onActionClick() {
			 * 
			 *           return false; } });
			 **/
			searchView.setSearchTextHint(getString(R.string.search_hint));
			searchView.setOnSearchTextListener(new OnSearchTextListener() {

				@Override
				public boolean onSearchText(String keywords) {
					getCustomActionBar().stopSearchMode();
					doSearch(keywords);
					return true;
				}

			});
			break;
		}
		return super.onMenuActionSelected(action);
	}
	protected void updateViews(List<Goods> list) {
		if(list!=null&&list.size()>0){
			mGoodsAdapter.clear();
			mGoodsAdapter.addAll(list);
			if(mGoodsAdapter.getCount()>0){
				mPromptView.showContent();
			}else{
				mPromptView.showPrompt(R.string.common_empty);
			}
		}else{
			mPromptView.showPrompt(R.string.common_empty);
		}
	}
	protected void doSearch(String keywords) {
		Bundle bundle=new Bundle();
		bundle.putString("keywords", keywords);
		replaceFragment(SearchFragment.class,"SearchFragment",bundle);
		
	}

	private void getBannerList() {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_POLL_AREA));
		//apiTask.setParams(ApiContants.instance(getActivity()).bannerList());
		apiTask.execute(new OnDataLoader<List<Banner>>(){

			@Override
			public void onStart() {
				if(getActivity()!=null)
				mPromptView.showLoading();
			}

			@Override
			public void onSuccess(int totalPage,final List<Banner> list) {
				if(getActivity()!=null){
					mBannerAdapter.addAll(list);
					mBannerGallery.setAdapter(mBannerAdapter, list.size());
					mBannerGallery.startFadeOut(3000L);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				if(getActivity()!=null){
					mPromptView.showRetry();
				}
				
			}
			
		});
		
	}
	private void getGoodsList() {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_POLL_AREA));
		//apiTask.setParams(ApiContants.instance(getActivity()).goodsList());
		apiTask.execute(new OnDataLoader<List<Goods>>(){

			@Override
			public void onStart() {
				if(getActivity()!=null)
				mPromptView.showLoading();
			}

			@Override
			public void onSuccess(int totalPage,final List<Goods> list) {
				if(getActivity()!=null){
					updateViews(list);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				if(getActivity()!=null){
					mPromptView.showRetry();
				}
				
			}
			
		});
	}

	@Override
	public void onDestroyView() {
		this.getCustomActionBar().stopSearchMode();
		super.onDestroyView();

	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}

	@Override
	public boolean isCleanStack() {
		return true;
	}
}
