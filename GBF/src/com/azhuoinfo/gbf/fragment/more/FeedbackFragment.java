package com.azhuoinfo.gbf.fragment.more;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedbackFragment extends BaseContentFragment {
	private Button mSubmitButton;
	private EditText mContentEditText;
	private EditText mContactEditText;
	private TextView mContentNumTextView;
	private LoadingDialog mLoadingDialog;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_feedback, container,false);
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
		mSubmitButton=(Button) this.findViewById(R.id.button_feedback_submit);
		mContentEditText=(EditText) this.findViewById(R.id.edittext_feedback_content);
		mContactEditText=(EditText) this.findViewById(R.id.edittext_feedback_contact);
		mContentNumTextView=(TextView) this.findViewById(R.id.edittext_feedback_content_num);
	}
	
	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_feedback);
		mSubmitButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!validate())return;
				String content=mContentEditText.getText().toString();
				String contact=TextUtils.isEmpty(mContactEditText.getText())?"":mContactEditText.getText().toString();
				sendFeedback(content,contact);
			}
			
		});
		mContentEditText.addTextChangedListener(new android.text.TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				mContentNumTextView.setText(String.format(getString(R.string.feedback_content_num), s.length()));
			}  
			
		});
		mLoadingDialog=LoadingDialog.create(getActivity());
		mLoadingDialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				Log.d("LoadingDialog onCancel");
			}
			
		});
		//默认值0
		mContentNumTextView.setText(String.format(getString(R.string.feedback_content_num), 0));
	}
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
	protected boolean validate() {
		
		if(!Validator.validateNull(mContentEditText)){
			showToast(R.string.feedback_validate_content_null);
			return false;
		}
		
		if(!Validator.validateContent(mContentEditText)){
			showToast(R.string.feedback_validate_content_short);
			return false;
		}
		
		return true;
	}
	
	private void sendFeedback(String content,String contact){
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_COMMON_FEEDBACK));
		apiTask.setParams(ApiContants.instance(getActivity()).feedback(content,contact));
		apiTask.execute(new OnDataLoader<Object>(){

			@Override
			public void onStart() {
				if(getActivity()!=null);
				mLoadingDialog.show(R.string.common_submiting);
			}

			@Override
			public void onSuccess(int totalPage,Object t) {
				if(getActivity()!=null){
					mLoadingDialog.dismiss();
					showToast(R.string.feedback_success);
					//提交成功后 退出当前页
					popBackStack();
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
		super.onDestroyView();
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
