package com.azhuoinfo.gbf.activity;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseActionBarActivity;
import mobi.cangol.mobile.logging.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.GridView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.api.task.DbTask;
import com.azhuoinfo.gbf.fragment.adapter.GalleryAdapter;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.utils.GalleryUtils;
import com.azhuoinfo.gbf.view.PromptView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

@SuppressLint("ResourceAsColor")
public class GalleryActivity extends BaseActionBarActivity {
	public static final int REQUEST_SELECT_IMAGE = 1;
	private PromptView mPromptView;
	private GridView mGridView;
	private GalleryAdapter mDataAdapter;
	private boolean mIsMultiplePick=false;
	private ArrayList<String> mPhotos=new ArrayList<String>();
	private int mMaxSelected=1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		this.setActionbarOverlay(false);
		this.getCustomActionBar().displayUpIndicator();
		this.getCustomActionBar().setBackgroundResource(R.color.statusbar_bg);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		String action=this.getIntent().getAction();
		mMaxSelected=this.getIntent().getIntExtra("max_select",1);
		ArrayList<String> list=this.getIntent().getStringArrayListExtra("select");
		if(list!=null)
			mPhotos.addAll(list);
		if(Constants.ACTION_PICK.equalsIgnoreCase(action))
			mIsMultiplePick=false;
		else
			mIsMultiplePick=true;
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	@Override
	public void findViews() {
		mPromptView=(PromptView) findViewById(R.id.promptView);
		mGridView=(GridView) findViewById(R.id.gridView);
	}
	@Override
	public void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_gallery);
		mDataAdapter = new GalleryAdapter(this);
		mDataAdapter.setMultiplePick(mIsMultiplePick);
		mDataAdapter.setSelectedMode(true);
		mGridView.setAdapter(mDataAdapter);
		DisplayMetrics displayMetrics =getResources().getDisplayMetrics(); 
		mDataAdapter.setItemSize((int)((displayMetrics.widthPixels/3)-displayMetrics.density*1),(int)((displayMetrics.widthPixels/3)-displayMetrics.density*1));
		mDataAdapter.setOnActionClickListener(new GalleryAdapter.OnActionClickListener(){

			@Override
			public void onClickImage(int position) {
				showImageGallery(position,(ArrayList<String>) mDataAdapter.getItems(),(ArrayList<String>) mDataAdapter.getSelected());
			}

			@Override
			public void onClickSelect(int position) {
				Log.d("onClickSelect");
				String item=(String) mDataAdapter.getItem(position);
				if(mIsMultiplePick){
					if(mDataAdapter.getSelected().size()<mMaxSelected||mDataAdapter.getItemSelected(position)){
						mDataAdapter.invertSelected(position);
						setTitle(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),mMaxSelected));
					}else{
						showToast(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),mMaxSelected));
					}
				}else{
					setActivityResult(item);
				}
			}
			
		});
		mGridView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),true, true));
	}
	@Override
	public void initData(Bundle savedInstanceState) {
		getGalleryList();
	}
	private void showImageGallery(int position,ArrayList<String> images,ArrayList<String> select){
		Intent intent=new Intent(this,GalleryImageActivity.class);
		intent.putExtra("position", position);
		intent.putExtra("max_select", mMaxSelected);
		intent.putStringArrayListExtra("images",images);
		intent.putStringArrayListExtra("select",select);
		intent.putExtra("isSelected",true);
		this.startActivityForResult(intent, REQUEST_SELECT_IMAGE);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SELECT_IMAGE) {
			if (resultCode == Activity.RESULT_OK) {
				List<String> strs=data.getStringArrayListExtra(Constants.DATA_GALLERY_MULTIPLE_PICK);
				mPhotos.addAll(strs);
				mDataAdapter.addAll(strs);
			}
		}
	}
	@Override
	public void onMenuActionCreated(ActionMenu actionMenu) {
		actionMenu.add(new ActionMenuItem(1,R.string.action_menu_done,-1,1));
	}
	@Override
	public boolean onMenuActionSelected(ActionMenuItem action) {
		Log.d("onMenuActionSelected ");
		switch(action.getId()){
			case 1:
				setActivityResult((ArrayList<String>) mDataAdapter.getSelected());
				break;
		}
		return super.onMenuActionSelected(action);
	}
	protected void setActivityResult(String str){
		Intent data = new Intent().putExtra(Constants.DATA_GALLERY_PICK, str);
		this.setResult(RESULT_OK, data);
		this.finish();
	}
	protected void setActivityResult(ArrayList<String> strs){
		Intent data = new Intent().putStringArrayListExtra(Constants.DATA_GALLERY_MULTIPLE_PICK, strs);
		this.setResult(RESULT_OK, data);
		this.finish();
	}
	protected void updateViews(List<String> list) {
		if(list!=null&&list.size()>0){
			mDataAdapter.addAll(list);
			if(mDataAdapter.getCount()>0){
				mPromptView.showContent();
			}else{
				mPromptView.showPrompt(R.string.common_empty);
			}
		}else{
			mPromptView.showPrompt(R.string.common_empty);
		}
	}
	private void getGalleryList(){
		DbTask<Void,List<String>> dbTask=new DbTask<Void,List<String>>(TAG){
			
			@Override
			public void onPreExecute() {
				super.onPreExecute();
				mPromptView.showLoading();
			}

			@Override
			public List<String> doInBackground(Void... mParams) {
				return GalleryUtils.getGalleryPhotos(GalleryActivity.this);
			}

			@Override
			public void onPostExecute(List<String> result) {
				super.onPostExecute(result);
				updateViews(result);
			}
			
		};
		dbTask.execute();
	}
}
