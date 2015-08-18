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
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserChangePwdFragment extends BaseContentFragment{
	private TextView mErrorText;
	private EditText mOldPwdText;
	private EditText mNewPwdText;
	private EditText mRePwdText;
	private Button mSubmitBtn;
	
	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
		mApiContants=ApiContants.instance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_user_changepwd, container,false);
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
		mErrorText=(TextView) this.findViewById(R.id.textview_user_changepwd_error);
		mOldPwdText=(EditText) this.findViewById(R.id.edittext_user_changepwd_password);
		mNewPwdText=(EditText) this.findViewById(R.id.edittext_user_changepwd_newpassword);
		mRePwdText=(EditText) this.findViewById(R.id.edittext_user_changepwd_repassword);
		mSubmitBtn=(Button) this.findViewById(R.id.button_user_changepwd_submit);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_changepassword);
		mSubmitBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(validator()){
					String pwd=mOldPwdText.getText().toString().trim();
					String newpwd=mNewPwdText.getText().toString().trim();
					changePwd(StringUtils.md5(pwd),StringUtils.md5(newpwd));
				}
				
			}
			
		});
	}

	private boolean validator() {
		if (!Validator.validateNull(mOldPwdText)) {
			mErrorText.setText(R.string.user_validate_oldpassword_notnull);
			return false;
		}else if (!Validator.validateNull(mNewPwdText)) {
			mErrorText.setText(R.string.user_validate_newpassword_notnull);
			return false;
		}else if (!Validator.validateNull(mRePwdText)) {
			mErrorText.setText(R.string.user_validate_renewpassword_notnull);
			return false;
		}else if (!Validator.validatePassword(mNewPwdText)) {
			mErrorText.setText(R.string.user_validate_newpassword_notnull);
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
	protected void changePwd(String pwd, String newpwd) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_USER_MODIFYPASSWORD));
		apiTask.setParams(ApiContants.instance(getActivity()).modifyPassword(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), pwd,newpwd));
		apiTask.execute(
				new OnDataLoader<Object>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,Object obj) {
						if (!isEnable())return;
						ld.dismiss();
						showToast(R.string.user_change_success);
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
}
