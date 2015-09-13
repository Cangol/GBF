package com.azhuoinfo.gbf.fragment.publish;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.azhuoinfo.gbf.AccountVerify;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.activity.GalleryActivity;
import com.azhuoinfo.gbf.api.ApiContants;
import com.azhuoinfo.gbf.fragment.adapter.PublishImageAdapter;
import com.azhuoinfo.gbf.model.PublishImage;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.utils.GalleryUtils;
import com.azhuoinfo.gbf.view.CommonDialog;
import com.azhuoinfo.gbf.view.CommonDialog.OnDialogItemClickListener;
import com.azhuoinfo.gbf.view.LoadingDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PublishImagesFragment extends BaseContentFragment{
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String TEMP_IMAGE_CAMERA = "temp_camera.jpg";
	private static final int IMAGE_FROM_CAMERA = 0x1;
	private static final int IMAGE_FROM_PHOTOS = 0x2;
	private static final int IMAGE_FROM_GALLERY=0x3;
	private GridView mGridView;
	private PublishImageAdapter mDataAdapter;
	private LoadingDialog mLoadingDialog;
	private AccountVerify mAccountVerify;
	private ApiContants mApiContants;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	
	private List<String> mImages;
	private String mTag;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);mAccountVerify = AccountVerify.getInstance(getActivity());
		mApiContants=ApiContants.instance(getActivity());
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
		mImages=new ArrayList<String>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_publish_images,container,false);
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
		mGridView=(GridView) this.findViewById(R.id.gridView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_publish);
		mDataAdapter=new PublishImageAdapter(getActivity());
		mGridView.setAdapter(mDataAdapter);
		this.findViewById(R.id.button_publish_images_next).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				replaceFragment(PublishDetailsFragment.class,"PublishDetailsFragment",bundle);
			}
			
		});
		this.findViewById(R.id.button_publish_images_sevices).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				//replaceFragment(PublishDetailsFragment.class,"PublishDetailsFragment",bundle);
			}
			
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position==parent.getCount()-1){
					showSelectFromPicDialog(6);
				}else{
					PublishImage item=(PublishImage) parent.getItemAtPosition(position);
					mTag=item.getName();
				}
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		mDataAdapter.add(new PublishImage("正面",null));
		mDataAdapter.add(new PublishImage("侧面",null));
		mDataAdapter.add(new PublishImage("反面",null));
		mDataAdapter.add(new PublishImage("内面",null));
		mDataAdapter.add(new PublishImage("外面",null));
		mDataAdapter.add(new PublishImage("新增",null));
	}
	private void showSelectFromPicDialog(final int max) {
		String[] from=this.getResources().getStringArray(R.array.user_avatar_from);
		final CommonDialog dialog=CommonDialog.creatDialog(this.getActivity());
		dialog.setListViewInfo(new ArrayAdapter<String>(app,
				R.layout.common_dialog_textview, from),
				new OnDialogItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
							case 0:
								getFromPhotos(max);// 从相相册获取
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
	private void getFromCamera() {
		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CAMERA)));
			startActivityForResult(intent, IMAGE_FROM_CAMERA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getFromPhotos(int max) {
		Intent intent = new Intent(this.getActivity(),GalleryActivity.class);
		intent.setAction(Constants.ACTION_MULTIPLE_PICK);
		intent.putExtra("max_select", max);
		startActivityForResult(intent, IMAGE_FROM_PHOTOS);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == IMAGE_FROM_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				String image=Uri.fromFile(GalleryUtils.getTempFile(this.getActivity(),TEMP_IMAGE_CAMERA)).toString();
				Log.d("IMAGE_FROM_CAMERA "+image);
				updateGridViews(image);
			}
		} else if (requestCode == IMAGE_FROM_PHOTOS) {
			if (resultCode == Activity.RESULT_OK) {
				List<String> strs=data.getStringArrayListExtra(Constants.DATA_GALLERY_MULTIPLE_PICK);
				Log.d("IMAGE_FROM_PHOTOS "+strs.get(0));
				updateGridViews(strs);
			}
		} 
	}
	private void updateGridViews(List<String> strs) {
		for (int i = 0; i < strs.size(); i++) {
			mDataAdapter.add(mDataAdapter.getCount()-1,new PublishImage(null,strs.get(i)));
		}		
	}

	private void updateGridViews(String image) {
		mDataAdapter.add(mDataAdapter.getCount()-1,new PublishImage(mTag,image));
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
