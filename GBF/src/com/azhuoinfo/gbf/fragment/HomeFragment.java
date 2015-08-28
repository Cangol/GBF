package com.azhuoinfo.gbf.fragment;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.actionbar.view.SearchView.OnSearchTextListener;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.azhuoinfo.gbf.R;
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
			}
		});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener() {

			@Override
			public void onClick(View v, int action) {
				if (mPromptView.ACTION_RETRY == action) {

				}
			}

		});
		getSearchList("");
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
					getSearchList(keywords);
					return true;
				}

			});
			break;
		}
		return super.onMenuActionSelected(action);
	}

	private void getSearchList(String keywords) {
		ArrayList<Banner> list = new ArrayList<Banner>();
		list.add(new Banner("1", "Bags", "", "drawable://" + R.drawable.bg_category_bag));
		list.add(new Banner("2", "Watches", "", "drawable://" + R.drawable.bg_category_watch));
		list.add(new Banner("3", "Jewelry", "", "drawable://" + R.drawable.bg_category_jewelry));
		mBannerAdapter.addAll(list);
		mBannerGallery.setAdapter(mBannerAdapter, list.size());
		mBannerGallery.startFadeOut(3000L);
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
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}

	@Override
	public boolean isCleanStack() {
		return true;
	}
}
