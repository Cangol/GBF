package com.azhuoinfo.gbf.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseFragment;
import mobi.cangol.mobile.logging.Log;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.ImageSelect;
import com.azhuoinfo.gbf.utils.GalleryUtils;
import com.azhuoinfo.gbf.view.touchgallery.BasePagerAdapter;
import com.azhuoinfo.gbf.view.touchgallery.GalleryViewPager;
import com.azhuoinfo.gbf.view.touchgallery.TouchImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 Class wraps URLs to adapter, then it instantiates {@link UrlTouchImageView} objects to paging up through them.
 */
public class ImagePagerAdapter extends BasePagerAdapter<ImageSelect> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private BaseFragment mFragment;
	private boolean isNative=true;
	private List<ImageSelect> mSelected=new ArrayList<ImageSelect>();
	private boolean isSelected=false;
	public void setFragment(BaseFragment mFragment) {
		this.mFragment = mFragment;
	}
	
	public boolean isNative() {
		return isNative;
	}

	public void setNative(boolean isNative) {
		this.isNative = isNative;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public List<ImageSelect> getSelected() {
		return mSelected;
	}

	public void addSelected(List<ImageSelect> selecteds) {
		this.mSelected.addAll(selecteds);
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public void setItemSelected(int position) {
		ImageSelect item=mResources.get(position);
		if(mSelected.contains(item)){
			mSelected.remove(item);
		}else{
			mSelected.add(item);
		}
	}
	public boolean getItemSelected(int position) {
		return mSelected.contains(mResources.get(position));
	}
	public ImagePagerAdapter(Context context, List<ImageSelect> list){
		super(context, list);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
		.cacheOnDisk(isNative)
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
	}
	
    static class ViewHolder{
    	ProgressBar progressBar;
    	TouchImageView imageView;
    	ImageView selectView;
    	ImageView deleteView;
	}
    
	@Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        final String image=mResources.get(position).toString();
        ViewHolder holder=(ViewHolder) ((View )object).getTag();
        ((GalleryViewPager)container).mCurrentView = holder.imageView;
    }

	@Override
    public Object instantiateItem(ViewGroup collection,final int position){
		Log.d("instantiateItem "+mResources.get(position));
    	String item=mResources.get(position).toString();
    	final ViewGroup viewGroup=(ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.page_item_image, null,false);
    	final ViewHolder holder=new ViewHolder();
    	holder.progressBar=(ProgressBar) viewGroup.findViewById(R.id.progressbar_page_item_progress);
    	holder.imageView=(TouchImageView) viewGroup.findViewById(R.id.page_item_touchImageView);
    	holder.selectView=(ImageView) viewGroup.findViewById(R.id.page_item_select);
    	
    	viewGroup.setTag(holder);
    	collection.addView(viewGroup, 0);
    	
    	if(item.startsWith("http")){
    		item=GalleryUtils.getResizerImageUrl(mContext,item);
    	}
//    	if (isSelected) {
//			holder.selectView.setVisibility(View.VISIBLE);
//			holder.selectView.setSelected(getItemSelected(position));
//		} else {
//			holder.selectView.setVisibility(View.GONE);
//			holder.selectView.setSelected(false);
//		}
    	
        mImageLoader.loadImage(item, mDisplayImageOptions, new ImageLoadingListener(){

   			@Override
   			public void onLoadingStarted(String imageUri, View view) {
   				holder.progressBar.setVisibility(View.VISIBLE);
   				Log.d("onLoadingStarted");
   			}

   			@Override
   			public void onLoadingFailed(String imageUri, View view,
   					FailReason failReason) {
   				Log.d("onLoadingFailed");
   			}

   			@Override
   			public void onLoadingComplete(String imageUri, View view,
   					Bitmap loadedImage) {
   				Log.d("onLoadingComplete");
   				holder.progressBar.setVisibility(View.GONE);
   				holder.imageView.setImageBitmap(loadedImage);
   			}

   			@Override
   			public void onLoadingCancelled(String imageUri, View view) {
   				Log.d("onLoadingCancelled");
   			}
           	
           });
        holder.imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClickImage(position);
				
			}
        	
        });
        holder.selectView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClickSelect(position);
				
			}
        	
        });
        
        return viewGroup;
    }

	@Override
	public void destroyItem(ViewGroup collection, int position, Object view) {
    	Log.d("destroyItem");
		super.destroyItem(collection, position, view);
	}

	private OnActionClickListener mOnActionClickListener;
    
    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
		this.mOnActionClickListener = onActionClickListener;
	}

	public interface OnActionClickListener{
		void onClickSelect(int position);
		void onClickImage(int position);
	}
}
