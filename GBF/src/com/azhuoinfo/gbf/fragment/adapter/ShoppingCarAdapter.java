package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Goods;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShoppingCarAdapter extends BaseAdapter<Goods> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public ShoppingCarAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
		.cacheOnDisk(false)
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Goods item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_car, parent, false);
			holder=new ViewHolder();
			holder.check=(ImageView) convertView.findViewById(R.id.listview_item_car_check);
			holder.image=(ImageView) convertView.findViewById(R.id.listview_item_car_image);
			holder.title=(TextView) convertView.findViewById(R.id.listview_item_car_title);
			holder.price=(TextView) convertView.findViewById(R.id.listview_item_car_price);
			holder.num=(TextView) convertView.findViewById(R.id.listview_item_car_title);
			holder.details=(TextView) convertView.findViewById(R.id.listview_item_car_price);
			
			convertView.setTag(holder);
		}
		holder.title.setText(item.getName());
		holder.price.setText(item.getPrice());
		holder.num.setText(item.getName());
		holder.details.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClickDetails(position);
				
			}
		});
		mImageLoader.displayImage(item.getImage(),holder.image,mDisplayImageOptions);
		
		
		return convertView;
	}
	private OnActionClickListener mOnActionClickListener;
	
	public void setOnActionClickListener(
			OnActionClickListener mOnActionClickListener) {
		this.mOnActionClickListener = mOnActionClickListener;
	}
	public interface OnActionClickListener{
		void onClickDetails(int position);
		void onClickSelect(int position);
	}
	class ViewHolder {
		TextView title;
		TextView price;
		TextView details;
		TextView num;
		ImageView image;
		ImageView check;
	}
}
