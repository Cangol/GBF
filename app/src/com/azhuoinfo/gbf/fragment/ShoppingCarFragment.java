package com.azhuoinfo.gbf.fragment;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.fragment.adapter.ShoppingCarAdapter;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class ShoppingCarFragment extends BaseContentFragment {

	private PromptView mPromptView;
	private ListView mListView;
	private ShoppingCarAdapter mShoppingCarAdapter;

	private TextView mCheckAllTextView;
	private TextView mTotalTextView;
	private TextView mCheckouTextView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_shopping_car, container, false);
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
		mCheckAllTextView=(TextView) this.findViewById(R.id.layout_car_bottom_checkall);
		mTotalTextView=(TextView) this.findViewById(R.id.layout_car_bottom_price);
		mCheckouTextView=(TextView) this.findViewById(R.id.layout_car_bottom_checkout);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.getCustomActionBar().setTitle(R.string.title_shopping_car);
		mTotalTextView.setText(Html.fromHtml(String.format(getString(R.string.car_total),1900)));
		mCheckAllTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v.isSelected()){
					v.setSelected(false);
					calcTotal();
				}else{
					v.setSelected(true);
					mShoppingCarAdapter.selectAll();
					calcTotal();
				}
			}
		});
		mShoppingCarAdapter = new ShoppingCarAdapter(getActivity());
		mListView.setAdapter(mShoppingCarAdapter);
		mShoppingCarAdapter.setOnActionClickListener(new ShoppingCarAdapter.OnActionClickListener() {

			@Override
			public void onClickDetails(int position) {
				Bundle bundle=new Bundle();
				//replaceFragment();
			}

			@Override
			public void onClickSelect(int position) {
				calcTotal();
			}

					
		});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener() {

			@Override
			public void onClick(View v, int action) {
				if (mPromptView.ACTION_RETRY == action) {
					getShoppingCarList();
				}
			}

		});
	}


	protected void calcTotal() {
		
		
	}


	@Override
	protected void initData(Bundle savedInstanceState) {
		getShoppingCarList();
	}

	private void getShoppingCarList() {

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
