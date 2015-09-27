package com.azhuoinfo.gbf.activity;

import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.navigation.DrawerNavigationFragmentActivity;
import mobi.cangol.mobile.navigation.TabNavigationFragmentActivity;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.service.status.StatusListener;
import mobi.cangol.mobile.service.status.StatusService;
import mobi.cangol.mobile.service.upgrade.UpgradeService;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.StringUtils;
import mobi.cangol.mobile.utils.TimeUtils;

import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.AccountVerify.OnLoginListener;
import com.azhuoinfo.gbf.ModuleMenuIDS;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.db.MessageService;
import com.azhuoinfo.gbf.fragment.ChatFragment;
import com.azhuoinfo.gbf.fragment.HomeFragment;
import com.azhuoinfo.gbf.fragment.MenuFragment;
import com.azhuoinfo.gbf.fragment.user.UserLoginFragment;
import com.azhuoinfo.gbf.model.Upgrade;
import com.azhuoinfo.gbf.model.User;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.CommonDialog;
import com.azhuoinfo.gbf.view.CommonDialog.OnButtonClickListener;
import com.azhuoinfo.gbf.view.MessageBar;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;

@SuppressLint("ResourceAsColor")
public class MainActivity extends TabNavigationFragmentActivity implements OnLoginListener, StatusListener {
	private static long back_pressed;
	private boolean isBackPressed;
	private MessageBar mMessageBar;
	private CommonDialog mLoginDialog;
	private AccountVerify mAccountVerify;
	private GlobalData mGlobalData;
	private UpgradeService mUpgradeService;
	private ApiContants mApiContants;
	private MessageService mMessageService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getCustomActionBar().setDisplayShowHomeEnabled(true);
		this.setStatusBarTintColor(R.color.actionbar_background);
		setContentView(R.layout.activity_main);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		initStatus();
		if (savedInstanceState == null) {
			// 启用动画
			this.getCustomFragmentManager().setDefaultAnimation(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
			this.setMenuFragment(MenuFragment.class, null);
			this.setContentFragment(HomeFragment.class, "CenterFragment", null, ModuleMenuIDS.MODULE_HOME);
			if (mGlobalData.get(Constants.KEY_CHECK_UPGRADE) == null || !TimeUtils.getCurrentDate().equals(mGlobalData.get(Constants.KEY_CHECK_UPGRADE))) {
				// 判断升级提示是否检测过，每天只检测一次
				checkUpgrade();
			}
			this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.ic_action_message, R.drawable.ic_back);
		} else {
			Log.d("savedInstanceState=" + savedInstanceState);
		}
		// 替换背景
		// this.getWindow().setBackgroundDrawableResource(R.drawable.activity_bg);
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);

		//SocialProvider.getInstance(this).configPlatforms(app.isDevMode());
		//SocialProvider.getInstance(this).addWXPlatform("wxe6e851e996ce64b9", "6410e77c9613c770bb0e84340e72bd29");
		//SocialProvider.getInstance(this).addQQQZonePlatform("101137147", "0e06e671ca5598b30dbd51893d62b5e2", "http://www.laoyuegou.com");
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	public int getContentFrameId() {
		return R.id.content_frame;
	}
	@Override
	public void findViews() {
		Log.d("getMD5Fingerprint=" + DeviceInfo.getMD5Fingerprint(this));
		mMessageBar = (MessageBar) this.findViewById(R.id.messageBar);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void initViews(Bundle savedInstanceState) {

	}

	@Override
	public void initData(Bundle savedInstanceState) {
		StatusService statusService = (StatusService) getAppService(AppService.STATUS_SERVICE);
		statusService.registerStatusListener(this);
		if (savedInstanceState == null) {
			NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancelAll();
		} else {

		}
		if (!mAccountVerify.isLogin()) {
			mAccountVerify.verifyToken();
		}
	}
	private void initStatus() {

		mGlobalData = (GlobalData) getAppService(AppService.GLOBAL_DATA);
		mUpgradeService = (UpgradeService) getAppService(AppService.UPGRADE_SERVICE);
		mApiContants = ApiContants.instance(this);

		mAccountVerify = AccountVerify.getInstance(this);
		mAccountVerify.registerLoginListener(this);

		mMessageService = new MessageService(this);
	}
	@Override
	protected void onDestroy() {
		mAccountVerify.unregisterLoginListener(this);
		mGlobalData.save(Constants.KEY_EXIT_CODE, "0");
		mGlobalData.save(Constants.KEY_EXIT_VERSION, DeviceInfo.getAppVersion(this));
		super.onDestroy();
		if (isBackPressed) {
			Log.e("app.exit");
			app.exit();
		}

	}

	@Override
	public void onBack() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			isBackPressed = true;
			super.onBack();
		} else {
			showToast(R.string.app_exit, 2000);
			back_pressed = System.currentTimeMillis();
		}
	}
	private void showLoginDialog() {
		mLoginDialog = CommonDialog.creatDialog(this);
		mLoginDialog.setTitle(R.string.dialog_expire_title).setMessage(R.string.dialog_expire_content).setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				setContentFragment(UserLoginFragment.class, "LoginFragment", null);
			}

		}).setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {
			@Override
			public void onClick(View view) {
				// do nothing
			}
		});
		mLoginDialog.show();

	}
	private void showUpgradeDialog(final Upgrade upgrade) {
		CommonDialog dialog = CommonDialog.creatDialog(this);
		dialog.setTitle(R.string.dialog_upgrade_title);
		dialog.setMessage(upgrade.getDesc());
		// 当前版本低于 最新版本的最低支持版本 则强制升级
		if (DeviceInfo.getAppVersion(this).compareTo(upgrade.getMinVersion()) < 0) {
			// 强制升级
			dialog.setRightButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {
				@Override
				public void onClick(View view) {
					// do nothing
					mUpgradeService.upgrade(getString(R.string.app_name), upgrade.getUrl(), true);

				}
			});
			dialog.setLeftButtonInfo(getString(R.string.common_dialog_exit), new OnButtonClickListener() {

				@Override
				public void onClick(View view) {
					finish();
				}
			});
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
		} else {
			dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {
				@Override
				public void onClick(View view) {
					// do nothing

				}
			});
			dialog.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

				@Override
				public void onClick(View view) {
					// /统计
					mUpgradeService.upgradeApk(getString(R.string.app_name), upgrade.getUrl(), true);

				}
			});
			// dialog.setOnCancelListener(new OnCancelListener(){
			//
			// @Override
			// public void onCancel(DialogInterface dialog) {
			// toMain();
			// }
			//
			// });
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
		}
		dialog.show();
	}

	// 检测升级
	private void checkUpgrade() {
		ApiTask apiTask = ApiTask.build(this, "checkUpgrade");
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_COMMON_UPGRADE));
		apiTask.setParams(mApiContants.upgrade());
		apiTask.execute(new OnDataLoader<Upgrade>() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(int totalPage, Upgrade upgrade) {
				try {
					Log.e("upgrade=" + upgrade);
					if (DeviceInfo.getAppVersion(MainActivity.this).compareTo(upgrade.getVersion()) < 0) {
						showUpgradeDialog(upgrade);
						if (DeviceInfo.getAppVersion(MainActivity.this).compareTo(upgrade.getMinVersion()) >= 0)
							// 记录更新提示日期
							mGlobalData.save(Constants.KEY_CHECK_UPGRADE, TimeUtils.getCurrentDate());
					}

				} catch (Exception e) {
					Log.d(TAG, "Exception", e);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:" + errorCode + "," + errorResponse);
			}

		});

	}
	private void getUserInfo(String userId, String userToken) {
		ApiTask apiTask = ApiTask.build(this, "getUserInfo");
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_PROFILE));
		apiTask.setParams(mApiContants.userProfile(userId, userToken));
		apiTask.execute(new OnDataLoader<User>() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(int totalPage, User user) {
				try {
					if (user != null) {
						mAccountVerify.setUser(user);
					} else {
						showToast(R.string.common_error);
					}
				} catch (Exception e) {
					Log.d(TAG, "Exception", e);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:" + errorCode + "," + errorResponse);
			}

		});

	}
	private void voterInit() {
		ApiTask apiTask = ApiTask.build(this, TAG);
		apiTask.setMethod("POST");
		apiTask.setUrl(ApiContants.instance(this).getActionUrl(ApiContants.API_VOTER_INIT));
		apiTask.setParams(mApiContants.voterInit(mAccountVerify.getUserId(),mAccountVerify.getUserToken()));
		apiTask.execute(new OnDataLoader<Object>() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(int totalPage, final Object voter) {
				
			}
			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:" + errorCode + "," + errorResponse);
			}

		});

	}
	@Override
	public void login() {
		// 获取用户信息
		getUserInfo(mAccountVerify.getUserId(), mAccountVerify.getUserToken());
		// 每天第一次登录
		String date = (String) mGlobalData.get(Constants.KEY_LAST_LOGIN_DATE);
		Log.d("last_login_date:" + date);
		if (!StringUtils.isEmpty(date) && !TimeUtils.getCurrentDate().equals(date)) {
			Log.d("loginPerDay");
			showToast("欢迎回来！");
		}
		mGlobalData.save(Constants.KEY_LAST_LOGIN_DATE, TimeUtils.getCurrentDate());
		voterInit();
	}

	@Override
	public void logout() {

	}

	@Override
	public void expire() {
		// token过期提示重新登录
		if (!app.mSession.getBoolean(Constants.KEY_IS_LOADING)) {
			if (mLoginDialog != null && mLoginDialog.isShow()) {
				return;
			}
			showLoginDialog();
		}

	}
	@Override
	public void callStateIdle() {
	}

	@Override
	public void callStateOffhook() {
	}

	@Override
	public void callStateRinging() {
	}

	@Override
	public void networkConnect(Context arg0) {
		Log.d("networkConnect");
		mMessageBar.setVisibility(View.GONE);
		if (!mAccountVerify.isLogin()) {
			if (StringUtils.isNotBlank(mAccountVerify.getUserId()) && StringUtils.isNotBlank(mAccountVerify.getUserToken())) {
				mAccountVerify.verifyToken();
			}
		}
	}

	@Override
	public void networkDisconnect(Context arg0) {
		Log.d("networkDisconnect messagerbar 提示");
		mMessageBar.setMessage(R.string.common_network_error, new Intent(Settings.ACTION_WIFI_SETTINGS));
	}

	@Override
	public void networkTo3G(Context arg0) {
		mMessageBar.setMessage(R.string.common_network_to3g);
	}

	@Override
	public void storageMount(Context arg0) {
	}

	@Override
	public void storageRemove(Context arg0) {
		mMessageBar.setMessage(R.string.common_storage_remove);

	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onHomeIndicatorClick() {
		this.setContentFragment(ChatFragment.class, "ChatFragment", null);
	}
}
