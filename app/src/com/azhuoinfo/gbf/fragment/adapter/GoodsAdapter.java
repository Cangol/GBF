package com.azhuoinfo.gbf.fragment.adapter;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Goods;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodsAdapter extends BaseAdapter<Goods> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private boolean isActionMultiplePick;
	private FrameLayout.LayoutParams mImageViewLayoutParams;
	public GoodsAdapter(Context context) {
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
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Goods item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.gridview_item_goods, parent, false);
			holder=new ViewHolder();
			holder.image=(ImageView) convertView.findViewById(R.id.gridview_item_good_image);
			holder.name=(TextView) convertView.findViewById(R.id.gridview_item_good_name);
			holder.price=(TextView) convertView.findViewById(R.id.gridview_item_good_price);
			
			convertView.setTag(holder);
		}
		holder.name.setText(item.getName());
		holder.price.setText(item.getPrice());
		
		
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
		TextView name;
		TextView price;
	}
}
