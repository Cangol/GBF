package com.azhuoinfo.gbf.fragment.user;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.StringUtils;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.view.CountDownTextView;
import com.azhuoinfo.gbf.view.CountDownTextView.OnCountDownListener;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserForgetFragment extends BaseContentFragment{
	private TextView mErrorText;
	private EditText mUsernameText;
	private EditText mVerifyCodeText;
	private EditText mNewPwdText;
	private EditText mRePwdText;
	private Button mSubmitBtn;
	private CountDownTextView mSendBtn;
	
	private ApiContants mApiContants;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApiContants=ApiContants.instance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_user_forget, container,false);
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
//		mErrorText=(TextView) this.findViewById(R.id.textview_user_forget_error);
//		mUsernameText=(EditText) this.findViewById(R.id.edittext_user_forget_username);
//		mVerifyCodeText=(EditText) this.findViewById(R.id.edittext_user_forget_verifycode);
//		mNewPwdText=(EditText) this.findViewById(R.id.edittext_user_forget_newpassword);
//		mRePwdText=(EditText) this.findViewById(R.id.edittext_user_forget_renewpassword);
//		mSubmitBtn=(Button) this.findViewById(R.id.button_user_forget_submit);
//		mSendBtn=(CountDownTextView) this.findViewById(R.id.button_user_forget_send);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_findpassword);
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
				String username = mUsernameText.getText().toString();
				if(Validator.validateNull(mUsernameText)&&Validator.validateUsername(mUsernameText)){
					if(Validator.validateEmail(mUsernameText)||Validator.validateMobile(mUsernameText)){
						toSendCode("1",username);
					}else{
						mErrorText.setText(R.string.user_validate_username_format);
					}
					mSendBtn.starTimeByMillisInFuture(60*1000L);
					mSendBtn.setEnabled(false);
				}else{
					mErrorText.setText(R.string.user_validate_username_notnull);
				}
				
			}
		});
		mSubmitBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(validator()){
					String username=mUsernameText.getText().toString().trim();
					String verifyCode=mVerifyCodeText.getText().toString().trim();
					String newpwd=mNewPwdText.getText().toString().trim();
					forgetPassword(username,verifyCode,StringUtils.md5(newpwd));
				}
				
			}
			
		});
	}

	private boolean validator() {
		if (!Validator.validateNull(mUsernameText)) {
			mErrorText.setText(R.string.user_validate_username_notnull);
			return false;
		}else if(!Validator.validateMobile(mUsernameText)&&!Validator.validateEmail(mUsernameText)){
			mErrorText.setText(R.string.user_validate_username_format);
			return false;
		}else if (!Validator.validateNull(mVerifyCodeText)) {
			mErrorText.setText(R.string.user_validate_verifycode_notnull);
			return false;
		}else if (!Validator.validateNull(mNewPwdText)) {
			mErrorText.setText(R.string.user_validate_newpassword_notnull);
			return false;
		}else if (!Validator.validatePassword(mNewPwdText)) {
			mErrorText.setText(R.string.user_validate_newpassword_format);
			return false;
		}else if (!Validator.validateNull(mRePwdText)) {
			mErrorText.setText(R.string.user_validate_renewpassword_notnull);
			return false;
		}else if (!Validator.validateSamePassword(mNewPwdText,mRePwdText)){
			mErrorText.setText(R.string.user_validate_renewpassword_notsame);
			return false;
		}
		return true;
	}
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	protected void toSendCode(String type,String target) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_COMMON_SENDCODE));
		apiTask.setParams(mApiContants.sendCode(type,target));
		apiTask.execute(new OnDataLoader<Object>(){
			//private LoadingDialog mLoadingDialog=null;
			@Override
			public void onStart() {
				//if(getActivity()!=null)
				//mLoadingDialog=LoadingDialog.show(getActivity(),R.string.common_processing);
			}

			@Override
			public void onSuccess(int totalPage,Object object) {
				//if(getActivity()!=null)mLoadingDialog.dismiss();
			}

			@Override
			public void onFailure(String errorCode, String errorResponse) {
				Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
				//if(getActivity()!=null)mLoadingDialog.dismiss();
				showToast(errorResponse);
//				mSendBtn.stopTime();
//				mSendBtn.setText(R.string.register_verifycode);
//				mSendBtn.setEnabled(true);
			}
			
		});
	}
	
	protected void forgetPassword(String email,String verifyCode ,String newpwd) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_FORGETPASSWORD));
		apiTask.setParams(ApiContants.instance(getActivity()).forgetPassword(email,verifyCode,newpwd));
		apiTask.execute(new OnDataLoader<Object>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,Object t) {
						if (!isEnable())return;
						showToast(R.string.user_change_success);
						ld.dismiss();
						popBackStack();
					}

					@Override
					public void onFailure(String errorCode, String errorResponse) {
						Log.d(TAG, errorResponse);
						if (!isEnable())return;
						showToast(errorResponse);
						ld.cancel();
					}
				});
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
