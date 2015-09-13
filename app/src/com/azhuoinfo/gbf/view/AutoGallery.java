package com.azhuoinfo.gbf.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import java.lang.ref.WeakReference;

import mobi.cangol.mobile.logging.Log;

public class AutoGallery extends ViewGroup {
	private final static String TAG="AutoGallery";
	private Gallery mGallery;
	private int radius = 6;
	private int mSelectedIndex = -1;
	private int mTotalItems;
	private int mIndicatorLeft;
	private int mIndicatorSize=radius*2*3;
	private int activeColor= 0xFFEB83A5;
	private int inactiveColor= 0x8F6D6D6D;

	private boolean isOnTouch = false;
	private long fadeOutTime = 3*1000;
	private MyHandler mHander;

	public AutoGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHander=new MyHandler(this);
		addGallery(context);
	}
	@SuppressLint("NewApi")
	private void addGallery(Context context) {
		mGallery = new Gallery(context){
			private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
				return e2.getX() > e1.getX();
			}

			public boolean onFling(MotionEvent e1, MotionEvent e2,
								   float velocityX, float velocityY) {
				int kEvent;
				if (isScrollingLeft(e1, e2)) {
					kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
				} else {
					kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
				}
				onKeyDown(kEvent, null);
				return true;
			}
		};
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mGallery.setPadding(0, 0, 0, 0);
		mGallery.setFadingEdgeLength(0);
		mGallery.setSoundEffectsEnabled(false);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			mGallery.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		addView(mGallery, params);
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				mSelectedIndex = position%mTotalItems;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}
	
	@Override
	protected void detachViewFromParent(View child) {
		super.detachViewFromParent(child);
		if(null!=mHander)mHander.removeMessages(1);
	}
	
	@Override
	protected void attachViewToParent(View child, int index, LayoutParams params) {
		super.attachViewToParent(child, index, params);
		//if(null!=mHander)mHander.sendEmptyMessageDelayed(1, fadeOutTime);
	}
	public void startFadeOut(long fadeOutTime){
		this.fadeOutTime=fadeOutTime;
		mHander.sendEmptyMessageDelayed(1, fadeOutTime);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		isOnTouch = true;
		return super.onTouchEvent(event);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		for (int index = 0; index < mGallery.getChildCount(); index++) {
			View child = mGallery.getChildAt(index);
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		}
		mGallery.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
		setMeasuredDimension(parentWidth, mGallery.getMeasuredHeight());
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		mIndicatorLeft = (int) ((getMeasuredWidth() - mTotalItems *  mIndicatorSize)/2);
		Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		paint.setAlpha(175);
		paint.setStyle(Style.FILL);
		for(int i=0; i<mTotalItems; i++) {
			if(i == mSelectedIndex) {
				paint.setColor(activeColor);
				canvas.drawCircle(mIndicatorLeft + i *  mIndicatorSize, mGallery.getBottom() - mIndicatorSize/2, radius, paint);
			} else {
				paint.setColor(inactiveColor);
				canvas.drawCircle(mIndicatorLeft + i *  mIndicatorSize, mGallery.getBottom() - mIndicatorSize/2, radius, paint);
			}
		}
	}
	public Gallery getGallery(){
		return mGallery;
	}
	public void setSelection(int selected){
		mGallery.setSelection(selected);
		mGallery.postInvalidate();
	}
	public void setAdapter(SpinnerAdapter adapter,int size) {
		mGallery.setAdapter(adapter);
		mTotalItems = size;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int width = mGallery.getMeasuredWidth();
		int height = mGallery.getMeasuredHeight();
		mGallery.layout(0, 0, width,height);
	}

	static class  MyHandler extends Handler {
		private final WeakReference<AutoGallery> mAutoGallery;
		MyHandler(AutoGallery autoGallery){
			mAutoGallery = new WeakReference<AutoGallery>(autoGallery);
		}
		public void handleMessage(Message msg){
			AutoGallery autoGallery = mAutoGallery.get();
			if(autoGallery!=null){
				if (!autoGallery.isOnTouch) {
					autoGallery.getGallery().onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				} else {
					autoGallery.isOnTouch = false;
				}
				sendEmptyMessageDelayed(1, autoGallery.fadeOutTime);
			}

		}
	}
}
