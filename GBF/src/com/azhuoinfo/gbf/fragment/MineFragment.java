package com.azhuoinfo.gbf.fragment;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.fragment.mine.UserFavoriteFragment;
import com.azhuoinfo.gbf.fragment.mine.UserOrderFragment;
import com.azhuoinfo.gbf.fragment.mine.UserSalesFragment;
import com.azhuoinfo.gbf.fragment.mine.UserSocialFragment;
import com.azhuoinfo.gbf.fragment.user.UserInfoFragment;
import com.azhuoinfo.gbf.fragment.user.UserLoginFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MineFragment extends BaseContentFragment{
	
	private ImageView mFaceImageView;	
	private TextView mUserNameText;
	
	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
		mApiContants=ApiContants.instance(getActivity());
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.ic_face_woman)
        .showImageOnFail(R.drawable.ic_face_woman)
        .build();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_mine,container,false);
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
		this.setTitle(R.string.title_mine);
		this.findViewById(R.id.layout_mine_top).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(UserInfoFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_favorite).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(UserFavoriteFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_order).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(UserOrderFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_sales).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(UserSalesFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_shopcar).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(ShoppingCarFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_friend).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					replaceFragment(UserSocialFragment.class);
				}
			}

		});
		this.findViewById(R.id.textview_mine_member).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mAccountVerify.isLogin()){
					replaceFragment(UserLoginFragment.class);
				}else{
					
				}
			}

		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
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
		return true;
	}
}
