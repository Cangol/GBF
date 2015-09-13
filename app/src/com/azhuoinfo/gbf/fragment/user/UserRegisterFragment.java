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
import com.azhuoinfo.gbf.view.CountDownTextView;
import com.azhuoinfo.gbf.view.CountDownTextView.OnCountDownListener;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UserRegisterFragment extends BaseContentFragment{
	private View mLayout;
	private TextView mErrorText;
	private EditText mUsernameText;
	private EditText mPasswordText;
	private EditText mVerifyCodeText;
	private Button mRegisterBtn;
	private CountDownTextView mSendBtn;
	private CheckBox mDisplayRbtn;
	
	private View eLayout;
	private TextView eErrorText;
	private EditText eUsernameText;
	private EditText ePasswordText;
	private Button eRegisterBtn;
	private CheckBox eDisplayRbtn;
	
	private TextView mLoginHintText;
	private RadioGroup mRadioGroup;
	
	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApiContants=ApiContants.instance(getActivity());
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_user_register, container,false);
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
		//initViews(savedInstanceState);
		//initData(savedInstanceState);
	}
	@Override
	protected void findViews(View view) {
		this.getCustomActionBar().setBackgroundColor(Color.TRANSPARENT);
//		mLayout=this.findViewById(R.id.layout_user_register_mobile);
//		mErrorText=(TextView) this.findViewById(R.id.textview_user_register_mobile_error);
//		mUsernameText=(EditText) this.findViewById(R.id.edittext_user_register_mobile);
//		mPasswordText=(EditText) this.findViewById(R.id.edittext_user_register_mobile_password);
//		mRegisterBtn=(Button) this.findViewById(R.id.button_user_register_mobile_submit);
//		mSendBtn=(CountDownTextView) this.findViewById(R.id.button_user_register_mobile_sms);
//		mVerifyCodeText=(EditText) this.findViewById(R.id.edittext_user_register_mobile_code);
//		mDisplayRbtn=(CheckBox) this.findViewById(R.id.radio_user_register_mobile_display);
//		
//		eLayout=this.findViewById(R.id.layout_user_register_email);
//		eErrorText=(TextView) this.findViewById(R.id.textview_user_register_email_error);
//		eUsernameText=(EditText) this.findViewById(R.id.edittext_user_register_email);
//		ePasswordText=(EditText) this.findViewById(R.id.edittext_user_register_email_password);
//		eRegisterBtn=(Button) this.findViewById(R.id.button_user_register_email_submit);
//		eDisplayRbtn=(CheckBox) this.findViewById(R.id.radio_user_register_email_display);
		
		mLoginHintText=(TextView) this.findViewById(R.id.textview_user_register_login_hint);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_register);
		mLoginHintText.setText(Html.fromHtml(getString(R.string.user_login_hint)));
		mSendBtn.setOnCountDownListener(new OnCountDownListener(){

			@Override
			public void onFinish() {
				mSendBtn.setText(R.string.register_verifycode);
				mSendBtn.setEnabled(true);
			}
			
		});
		mSendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String mobile = mUsernameText.getText().toString();
				if(Validator.validateNull(mUsernameText)){
					if(Validator.validateMobile(mUsernameText)){
						Log.d("starTime");
						toSendVerifyCode(mobile);
						mSendBtn.starTimeByMillisInFuture(60*1000L);
						mSendBtn.setEnabled(false);
					}else{
						mErrorText.setText(R.string.user_validate_mobile_format);
					}
				}else{
					mErrorText.setText(R.string.user_validate_mobile_notnull);
				}
				
			}
		});
		mDisplayRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
		              //如果选中，显示密码      
					mPasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		          }else{
		              //否则隐藏密码
		          	mPasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		          }
			}
			
		});
		eDisplayRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
	              //如果选中，显示密码      
				ePasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
	          }else{
	              //否则隐藏密码
	          	ePasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
	          }
		}
		
	});
		mRegisterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(validatorMobile()){
					String mobile = mUsernameText.getText().toString();
					String password = mPasswordText.getText().toString();
					String verifyCode = mVerifyCodeText.getText().toString();
					doRegister(mobile,StringUtils.md5(password),verifyCode);
				}
				
			}

		});
		eRegisterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(validatorEmail()){
					String email = eUsernameText.getText().toString();
					String password = ePasswordText.getText().toString();
					doRegister(email,StringUtils.md5(password),null);
				}
			}

		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}
	private boolean validatorMobile() {
		if (!Validator.validateNull(mUsernameText)) {
			mErrorText.setText(R.string.user_validate_mobile_notnull);
			return false;
		}else if(!Validator.validateMobile(mUsernameText)){
			mErrorText.setText(R.string.user_validate_mobile_format);
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
	
	private boolean validatorEmail() {
		if (!Validator.validateNull(eUsernameText)) {
			eErrorText.setText(R.string.user_validate_email_notnull);
			return false;
		}else if(!Validator.validateEmail(eUsernameText)){
			eErrorText.setText(R.string.user_validate_email_format);
			return false;
		}
		if (!Validator.validateNull(ePasswordText)) {
			eErrorText.setText(R.string.user_validate_password_notnull);
			return false;
		}else if(!Validator.validatePassword(ePasswordText)){
			eErrorText.setText(R.string.user_validate_password_format);
			return false;
		}
		return true;
	}
	protected void toSendVerifyCode(String mobile) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_COMMON_SENDCODE));
		apiTask.setParams(mApiContants.sendCode("1",mobile));
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
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				if(getActivity()!=null)mLoadingDialog.dismiss();
				showToast(errorResponse);
			}
			
		});
	}
	protected void doRegister(String username,String password,String verifyCode) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_REGISTER));
		apiTask.setParams(mApiContants.userRegister(username, password, verifyCode));
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
					}else{
						showToast(R.string.common_error);
					}
					setResult(RESULT_OK);
					popBackStack();
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
	@Override
	public void onDestroyView() {
		this.getCustomActionBar().setBackgroundResource(R.color.actionbar_background);
		super.onDestroyView();
	}
	
}
