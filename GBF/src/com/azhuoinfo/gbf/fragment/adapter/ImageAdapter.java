package com.azhuoinfo.gbf.fragment.adapter;

import mobi.cangol.mobile.utils.StringUtils;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Image;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapter extends BaseAdapter<Image> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private FrameLayout.LayoutParams mImageViewLayoutParams;
	public ImageAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
		mImageViewLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	public void setItemSize(int width,int height){
		mImageViewLayoutParams.width=width;
		mImageViewLayoutParams.height=height;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Image item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.gridview_item_image, parent, false);
			holder=new ViewHolder();
			holder.image=(ImageView) convertView.findViewById(R.id.gridview_item_image);
			holder.name=(TextView) convertView.findViewById(R.id.gridview_item_image_name);
			convertView.setTag(holder);
		}
		
		holder.name.setText(StringUtils.null2Empty(item.getName()));
		mImageLoader.displayImage(item.getThumb(),holder.image,mDisplayImageOptions);
		holder.image.setTag(item.getThumb());
		holder.image.setLayoutParams(mImageViewLayoutParams);
		return convertView;
	}
	
	static class ViewHolder{
		ImageView image;
		TextView name;
	}
}
