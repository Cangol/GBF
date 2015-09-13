package com.azhuoinfo.gbf.fragment;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class GoodsListFragment extends BaseContentFragment {

	private PromptView mPromptView;
	private ListView mListView;
	private RadioGroup mRadioGroup;
	private RadioButton mFilterRadioButton;
	private RadioButton mOrderRadioButton;
	private RadioButton mBrandRadioButton;
	private RadioButton mCategoryRadioButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_goods_list, container, false);
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
		mListView = (ListView) findViewById(R.id.listView);
		mRadioGroup = (RadioGroup) this.findViewById(R.id.radio_goods_tab);
		mFilterRadioButton = (RadioButton) findViewById(R.id.radio_goods_filter);
		mOrderRadioButton = (RadioButton) findViewById(R.id.radio_goods_order);
		mBrandRadioButton = (RadioButton) findViewById(R.id.radio_goods_brand);
		mCategoryRadioButton = (RadioButton) findViewById(R.id.radio_goods_category);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle("");
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_goods_order:
					findViewById(R.id.layout_goods_order).setVisibility(View.VISIBLE);
					findViewById(R.id.layout_goods_brand).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_category).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_filter).setVisibility(View.GONE);
					break;
				case R.id.radio_goods_brand:
					findViewById(R.id.layout_goods_order).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_brand).setVisibility(View.VISIBLE);
					findViewById(R.id.layout_goods_category).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_filter).setVisibility(View.GONE);
					break;
				case R.id.radio_goods_category:
					findViewById(R.id.layout_goods_order).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_brand).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_category).setVisibility(View.VISIBLE);
					findViewById(R.id.layout_goods_filter).setVisibility(View.GONE);
					break;
				case R.id.radio_goods_filter:
					findViewById(R.id.layout_goods_order).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_brand).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_category).setVisibility(View.GONE);
					findViewById(R.id.layout_goods_filter).setVisibility(View.VISIBLE);
					break;
				}

			}
		});
		mOrderRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mOrderRadioButton.isChecked()) {
					findViewById(R.id.layout_goods_order).setVisibility(View.GONE);
				}
			}
		});
		mBrandRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mBrandRadioButton.isChecked()) {
					findViewById(R.id.layout_goods_brand).setVisibility(View.GONE);
				}
			}
		});
		mCategoryRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mCategoryRadioButton.isChecked()) {
					findViewById(R.id.layout_goods_category).setVisibility(View.GONE);
				}
			}
		});
		mFilterRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mFilterRadioButton.isChecked()) {
					findViewById(R.id.layout_goods_filter).setVisibility(View.GONE);
				}
			}
		});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener() {

			@Override
			public void onClick(View v, int action) {
				if (mPromptView.ACTION_RETRY == action) {

				}
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
