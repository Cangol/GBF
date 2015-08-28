package com.azhuoinfo.gbf.fragment;

import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.fragment.adapter.BrandAdapter;
import com.azhuoinfo.gbf.model.Brand;
import com.azhuoinfo.gbf.view.PromptView;
import com.azhuoinfo.gbf.view.PromptView.OnPromptClickListener;

public class BrandFragment extends BaseContentFragment{
	
	private PromptView mPromptView;
	private GridView mGridView;
	private BrandAdapter mDataAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_brand,container,false);
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
		mGridView=(GridView) findViewById(R.id.gridView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_brand);
		mDataAdapter=new BrandAdapter(getActivity());
		mGridView.setAdapter(mDataAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Brand item=(Brand) parent.getItemAtPosition(position);
				Bundle bundle=new Bundle();
				bundle.putString("brandId", item.getId());
				bundle.putString("brandName", item.getName());
				setContentFragment(GoodsListFragment.class, "GoodListFragment", bundle);
			}
			
		});
		mPromptView.setOnPromptClickListener(new OnPromptClickListener(){

			@Override
			public void onClick(View v, int action) {
				if(mPromptView.ACTION_RETRY==action){
					getDataList();
				}
			}
			
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		getDataList();
	}
	
	protected void updateViews(List<Brand> list) {
		if(list!=null&&list.size()>0){
			mDataAdapter.clear();
			mDataAdapter.addAll(list);
			if(mDataAdapter.getCount()>0){
				mPromptView.showContent();
			}else{
				mPromptView.showPrompt(R.string.common_empty);
			}
		}else{
			mPromptView.showPrompt(R.string.common_empty);
		}
	}
	
	private void getDataList(){
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_POLL_AREA));
		//apiTask.setParams(ApiContants.instance(getActivity()).brand());
		apiTask.execute(new OnDataLoader<List<Brand>>(){

			@Override
			public void onStart() {
				if(getActivity()!=null)
				mPromptView.showLoading();
			}

			@Override
			public void onSuccess(int totalPage,final List<Brand> list) {
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
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public boolean isCleanStack() {
		return true;
	}
}
