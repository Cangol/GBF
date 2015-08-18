package com.azhuoinfo.gbf.fragment.user;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.StringUtils;
import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.model.UserAuth;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserLoginFragment extends BaseContentFragment{
	public static final int LOGIN_REQUEST_CODE=1;
	private TextView mErrorText;
	private EditText mUsernameText;
	private EditText mPasswordText;
	private TextView mForgetText;
	private Button mLoginBtn;
	private Button mRegisiterBtn;
	private ImageButton mQQLoginBtn;
	private ImageButton mWeiboLoginBtn;
	private ImageButton mWeixinLoginBtn;
	private TextView dRegisterBtn;

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
		mErrorText=(TextView) this.findViewById(R.id.textview_user_login_error);
		mUsernameText=(EditText) this.findViewById(R.id.edittext_user_login_username);
		mPasswordText=(EditText) this.findViewById(R.id.edittext_user_login_password);
		
		mForgetText=(TextView) this.findViewById(R.id.textview_user_login_forget);
		
		mLoginBtn=(Button) this.findViewById(R.id.button_user_login_login);
		mRegisiterBtn=(Button) this.findViewById(R.id.button_user_login_regisiter);
		
		mQQLoginBtn=(ImageButton) this.findViewById(R.id.button_user_login_qq);
		mWeiboLoginBtn=(ImageButton) this.findViewById(R.id.button_user_login_weibo);
		mWeixinLoginBtn=(ImageButton) this.findViewById(R.id.button_user_login_weixin);
		
		dRegisterBtn=(TextView) this.findViewById(R.id.textview_user_login_device);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_login);
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
		mRegisiterBtn.setOnClickListener(new OnClickListener() {

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
		dRegisterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doRegister(null);
			}

		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}
	private boolean validator() {
		if (!Validator.validateNull(mUsernameText)) {
			mErrorText.setText(R.string.user_validate_username_notnull);
			return false;
		}else if(Validator.validateMobile(mUsernameText)||Validator.validateEmail(mUsernameText)){
			
		}else{
			mErrorText.setText(R.string.user_validate_username_format);
			return false;
		}
		
		if (!Validator.validateNull(mPasswordText)) {
			mErrorText.setText(R.string.user_validate_password_notnull);
			return false;
		}else if(!Validator.validatePassword(mPasswordText)){
			mErrorText.setText(R.string.user_validate_password_format);
			return false;
		}
		return true;
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
	protected void doRegister(String[] args) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		if(args!=null){
			apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_THIRDREGISTER));
			apiTask.setParams(mApiContants.thirdRegister(args[0],args[1],args[2],args[3]));
		}else{
			apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_AUTOREGISTER));
			apiTask.setParams(mApiContants.autoRegister());
		}
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
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	
}
