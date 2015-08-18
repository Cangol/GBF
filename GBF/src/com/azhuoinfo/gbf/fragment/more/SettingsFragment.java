package com.azhuoinfo.gbf.fragment.more;


import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.cache.CacheManager;
import mobi.cangol.mobile.service.upgrade.UpgradeService;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.FileUtils;
import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.fragment.GuideFragment;
import com.azhuoinfo.gbf.model.Upgrade;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.CommonDialog;
import com.azhuoinfo.gbf.view.CommonDialog.OnButtonClickListener;
import com.azhuoinfo.gbf.view.LoadingDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class SettingsFragment extends BaseContentFragment {
	private TextView mPushTextView;
	private CheckBox mPushRadio;
	private CacheManager mCacheManager;
	private UpgradeService mUpgradeService;
	private ApiContants mApiContants;
	private AccountVerify mAccountVerify;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCacheManager=(CacheManager) this.getAppService(AppService.CACHE_MANAGER);
		mUpgradeService=(UpgradeService) this.getAppService(AppService.UPGRADE_SERVICE);
		mApiContants=ApiContants.instance(getActivity());
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
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
	protected void initData(Bundle savedInstanceState) {
	}

	@Override
	protected void findViews(View view) {
		mPushTextView = (TextView) this.findViewById(R.id.textview_settings_push);
		mPushRadio = (CheckBox) findViewById(R.id.radio_settings_push);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_settings);
		//mPushRadio.setChecked(!JPushInterface.isPushStopped(getActivity()));
		mPushRadio.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setPushEnable(isChecked);
				if(isChecked){
					//JPushInterface.resumePush(getActivity());
					//mPushTextView.setText(R.string.settings_push_on);
				}else{
					//JPushInterface.stopPush(getActivity());
					//mPushTextView.setText(R.string.settings_push_off);
				}
				
			}
			
		});
		this.findViewById(R.id.linearlayout_settings_guide).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putBoolean("isDisplay", true);
				replaceFragment(GuideFragment.class,"GuideFragment",bundle);
			}

		});
		this.findViewById(R.id.linearlayout_settings_help).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(Constants.URL_HELP);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				try{
					startActivity(intent);
				}catch(ActivityNotFoundException e){
					showToast(R.string.app_activity_not_found);
				}
			}

		});
		this.findViewById(R.id.linearlayout_settings_clearcache).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showClearCacheDialog();
			}

		});
		this.findViewById(R.id.linearlayout_settings_feedback).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(FeedbackFragment.class);
			}

		});

		this.findViewById(R.id.linearlayout_settings_about).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(AboutFragment.class);
			}

		});
	}
	private void clearCache(){
		String sizeStr=FileUtils.formatSize(mCacheManager.size());
		//数据缓存
		mCacheManager.clearCache();
		ImageLoader.getInstance().clearDiskCache();
		showToast(String.format(getString(R.string.clean_cache_format), sizeStr));
	}
	private void showClearCacheDialog(){
		final CommonDialog mDialog = CommonDialog.creatDialog(getActivity());
		mDialog.setTitle(R.string.common_dialog_title);
		mDialog.setMessage(R.string.dialog_cache_content);
		mDialog.setLeftButtonInfo(getString(R.string.common_dialog_confirm),
				new OnButtonClickListener() {
					@Override
					public void onClick(View view2) {
						clearCache();
					}
				});
		mDialog.setRightButtonInfo(getString(R.string.common_dialog_cancel),
				new OnButtonClickListener() {
					@Override
					public void onClick(View view) {

					}
				});
		mDialog.show();

	}
	private void showUpgradeDialog(final Upgrade upgrade){
		CommonDialog dialog=CommonDialog.creatDialog(getActivity());
		dialog.setTitle(R.string.dialog_upgrade_title)
		.setMessage(upgrade.getDesc())
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener(){

			@Override
			public void onClick(View view) {
				///统计
				mUpgradeService.upgradeApk(getActivity().getString(R.string.app_name), upgrade.getUrl(), true);
			}
			
		})
		.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener(){
			@Override
			public void onClick(View view) {
				//do nothing
			}
		});
		dialog.show();
		
	}
	
	private void toUpgrade() {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_COMMON_UPGRADE));
		apiTask.setParams(mApiContants.upgrade());
		apiTask.execute(new OnDataLoader<Upgrade>(){
			private LoadingDialog mLoadingDialog=null;
			@Override
			public void onStart() {
				if(getActivity()!=null)
				mLoadingDialog=LoadingDialog.show(getActivity(),R.string.common_processing);
			}

			@Override
			public void onSuccess(int totalPage,Upgrade upgrade) {
				if(getActivity()!=null)mLoadingDialog.dismiss();
				try {
					if (DeviceInfo.getAppVersion(getActivity()).compareTo(
							upgrade.getVersion()) < 0) {
						showUpgradeDialog(upgrade);
					}else{
						showToast(R.string.app_upgraded);
					}
				} catch (Exception e) {
					Log.d(TAG,"Exception",e);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				if(getActivity()!=null)mLoadingDialog.dismiss();
				showToast(errorResponse);
			}
			
		});

	}
	private void setPushEnable(boolean enable) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_COMMON_PUSHONOFF));
		apiTask.setParams(mApiContants.pushOnOff(enable?"1":"0"));
		apiTask.execute(new OnDataLoader<Object>(){
			private LoadingDialog mLoadingDialog=null;
			@Override
			public void onStart() {
				if(getActivity()!=null)
				mLoadingDialog=LoadingDialog.show(getActivity(),R.string.common_processing);
			}

			@Override
			public void onSuccess(int totalPage,Object object) {
				if(getActivity()!=null)mLoadingDialog.dismiss();
				try {
					
				} catch (Exception e) {
					Log.d(TAG,"Exception",e);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				if(getActivity()!=null)mLoadingDialog.dismiss();
				showToast(errorResponse);
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
}
