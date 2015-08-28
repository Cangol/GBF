package com.azhuoinfo.gbf.fragment.adapter;

import mobi.cangol.mobile.utils.StringUtils;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Friend;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FriendAdapter extends BaseAdapter<Friend> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public FriendAdapter(Context context) {
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
		final Friend item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_friend, parent, false);
			holder=new ViewHolder();
			holder.nickname=(TextView) convertView.findViewById(R.id.listview_item_friend_nickname);
			holder.status=(TextView) convertView.findViewById(R.id.listview_item_friend_status);
			holder.avatar=(ImageView) convertView.findViewById(R.id.listview_item_friend_avatar);
			convertView.setTag(holder);
		}
		holder.nickname.setText(StringUtils.null2Empty(item.getNickname()));
		holder.status.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnActionClickListener!=null)
					mOnActionClickListener.onClick(position);
			}
		});
		mImageLoader.displayImage(item.getAvatar(),holder.avatar,mDisplayImageOptions);
		
		return convertView;
	}
	private OnActionClickListener mOnActionClickListener;
	
	public void setOnActionClickListener(
			OnActionClickListener mOnActionClickListener) {
		this.mOnActionClickListener = mOnActionClickListener;
	}
	public interface OnActionClickListener{
		void onClick(int position);
	}
	static class ViewHolder{
		TextView nickname;
		TextView status;
		ImageView avatar;
	}
}
