package com.azhuoinfo.gbf.fragment.adapter;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GalleryAdapter extends BaseAdapter<String> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private boolean isActionMultiplePick;
	private FrameLayout.LayoutParams mImageViewLayoutParams;
	public GalleryAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
		.cacheOnDisk(false)
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
		mImageViewLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	public void setMultiplePick(boolean isMultiplePick) {
		this.isActionMultiplePick = isMultiplePick;
	}
	
	public boolean isActionMultiplePick() {
		return isActionMultiplePick;
	}
	public void setItemSize(int width,int height){
		mImageViewLayoutParams.width=width;
		mImageViewLayoutParams.height=height;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final String item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.gallery_item_image, parent, false);
			holder=new ViewHolder();
			holder.image=(ImageView) convertView.findViewById(R.id.gallery_item_image);
			holder.imageSelected=(ImageView) convertView.findViewById(R.id.gallery_item_image_selected);
			convertView.setTag(holder);
		}
		if (isActionMultiplePick) {
			holder.imageSelected.setVisibility(View.VISIBLE);
			holder.imageSelected.setSelected(getItemSelected(position));
		} else {
			holder.imageSelected.setVisibility(View.GONE);
			holder.imageSelected.setSelected(false);
		}
		mImageLoader.displayImage(item,holder.image,mDisplayImageOptions);
		holder.image.setTag(item);
		holder.image.setLayoutParams(mImageViewLayoutParams);

		holder.image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClickImage(position);
			}
			
		});
		holder.imageSelected.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClickSelect(position);
			}
			
		});
		return convertView;
	}
	private OnActionClickListener mOnActionClickListener;
	
	public void setOnActionClickListener(
			OnActionClickListener mOnActionClickListener) {
		this.mOnActionClickListener = mOnActionClickListener;
	}
	public interface OnActionClickListener{
		void onClickImage(int position);
		void onClickSelect(int position);
	}
	class ViewHolder {
		ImageView image;
		ImageView imageSelected;
	}
}
