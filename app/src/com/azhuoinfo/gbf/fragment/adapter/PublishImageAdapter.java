package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.PublishImage;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PublishImageAdapter extends BaseAdapter<PublishImage> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public PublishImageAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
	}
	public void setImageLayoutParams(ImageView imageView) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout. LayoutParams.MATCH_PARENT);
        layoutParams.width = (int) (displayMetrics.widthPixels-displayMetrics.density*12*4)/3;
        layoutParams.height = layoutParams.width;
        imageView.setLayoutParams(layoutParams);
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final PublishImage item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.gridview_item_image, parent, false);
			holder=new ViewHolder();
			holder.image=(ImageView) convertView.findViewById(R.id.gridview_item_image);
			holder.name=(TextView) convertView.findViewById(R.id.gridview_item_image_name);
			setImageLayoutParams(holder.image);
			convertView.setTag(holder);
		}
		
		if(null==item.getName()){
			holder.name.setText("");
			holder.name.setVisibility(View.GONE);
		}else{
			holder.name.setText(item.getName());
			holder.name.setVisibility(View.VISIBLE);
		}
		
		mImageLoader.displayImage(item.getUrl(),holder.image,mDisplayImageOptions);
		holder.image.setTag(item);
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView image;
		TextView name;
	}
}
