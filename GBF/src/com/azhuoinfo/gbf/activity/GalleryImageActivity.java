package com.azhuoinfo.gbf.activity;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseActionBarActivity;
import mobi.cangol.mobile.logging.Log;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.fragment.adapter.ImagePagerAdapter;
import com.azhuoinfo.gbf.model.ImageSelect;
import com.azhuoinfo.gbf.utils.Constants;
import com.azhuoinfo.gbf.view.touchgallery.BasePagerAdapter.OnItemChangeListener;
import com.azhuoinfo.gbf.view.touchgallery.GalleryViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

@SuppressLint("ResourceAsColor")
public class GalleryImageActivity extends BaseActionBarActivity {
	private GalleryViewPager mGalleryViewPager;
	private View mToolsBar;
	private ImageView mSelectView;
	private ImagePagerAdapter mDataAdapter;
	private ArrayList<ImageSelect> mImageList;
	private int position=0;
	private ImageSelect mImage;
	private boolean isLocalImage;
	private boolean isSelected;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_gallery_image);
		this.setActionbarOverlay(true);
		this.getCustomActionBar().setBackgroundResource(R.color.statusbar_bg);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		position=this.getIntent().getIntExtra("position", 0);
		isLocalImage=this.getIntent().getBooleanExtra("isLocalImage", false);
		isSelected=this.getIntent().getBooleanExtra("isSelected", false);
		ArrayList<String> all=this.getIntent().getStringArrayListExtra("images");
		mImageList=ImageSelect.listOf(all);
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	@Override
	public void findViews() {
		mGalleryViewPager=(GalleryViewPager) findViewById(R.id.galleryViewPager);
		mToolsBar=findViewById(R.id.page_item_select);
		mSelectView=(ImageView) findViewById(R.id.layout_image_select);
	}
		
	@Override
	public void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_gallery);
		ArrayList<String> select=this.getIntent().getStringArrayListExtra("select");
		mDataAdapter = new ImagePagerAdapter(this, mImageList);
		mDataAdapter.addSelected(ImageSelect.listOf(select));
		mDataAdapter.setSelected(isSelected);
		if(isSelected)
		setTitle(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),Constants.MAX_SELECTED));
		mDataAdapter.setOnItemChangeListener(new OnItemChangeListener(){
				@Override
				public void onItemChange(int currentPosition){
					Log.d("onItemChange");
					mImage=mImageList.get(currentPosition);
					position=currentPosition;
					mSelectView.setSelected(mDataAdapter.getItemSelected(currentPosition));
				}
			});
		mDataAdapter.setOnActionClickListener(new ImagePagerAdapter.OnActionClickListener(){

			@Override
			public void onClickSelect(int position) {
				if(mDataAdapter.getSelected().size()<Constants.MAX_SELECTED||mDataAdapter.getItemSelected(position)){
					mDataAdapter.setItemSelected(position);
					setTitle(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),Constants.MAX_SELECTED));
					mDataAdapter.notifyDataSetChanged();
				}else{
					showToast(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),Constants.MAX_SELECTED));
				}
				
			}

			@Override
			public void onClickImage(int position) {
				if(getCustomActionBar().isShow()){
					getCustomActionBar().setShow(false);
					if(mDataAdapter.isSelected())
						mToolsBar.setVisibility(View.GONE);
				}else{
					getCustomActionBar().setShow(true);
					if(mDataAdapter.isSelected())
						mToolsBar.setVisibility(View.VISIBLE);
				}
			}
			
		});
		mSelectView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!mDataAdapter.isSelected())return;
				if(mDataAdapter.getSelected().size()<Constants.MAX_SELECTED||mDataAdapter.getItemSelected(position)){
					mDataAdapter.setItemSelected(position);
					setTitle(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),Constants.MAX_SELECTED));
					mDataAdapter.notifyDataSetChanged();
				}else{
					showToast(String.format(getString(R.string.title_select),mDataAdapter.getSelected().size(),Constants.MAX_SELECTED));
				}
				mSelectView.setSelected(mDataAdapter.getItemSelected(position));
			}
			
		});
        mGalleryViewPager.setAdapter(mDataAdapter);
        mGalleryViewPager.setCurrentItem(position);
	}
	@Override
	public void initData(Bundle savedInstanceState) {
	}
	@Override
	public void onMenuActionCreated(ActionMenu actionMenu) {
		super.onMenuActionCreated(actionMenu);
		//actionMenu.add(new ActionMenuItem(1,R.string.action_menu_done,R.drawable.ic_action_done,1));
	}
	@Override
	public boolean onMenuActionSelected(ActionMenuItem action) {
		Log.d("onMenuActionSelected ");
		switch(action.getId()){
			case 1:
				setActivityResult(ImageSelect.toList(mDataAdapter.getSelected()));
				break;
		}
		return super.onMenuActionSelected(action);
	}
	protected void setActivityResult(ArrayList<String> strs){
		Intent data = new Intent().putStringArrayListExtra(Constants.DATA_GALLERY_MULTIPLE_PICK, strs);
		this.setResult(RESULT_OK, data);
		this.finish();
	}
}
