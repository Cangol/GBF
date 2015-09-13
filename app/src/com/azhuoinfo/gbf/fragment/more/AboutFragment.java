package com.azhuoinfo.gbf.fragment.more;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.upgrade.UpgradeService;
import mobi.cangol.mobile.utils.DeviceInfo;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.model.Upgrade;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.CommonDialog;
import com.azhuoinfo.gbf.view.CommonDialog.OnButtonClickListener;
import com.azhuoinfo.gbf.view.LoadingDialog;

import org.OpenUDID.OpenUDID_manager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends BaseContentFragment{
	private TextView mVersionTextView;
	private TextView mBuildTextView;
	private TextView mDeviceTextView;
	private UpgradeService mUpgradeService;
	private ApiContants mApiContants;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUpgradeService=(UpgradeService) this.getAppService(AppService.UPGRADE_SERVICE);
		mApiContants=ApiContants.instance(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_about, container,false);
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
		mVersionTextView=(TextView) this.findViewById(R.id.textview_aboutus_version);
		mBuildTextView=(TextView) this.findViewById(R.id.textview_aboutus_build);
		mDeviceTextView=(TextView) this.findViewById(R.id.textview_aboutus_devices);
		mBuildTextView.setText(String.format(getString(R.string.app_build), 
				DeviceInfo.getAppMetaData(this.getActivity(), "BUILD_DATE"),
				DeviceInfo.getAppMetaData(this.getActivity(), "BUILD_SVNV"),
				ApiContants.getChannelID(getActivity())
				));
		mVersionTextView.setText(String.format(getString(R.string.app_version), 
				DeviceInfo.getAppVersion(this.getActivity())
				));
		if(app.isDevMode()){
			mDeviceTextView.setText(String.format(getString(R.string.app_devices),OpenUDID_manager.getOpenUDID()));
		}
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_aboutus);
		findViewById(R.id.linearlayout_aboutus_upgrade).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				toUpgrade();
			}
			
		});
		findViewById(R.id.linearlayout_aboutus_copyright).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				toCopyright();
			}
			
		});
		findViewById(R.id.linearlayout_aboutus_service).setOnClickListener(new OnClickListener(){
					
					@Override
					public void onClick(View v) {
						toService();
					}
					
				});
		findViewById(R.id.linearlayout_aboutus_private).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				toPrivate();
			}
			
		});
		findViewById(R.id.linearlayout_aboutus_rating).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				toRating();
			}
			
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}
	protected void  toCopyright(){
		Uri uri = Uri.parse(Constants.URL_COPYRIGHT);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		try{
			startActivity(intent);
		}catch(ActivityNotFoundException e){
			showToast(R.string.app_activity_not_found);
		}
	}
	protected void  toService(){
		Uri uri = Uri.parse(Constants.URL_SERVICES);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		try{
			startActivity(intent);
		}catch(ActivityNotFoundException e){
			showToast(R.string.app_activity_not_found);
		}
	}
	protected void  toPrivate(){
		Uri uri = Uri.parse(Constants.URL_PRIVATE);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		try{
			startActivity(intent);
		}catch(ActivityNotFoundException e){
			showToast(R.string.app_activity_not_found);
		}
	}
	protected void toUpgrade() {
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
	protected void toRating() {
		Uri uri = Uri.parse("market://details?id="+ this.getActivity().getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			showToast(R.string.app_not_launcher_market);
		}
	}
	
	private void showUpgradeDialog(final Upgrade upgrade){
		CommonDialog dialog=CommonDialog.creatDialog(getActivity());
		dialog.setTitle(R.string.dialog_upgrade_title)
		.setMessage(upgrade.getDesc())
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener(){

			@Override
			public void onClick(View view) {
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
	
	public void download(String url){
		
	}
	
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public boolean isCleanStack() {
		return false;
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
