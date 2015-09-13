package com.azhuoinfo.gbf.fragment;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.fragment.adapter.CategoryAdapter;
import com.azhuoinfo.gbf.model.Category;
import com.azhuoinfo.gbf.utils.AnimationUtils;
import com.azhuoinfo.gbf.view.AllGridView;

public class CategoryFragment extends BaseContentFragment {

	private AllGridView mBagsGridView;
	private AllGridView mJewelryGridView;
	private AllGridView mWatchGridView;
	private TextView mBagTextView;
	private TextView mWatchTextView;
	private TextView mJewelryTextView;
	private CategoryAdapter mBagAdapter;
	private CategoryAdapter mWatchAdapter;
	private CategoryAdapter mJewelryAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_category, container, false);
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
		mBagTextView = (TextView) this.findViewById(R.id.textview_category_bags);
		mWatchTextView = (TextView) this.findViewById(R.id.textview_category_watch);
		mJewelryTextView = (TextView) this.findViewById(R.id.textview_category_jewelry);

		mBagsGridView = (AllGridView) this.findViewById(R.id.allgridview_category_bags);
		mWatchGridView = (AllGridView) this.findViewById(R.id.allgridview_category_watch);
		mJewelryGridView = (AllGridView) this.findViewById(R.id.allgridview_category_jewelry);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_category);
		mBagAdapter = new CategoryAdapter(this.getActivity());
		mWatchAdapter = new CategoryAdapter(this.getActivity());
		mJewelryAdapter = new CategoryAdapter(this.getActivity());
		mBagsGridView.setAdapter(mBagAdapter);
		mWatchGridView.setAdapter(mWatchAdapter);
		mJewelryGridView.setAdapter(mJewelryAdapter);
		mBagTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBagsGridView.getVisibility() == View.GONE) {
					AnimationUtils.expand(mBagsGridView);
				} else {
					AnimationUtils.collapse(mBagsGridView);
				}
			}
		});

		mWatchTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mWatchGridView.getVisibility() == View.GONE) {
					AnimationUtils.expand(mWatchGridView);
				} else {
					AnimationUtils.collapse(mWatchGridView);
				}
			}
		});

		mJewelryTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mJewelryGridView.getVisibility() == View.GONE) {
					AnimationUtils.expand(mJewelryGridView);
					((ScrollView)getView().findViewById(R.id.scrollview_category)).scrollBy(0, mJewelryGridView.getMeasuredHeight());
				} else {
					AnimationUtils.collapse(mJewelryGridView);
					((ScrollView)getView().findViewById(R.id.scrollview_category)).scrollBy(0, -mJewelryGridView.getMeasuredHeight());
				}
			}
		});
		mBagsGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category item = (Category) parent.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putString("catgoryId", item.getId());
				replaceFragment(GoodsListFragment.class, "GoodsListFragment", bundle);
			}
		});
		mWatchGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category item = (Category) parent.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putString("catgoryId", item.getId());
				replaceFragment(GoodsListFragment.class, "GoodsListFragment", bundle);
			}
		});
		mJewelryGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category item = (Category) parent.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putString("catgoryId", item.getId());
				replaceFragment(GoodsListFragment.class, "GoodsListFragment", bundle);
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		List<Category> list = new ArrayList<Category>();
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));
		list.add(new Category(null, null, "Tset"));

		mBagAdapter.addAll(list);
		mWatchAdapter.addAll(list);
		mJewelryAdapter.addAll(list);

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
