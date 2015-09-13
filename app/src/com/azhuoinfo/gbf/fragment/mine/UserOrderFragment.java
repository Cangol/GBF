package com.azhuoinfo.gbf.fragment.mine;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.fragment.adapter.OrderAdapter;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class UserOrderFragment extends BaseContentFragment {

	private PromptView mPromptView;
	private ListView mListView;
	private OrderAdapter mOrderAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_list, container, false);
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
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.getCustomActionBar().setTitle(R.string.title_mine_order);
		mOrderAdapter = new OrderAdapter(getActivity());
		mListView.setAdapter(mOrderAdapter);
		mOrderAdapter.setOnActionClickListener(new OrderAdapter.OnActionClickListener() {

					@Override
					public void onExchangeClick(int position) {
						exchangeOrder(position);
					}

					@Override
					public void onDeliveryClick(int position) {
						deliveryOrder(position);
					}

					@Override
					public void onDetailsClick(int position) {
						detailsOrder(position);
					}
				});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener() {

			@Override
			public void onClick(View v, int action) {
				if (mPromptView.ACTION_RETRY == action) {
					getOrderList();
				}
			}

		});
	}


	protected void detailsOrder(int position) {
		// TODO Auto-generated method stub
		
	}

	protected void deliveryOrder(int position) {
		// TODO Auto-generated method stub
		
	}

	protected void exchangeOrder(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		getOrderList();
	}

	private void getOrderList() {

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
