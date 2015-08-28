package com.azhuoinfo.gbf.fragment.user;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.BitmapUtils;
import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.api.task.ApiTask;
import com.azhuoinfo.gbf.api.task.OnDataLoader;
import com.azhuoinfo.gbf.db.MessageService;
import com.azhuoinfo.gbf.model.User;
import com.azhuoinfo.gbf.utils.DateTimePicker;
import com.azhuoinfo.gbf.utils.DateTimePicker.OnDateSetListener;
import com.azhuoinfo.gbf.utils.GalleryUtils;
import com.azhuoinfo.gbf.view.CommonDialog;
import com.azhuoinfo.gbf.view.CommonDialog.OnButtonClickListener;
import com.azhuoinfo.gbf.view.CommonDialog.OnDialogItemClickListener;
import com.azhuoinfo.gbf.view.CountDownTextView;
import com.azhuoinfo.gbf.view.CountDownTextView.OnCountDownListener;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.azhuoinfo.gbf.view.Validator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoFragment extends BaseContentFragment{
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private static final String TEMP_IMAGE_CAMERA = "temp_camera.jpg";
	private static final String TEMP_IMAGE_CROP = "temp_crop.jpg";
	private static final int CROP_AVATAR_HEIGHT = 240;
	private static final int CROP_AVATAR_WIDTH = 240;
	private static final int IMAGE_FROM_CAMERA = 0x0a1;
	private static final int IMAGE_FROM_PHOTOS = 0xfe2;
	private static final int IMAGE_CROP_RESULT = 0xaf3;// 结果
	
	private ImageView mAvatarImage;
	private TextView mUserIdText;
	private TextView mNicknameText;
	private TextView mMobileText;
	private TextView mEmailText;
	private TextView mGenderText;
	private TextView mBirthdayText;
	private TextView mLocationText;
	
	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private MessageService mMessageService;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
		mApiContants=ApiContants.instance(getActivity());
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.ic_face_man)
        .showImageOnFail(R.drawable.ic_face_man)
        .build();
		mMessageService=new MessageService(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_user_info, container,false);
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
		mAvatarImage=(ImageView) this.findViewById(R.id.imageview_user_info_face);
		mUserIdText=(TextView) this.findViewById(R.id.textview_user_info_userid);
		mNicknameText=(TextView) this.findViewById(R.id.textview_user_info_nickname);
		mMobileText=(TextView) this.findViewById(R.id.textview_user_info_mobile);
		mEmailText=(TextView) this.findViewById(R.id.textview_user_info_email);
		mGenderText=(TextView) this.findViewById(R.id.textview_user_info_gender);
		mBirthdayText=(TextView) this.findViewById(R.id.textview_user_info_birthday);
		mLocationText=(TextView) this.findViewById(R.id.textview_user_info_address);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_user);
		updateViews(mAccountVerify.getUser());
		mAvatarImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSelectFromPicDialog();
			}

		});
		this.findViewById(R.id.layout_user_info_changenickname).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showChangeUserNickname(mAccountVerify.getUser().getNickname());
			}

		});
		this.findViewById(R.id.layout_user_info_changemobile).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showChangeUserMobile();
			}

		});
		this.findViewById(R.id.layout_user_info_changeemail).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showChangeUserEmail(mAccountVerify.getUser().getEmail());
			}

		});
		this.findViewById(R.id.layout_user_info_changesex).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSelectSexDialog();
			}

		});
		this.findViewById(R.id.layout_user_info_changebirthday).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePicker.setDate(getActivity(), mAccountVerify.getUser().getBirthday(),new OnDateSetListener(){
					@Override
					public void OnDateSet(String date) {
						changeUserBirthday(date);
					}
				});
			}

		});
		this.findViewById(R.id.button_user_info_logout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogoutDialog();
			}

		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
	
	protected void updateViews(User user) {
		mAccountVerify.setUser(user);
		mUserIdText.setText(""+user.getUserId());
		mNicknameText.setText(user.getNickname());
		mBirthdayText.setText(user.getBirthday());
		mLocationText.setText(user.getLocation());
		if(TextUtils.isEmpty(user.getMobile())){
			mMobileText.setText(R.string.user_bind);
		}else{
			mMobileText.setText(user.getMobile());
			this.findViewById(R.id.layout_user_info_changemobile).setClickable(false);
		}
		
		if(TextUtils.isEmpty(user.getEmail())){
			mEmailText.setText(R.string.user_bind);
		}else{
			mEmailText.setText(user.getEmail());
			this.findViewById(R.id.layout_user_info_changeemail).setClickable(false);
			this.findViewById(R.id.layout_user_info_changeemail).setEnabled(false);
		}
		
		if(ApiContants.API_PARAMS_SEX_MAN.equals(user.getGender())){
			mGenderText.setText(R.string.user_gender_man);
		}else if(ApiContants.API_PARAMS_SEX_WOMAN.equals(user.getGender())){
			mGenderText.setText(R.string.user_gender_woman);
		}else{
			mGenderText.setText(R.string.user_gender_unknow);
		}
		
		mImageLoader.displayImage(user.getAvatar(), mAvatarImage, mDisplayImageOptions);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("requestCode="+requestCode+",resultCode="+resultCode);
		if (requestCode == IMAGE_FROM_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				GalleryUtils.startSystemPhotoCrop(this,
						IMAGE_CROP_RESULT,
						Uri.fromFile(GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CAMERA)),
						CROP_AVATAR_HEIGHT,
						CROP_AVATAR_WIDTH,
						GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CROP));
			}
		} else if (requestCode == IMAGE_FROM_PHOTOS) {
			if (resultCode == Activity.RESULT_OK) {
				GalleryUtils.startSystemPhotoCrop(this,
						IMAGE_CROP_RESULT,
						data.getData(),
						CROP_AVATAR_HEIGHT,
						CROP_AVATAR_WIDTH,
						GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CROP));
			}
		} else if (requestCode == IMAGE_CROP_RESULT) {
			if (resultCode == Activity.RESULT_OK){
				Log.d("changeHeadImg image="+GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CROP).getAbsolutePath());
				changeHeadImg(GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CROP).getAbsolutePath());
			}
				
		}
	}

	private void getFromCamera() {
		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CAMERA)));
			startActivityForResult(intent, IMAGE_FROM_CAMERA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getFromPhotos() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);
		startActivityForResult(intent, IMAGE_FROM_PHOTOS);
	}
	
	private void showSelectFromPicDialog() {
		String[] from=this.getResources().getStringArray(R.array.user_avatar_from);
		final CommonDialog dialog=CommonDialog.creatDialog(this.getActivity());
		dialog.setTitle(R.string.user_change_avatar);
		dialog.setListViewInfo(new ArrayAdapter<String>(app,
				R.layout.common_dialog_textview, from),
				new OnDialogItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
							case 0:
								getFromPhotos();// 从相相册获取
								break;
							case 1:
								String status = Environment.getExternalStorageState();
								if (status.equals(Environment.MEDIA_MOUNTED)) {
									getFromCamera();// 从相机获取
								} else {
									// 没有SD卡;
									showToast(R.string.common_storage_null);
								}
								break;
						}
						dialog.dismiss();
					}
				});
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	private void showLogoutDialog(){
		final CommonDialog dialog=CommonDialog.creatDialog(this.getActivity());
		dialog.setTitle(R.string.dialog_logout_title)
		.setMessage(R.string.dialog_logout_content)
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener(){

			@Override
			public void onClick(View view) {
				mMessageService.deleteAll(mAccountVerify.getUserId());
				mAccountVerify.logout();
				showToast(R.string.user_logout_success);
				popBackStack();
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
	private void showSelectSexDialog() {
		final String[] from=this.getResources().getStringArray(R.array.user_gender);
		final CommonDialog dialog=CommonDialog.creatDialog(this.getActivity());
		dialog.setTitle(R.string.user_change_sex);
		dialog.setListViewInfo(new ArrayAdapter<String>(app,
				R.layout.common_dialog_textview, from),
				new OnDialogItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						dialog.dismiss();
						changeUserSex(""+(position+1));
					}
				});
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	private void showChangeUserNickname(String nickname){
		final CommonDialog dialog = CommonDialog.creatDialog(getActivity());
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle(R.string.user_change_nickname);
		dialog.setContentView(R.layout.common_dialog_edit);
		final EditText editText = (EditText) dialog.findViewById(R.id.common_edit);
		dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		})
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				if(!Validator.validateNull(editText)){
					editText.setError(getString(R.string.user_validate_nickname_notnull));
					editText.requestFocus();
					return;
				}
				if(!Validator.validateNickname(editText)){
					editText.setError(getString(R.string.user_validate_nickname_format));
					editText.requestFocus();
					return;
				}
				String nickname=editText.getText().toString().trim();
				changeUserNickname(nickname);
				dialog.dismiss();
			}
		});
		if(!TextUtils.isEmpty(nickname)){
			editText.setText(nickname);
		}else{
			editText.setText(null);
			editText.setHint(R.string.register_nickname_hint);	
		}
		dialog.show();
	}
	private void showChangeUserLocation(String location){
		final CommonDialog dialog = CommonDialog.creatDialog(getActivity());
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle(R.string.user_change_location);
		dialog.setContentView(R.layout.common_dialog_edit);
		final EditText editText = (EditText) dialog.findViewById(R.id.common_edit);
		dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		})
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				if(!Validator.validateNull(editText)){
					editText.setError(getString(R.string.user_validate_location_notnull));
					editText.requestFocus();
					return;
				}
				String location=editText.getText().toString().trim();
				changeUserLocation(location);
				dialog.dismiss();
			}
		});
		if(!TextUtils.isEmpty(location)){
			editText.setText(location);
		}else{
			editText.setText(null);
			editText.setHint(R.string.register_location_hint);	
		}
		dialog.show();
	}
	private void showChangeUserEmail(String email){
		final CommonDialog dialog = CommonDialog.creatDialog(getActivity());
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle(R.string.user_change_email);
		dialog.setContentView(R.layout.common_dialog_edit);
		final EditText editText = (EditText) dialog.findViewById(R.id.common_edit);
		dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		})
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				if(!Validator.validateNull(editText)){
					editText.setError(getString(R.string.user_validate_email_notnull));
					editText.requestFocus();
					return;
				}
				if(!Validator.validateEmail(editText)){
					editText.setError(getString(R.string.user_validate_email_format));
					editText.requestFocus();
					return;
				}
				String email=editText.getText().toString().trim();
				changeUserEmail(email);
				dialog.dismiss();
			}
		});
		if(!TextUtils.isEmpty(email)){
			editText.setText(email);
		}else{
			editText.setText(null);
			editText.setHint(R.string.register_email_hint);	
		}
		dialog.show();
	}
	

	private void showChangeUserMobile(){
		final CommonDialog dialog = CommonDialog.creatDialog(getActivity());
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle(R.string.user_change_mobile);
		dialog.setContentView(R.layout.layout_edit_mobile);
		final EditText mobileEdit = (EditText) dialog.findViewById(R.id.edittext_edit_mobile);
		final EditText codeEdit = (EditText) dialog.findViewById(R.id.edittext_edit_mobile_code);
		final CountDownTextView countDown = (CountDownTextView) dialog.findViewById(R.id.button_edit_mobile_sms);
		countDown.setOnCountDownListener(new OnCountDownListener(){

			@Override
			public void onFinish() {
				countDown.setText(R.string.register_verifycode);
				countDown.setEnabled(true);
			}
			
		});
		dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		})
		.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				if(!Validator.validateNull(mobileEdit)){
					mobileEdit.setError(getString(R.string.user_validate_mobile_notnull));
					mobileEdit.requestFocus();
					return;
				}
				if(!Validator.validateMobile(mobileEdit)){
					mobileEdit.setError(getString(R.string.user_validate_mobile_format));
					mobileEdit.requestFocus();
					return;
				}
				String mobile=mobileEdit.getText().toString().trim();
				String code=codeEdit.getText().toString().trim();
				changeUserMobile(mobile,code);
				dialog.dismiss();
			}
		});
		countDown.setOnCountDownListener(new OnCountDownListener(){

			@Override
			public void onFinish() {
				countDown.setText(R.string.register_verifycode);
				countDown.setEnabled(true);
			}
			
		});
		countDown.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!Validator.validateNull(mobileEdit)){
					mobileEdit.setError(getString(R.string.user_validate_mobile_notnull));
					mobileEdit.requestFocus();
					return;
				}
				if(!Validator.validateMobile(mobileEdit)){
					mobileEdit.setError(getString(R.string.user_validate_mobile_format));
					mobileEdit.requestFocus();
					return;
				}
				countDown.starTimeByMillisInFuture(60*1000L);
				countDown.setEnabled(false);
				String mobile=mobileEdit.getText().toString().trim();
				sendCode(mobile);
			}
			
		});
		mobileEdit.setText(null);
		mobileEdit.setHint(R.string.register_mobile_hint);	
		dialog.show();
	}
	
	private void changeHeadImg(String imagePath) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setMethod("POST");
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYAVATAR));
		apiTask.setParams(mApiContants.modifyAvatar(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), BitmapUtils.imageFileToString(imagePath)));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						if (!isEnable())return;
						ld.dismiss();
						mImageLoader.displayImage(t.getAvatar(), mAvatarImage, mDisplayImageOptions);
						showToast(R.string.user_change_success);
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
	
	private void changeUserSex(String sex) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYGENDER));
		apiTask.setParams(mApiContants.modifyGender(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), sex));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	
	protected void changeUserNickname(String nickname) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYNICKNAME));
		apiTask.setParams(mApiContants.modifyNickname(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), nickname));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						Log.d(TAG, "onSuccess t="+t);
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	protected void changeUserBirthday(String birthday) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYBIRTHDAY));
		apiTask.setParams(mApiContants.modifyBirthday(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), birthday));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						Log.d(TAG, "onSuccess t="+t);
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	protected void changeUserLocation(String location) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYLOCATION));
		apiTask.setParams(mApiContants.modifyLocation(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), location));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						Log.d(TAG, "onSuccess t="+t);
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	protected void changeUserEmail(String email) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYEMAIL));
		apiTask.setParams(mApiContants.modifyEmail(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), email));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	
	protected void changeUserMobile(String mobile,String code) {
		final LoadingDialog ld = LoadingDialog.create(getActivity());
		ld.setMessage(R.string.common_processing);
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_MODIFYMOBILE));
		apiTask.setParams(mApiContants.modifyMobile(mAccountVerify.getUserId(),mAccountVerify.getUserToken(), mobile,code));
		apiTask.execute(new OnDataLoader<User>() {

					@Override
					public void onStart() {
						if (!isEnable())return;
						ld.show();
					}

					@Override
					public void onSuccess(int totalPage,User t) {
						if (!isEnable())return;
						ld.dismiss();
						updateViews(t);
						showToast(R.string.user_change_success);
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
	protected void sendCode(String target) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_COMMON_SENDCODE));
		apiTask.setParams(mApiContants.sendCode("1",target));
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
			}
			
		});
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	@Override
	public boolean isCleanStack() {
		return false;
	}
}
