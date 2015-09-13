package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Sale;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SaleAdapter extends BaseAdapter<Sale> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public SaleAdapter(Context context) {
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
		final Sale item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_sale, parent, false);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.listview_item_sale_title);
			holder.out=(TextView) convertView.findViewById(R.id.listview_item_sale_out);
			holder.up=(TextView) convertView.findViewById(R.id.listview_item_sale_up);
			holder.price=(TextView) convertView.findViewById(R.id.listview_item_sale_price);
			holder.image=(ImageView) convertView.findViewById(R.id.listview_item_sale_image);
			convertView.setTag(holder);
		}
		holder.title.setText(item.getTitle());
		holder.price.setText("￥"+item.getPrice());
		holder.out.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onOutClick(position);
			}
		});
		holder.up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onUpClick(position);
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
		void onOutClick(int position);
		void onUpClick(int position);
	}
	static class ViewHolder{
		TextView title;
		TextView price;
		TextView out;
		TextView up;
		ImageView image;
	}
}
