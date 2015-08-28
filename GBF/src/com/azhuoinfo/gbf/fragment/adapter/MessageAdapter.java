package com.azhuoinfo.gbf.fragment.adapter;

import mobi.cangol.mobile.utils.StringUtils;
import mobi.cangol.mobile.utils.TimeUtils;
import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Message;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageAdapter extends BaseAdapter<Message> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public MessageAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Message item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_message, parent, false);
			holder=new ViewHolder();
			holder.nickname=(TextView) convertView.findViewById(R.id.listview_item_message_nickname);
			holder.content=(TextView) convertView.findViewById(R.id.listview_item_message_content);
			holder.time=(TextView) convertView.findViewById(R.id.listview_item_message_time);
			holder.avatar=(ImageView) convertView.findViewById(R.id.listview_item_message_avatar);
			convertView.setTag(holder);
		}
		holder.nickname.setText(StringUtils.null2Empty(item.getTitle()));
		holder.content.setText(StringUtils.null2Empty(item.getContent()));
		holder.time.setText(TimeUtils.formatLatelyTime(item.getTimestamp()));
		mImageLoader.displayImage(item.getImage(),holder.avatar,mDisplayImageOptions);
		if(item.getStatus()==0){
			holder.nickname.getPaint().setFakeBoldText(true);
			holder.content.getPaint().setFakeBoldText(true);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.common_title));
			holder.time.setTextColor(mContext.getResources().getColor(R.color.text_black));
		}else{
			holder.nickname.getPaint().setFakeBoldText(false);
			holder.content.getPaint().setFakeBoldText(false);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.common_content));
			holder.time.setTextColor(mContext.getResources().getColor(R.color.text_grey));
		}
		
		return convertView;
	}
	static class ViewHolder{
		View layout;
		TextView nickname;
		TextView content;
		TextView time;
		ImageView avatar;
	}
}
