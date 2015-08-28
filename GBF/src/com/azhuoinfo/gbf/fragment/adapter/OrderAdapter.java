package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Order;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderAdapter extends BaseAdapter<Order> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public OrderAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_face_woman)
        .showImageForEmptyUri(R.drawable.ic_face_woman)
        .showImageOnFail(R.drawable.ic_face_woman)
        .build();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Order item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_order, parent, false);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.listview_item_order_title);
			holder.price=(TextView) convertView.findViewById(R.id.listview_item_order_price);
			holder.status=(TextView) convertView.findViewById(R.id.listview_item_order_status);
			holder.exchange=(TextView) convertView.findViewById(R.id.listview_item_order_exchange);
			holder.delivery=(TextView) convertView.findViewById(R.id.listview_item_order_delivery);
			holder.details=(TextView) convertView.findViewById(R.id.listview_item_order_details);
			holder.image=(ImageView) convertView.findViewById(R.id.listview_item_order_image);
			convertView.setTag(holder);
		}
		holder.title.setText(item.getTitle());
		holder.price.setText("￥"+item.getPrice());
		holder.exchange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onExchangeClick(position);
			}
		});
		holder.delivery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onDeliveryClick(position);
			}
		});
		holder.details.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onDetailsClick(position);
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
		void onExchangeClick(int position);
		void onDeliveryClick(int position);
		void onDetailsClick(int position);
	}
	static class ViewHolder{
		TextView title;
		TextView price;
		TextView status;
		TextView exchange;
		TextView delivery;
		TextView details;
		ImageView image;
	}
}
