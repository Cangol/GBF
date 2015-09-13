package com.azhuoinfo.gbf.view;

import com.azhuoinfo.gbf.R;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageBar extends LinearLayout{
	private View mMessageBarView;
	private TextView mTextTv;
	private ImageView mCloseBtn;
	private Context mContext;
	private Intent mIntent;
	private OnClickAction mOnClickAction;
	public MessageBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		this.setOrientation(LinearLayout.VERTICAL);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMessageBarView = mInflater.inflate(R.layout.common_messagebar_view, this);
		mCloseBtn = (ImageView) mMessageBarView.findViewById(R.id.messagebar_close);
		mTextTv = (TextView) mMessageBarView.findViewById(R.id.messagebar_text);
		initViews();
		this.setVisibility(View.GONE);
	}
	public void initViews(){
		mCloseBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mMessageBarView.setVisibility(View.GONE);
			}
		});
		mTextTv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mMessageBarView.setVisibility(View.GONE);
				if(mIntent!=null){
					mContext.startActivity(mIntent);
					mIntent=null;
				}
				if(mOnClickAction!=null)mOnClickAction.OnClick();
			}
		});
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if(visibility==View.VISIBLE){
			mMessageBarView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top));
		}else{
			mMessageBarView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
		}
	}
	public void setMessage(CharSequence msg) {
		mTextTv.setText(msg);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mIntent=null;
	}
	public void setMessage(int resid) {
		mTextTv.setText(resid);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mIntent=null;
	}
	public void setMessage(CharSequence msg,Intent intent) {
		mTextTv.setText(msg);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mIntent=intent;
	}
	public void setMessage(int resid,Intent intent) {
		mTextTv.setText(resid);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mIntent=intent;
	}
	public void setMessage(int resid,OnClickAction mOnClickAction) {
		mTextTv.setText(resid);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mOnClickAction = mOnClickAction;
	}
	public void setMessage(CharSequence msg,OnClickAction mOnClickAction) {
		mTextTv.setText(msg);
		mMessageBarView.setVisibility(View.VISIBLE);
		this.mOnClickAction = mOnClickAction;
	}
	public interface OnClickAction{
		void OnClick();
	}
}
