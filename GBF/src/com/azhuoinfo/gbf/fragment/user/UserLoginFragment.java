package com.azhuoinfo.gbf.fragment.user;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.StringUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.model.UserAuth;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;

public class UserLoginFragment extends BaseContentFragment{
	public static final int LOGIN_REQUEST_CODE=1;
	private EditText mUsernameText;
	private EditText mPasswordText;
	private EditText mVerifyCodeText;
	private TextView mForgetText;
	private Button mLoginBtn;
	private TextView mRegisterText;

	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	//private SocialLoginProvider mSocialLoginProvider;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApiContants=ApiContants.instance(getActivity());
		mAccountVerify = AccountVerify.getInstance(getActivity());
		//mSocialLoginProvider=SocialProvider.getInstance(getActivity()).getSocialLoginProvider();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_user_login, container,false);
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
		this.getCustomActionBar().setBackgroundColor(Color.TRANSPARENT);
		mUsernameText=(EditText) this.findViewById(R.id.edittext_user_login_mobile);
		mPasswordText=(EditText) this.findViewById(R.id.edittext_user_login_password);
		mVerifyCodeText=(EditText) this.findViewById(R.id.edittext_user_login_verifycode);
		mForgetText=(TextView) this.findViewById(R.id.textview_user_login_forget);
		
		mLoginBtn=(Button) this.findViewById(R.id.button_user_login_login);
		mRegisterText=(TextView) this.findViewById(R.id.textview_user_login_register_hint);
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_login);
		mRegisterText.setText(Html.fromHtml(getString(R.string.user_register_hint)));
		mLoginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(validator()){
					String username = mUsernameText.getText().toString();
					String password = mPasswordText.getText().toString();
					doLogin(username,StringUtils.md5(password));
				}
			}

		});
		mRegisterText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(UserRegisterFragment.class,"RegisterFragment",null,Constants.LOGIN_REQUEST_CODE);
			}

		});
		mForgetText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				replaceFragment(UserForgetFragment.class);
			}

		});
		this.findViewById(R.id.textview_user_login_remember_account).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(v.isSelected()){
					v.setSelected(false);
				}else{
					v.setSelected(true);
				}
			}

		});
		this.findViewById(R.id.textview_user_login_remember_password).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(v.isSelected()){
					v.setSelected(false);
				}else{
					v.setSelected(true);
				}
			}

		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}
	private boolean validator() {
		if (!Validator.validateNull(mUsernameText)) {
			showToast(R.string.user_validate_mobile_notnull);
			return false;
		}else if(Validator.validateMobile(mUsernameText)||Validator.validateEmail(mUsernameText)){
			
		}else{
			showToast(R.string.user_validate_mobile_format);
			return false;
		}
		
		if (!Validator.validateNull(mPasswordText)) {
			showToast(R.string.user_validate_password_notnull);
			return false;
		}else if(!Validator.validatePassword(mPasswordText)){
			showToast(R.string.user_validate_password_format);
			return false;
		}
		if (!Validator.validateNull(mVerifyCodeText)) {
			showToast(R.string.user_validate_verifycode_notnull);
			return false;
		}
		return true;
	}
	@Override
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		actionMenu.add(new ActionMenuItem(1, R.string.action_menu_register, -1, 1));
		return true;
	}

	@Override
	protected boolean onMenuActionSelected(ActionMenuItem action) {
		switch (action.getId()) {
			case 1 :
				replaceFragment(UserRegisterFragment.class,"RegisterFragment",null,Constants.LOGIN_REQUEST_CODE);
				break;
		}
		return super.onMenuActionSelected(action);
	}
	@Override
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		super.onFragmentResult(requestCode, resultCode, data);
		Log.d("onFragmentResult requestCode="+requestCode+",resultCode="+resultCode);
		if (requestCode == Constants.LOGIN_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				this.popBackStack();
			}
		}
	}
	
	protected void doLogin(String username,String password) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_LOGIN));
		apiTask.setParams(mApiContants.userLogin(username, password));
		apiTask.execute(new OnDataLoader<UserAuth>(){
			private LoadingDialog mLoadingDialog=null;
			@Override
			public void onStart() {
				if(getActivity()!=null)
				mLoadingDialog=LoadingDialog.show(getActivity(),R.string.common_processing);
			}

			@Override
			public void onSuccess(int totalPage,UserAuth userAuth) {
				if(getActivity()!=null)mLoadingDialog.dismiss();
				try {
					if (userAuth!=null){
						mAccountVerify.login(userAuth);
						popBackStack();
					}else{
						showToast(R.string.common_error);
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
	
	
	@Override
	public void onDestroyView() {
		this.getCustomActionBar().setBackgroundResource(R.color.actionbar_background);
		super.onDestroyView();
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	
}
