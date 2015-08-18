package com.azhuoinfo.gbf.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class MySwitch extends View {
	
	private final int DEFAULT_BGCOLOR = Color.parseColor("#8a7f83");
	private final int DEFAULT_SELECTEDCOLOR = Color.parseColor("#ea4c89");
	private final int DEFAULT_NOSELECTEDCOLOR = Color.parseColor("#c1c1c1");
	private final int DEFAULT_HANDLECOLOR = Color.WHITE;
	
	private static final int TOUCH_MODE_IDLE = 0;
	private static final int TOUCH_MODE_DOWN = 1;
	private static final int TOUCH_MODE_DRAGGING = 2;
	private static final int TOUCH_MODE_DOWN_IN_OTHER_AREA = 3;
	
	private int mHandleColor;
	private int mBgColor;
	private int mSelectedColor;
	private int mNoSelectedColor;

	private Context mContext;
	private float mDensity;
	private int mMinWidth = 76, mMinHeight = 25;
	private RectF mBgRect;//背景
	private RectF mCheckedRect;//代表选中的区域
	private RectF mNoCheckedRect;//代表未选中的区域
	private RectF mClipRect;
	private Paint mPaint;
	private int mInnerYPos;
	private int bgRound;
	private int mInnerWidth, mInnerHeight;
	private int mHandleHeightOffset = 6;
	private int mHandleRadius;//handle半径
	private int mCheckedRectX, mHandleX, mHandleDefaultY, mNoCheckedRectX;
	
	private int mTouchMode;
	private int mTouchSlop;
	private float mTouchX;
	private float mTouchY;
	private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
	private int mMinFlingVelocity;
	private boolean isChecked;
	private int mCommonOffset = 10;
	private OnSelectedChangeListener mOnSelectedChangeListener;
	

	public MySwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MySwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MySwitch(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		mBgColor = DEFAULT_BGCOLOR;
		mSelectedColor = DEFAULT_SELECTEDCOLOR;
		mHandleColor = DEFAULT_HANDLECOLOR;
		mNoSelectedColor = DEFAULT_NOSELECTEDCOLOR;
		mDensity = mContext.getResources().getDisplayMetrics().density;
		mMinWidth = (int)(mMinWidth * mDensity);
		mMinHeight = (int)(mMinHeight * mDensity);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();
		mMinFlingVelocity = config.getScaledMinimumFlingVelocity();
		setClickable(true);
		isChecked = true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBgRect = new RectF(0, 0, w, h);
		bgRound = h/2;
		
		
		mHandleRadius = (h - mHandleHeightOffset)/2;
		if(isChecked){
			mHandleX = w - (mHandleRadius + mHandleHeightOffset/2);
		}else{
			mHandleX = mHandleRadius + mHandleHeightOffset/2;
		}
		mHandleDefaultY = h - mHandleRadius - mHandleHeightOffset/2;
		
		mInnerWidth = w - (mHandleHeightOffset/2 + mCommonOffset)*2;
		mInnerHeight = h - mHandleHeightOffset*3;
		mInnerYPos = (h - mInnerHeight)/2;
		
		
		mCheckedRect = new RectF();
		
		mNoCheckedRect = new RectF();
		
		computeSelectedRectX();
		
		mClipRect = new RectF(getClipX(), mInnerYPos, 
				getClipX() + mInnerWidth,
				mInnerYPos + mInnerHeight);
	}
	
	private int getClipX(){
		return getWidth() - mHandleHeightOffset/2 - mCommonOffset - mInnerWidth;
	}
	
	private void computeSelectedRectX(){
		mCheckedRectX =  mHandleX + mHandleRadius - mCommonOffset - mInnerWidth;
	}
	
	private void computeNoSelectedRectX(){
		mNoCheckedRectX = mHandleX - mHandleHeightOffset + getWidth() - mHandleRadius  - mCommonOffset - mInnerWidth;
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = mMinWidth;
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = mMinHeight;
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		//draw bg
		mPaint.setColor(mBgColor);
		canvas.drawRoundRect(mBgRect, bgRound, bgRound, mPaint);
		canvas.restore();
		
		//Draw clip
		canvas.save();
		canvas.clipRect(mClipRect);
		//Draw checked
		mPaint.setColor(mSelectedColor);
		computeSelectedRectX();
		mCheckedRect.set(mCheckedRectX, mInnerYPos, 
				mCheckedRectX + mInnerWidth,
				mInnerYPos + mInnerHeight);
		canvas.drawRoundRect(mCheckedRect, mCheckedRect.height()/2, mCheckedRect.height()/2, mPaint);
		//Draw noChecked
		mPaint.setColor(mNoSelectedColor);
		computeNoSelectedRectX();
		mNoCheckedRect.set(mNoCheckedRectX, mInnerYPos, 
				mNoCheckedRectX + mInnerWidth, mInnerYPos + mInnerHeight);
		canvas.drawRoundRect(mNoCheckedRect, mNoCheckedRect.height()/2, mNoCheckedRect.height()/2, mPaint);
		canvas.restore();
		
		//Draw handle
		canvas.save();
		mPaint.setColor(mHandleColor);
		canvas.drawCircle(mHandleX,
				mHandleDefaultY, mHandleRadius, mPaint);
		canvas.restore();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mVelocityTracker.addMovement(ev);
		final int action = ev.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();
			if (isEnabled() && hitHandle(x, y)) {
				mTouchMode = TOUCH_MODE_DOWN;
				mTouchX = x;
				mTouchY = y;
			}else if(isEnabled() && mClipRect.contains(x, y)){
				mTouchMode = TOUCH_MODE_DOWN_IN_OTHER_AREA;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			switch (mTouchMode) {
			case TOUCH_MODE_IDLE:
				// Didn't target the thumb, treat normally.
				break;

			case TOUCH_MODE_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				if (Math.abs(x - mTouchX) > mTouchSlop
						|| Math.abs(y - mTouchY) > mTouchSlop) {
					mTouchMode = TOUCH_MODE_DRAGGING;
					getParent().requestDisallowInterceptTouchEvent(true);
					mTouchX = x;
					mTouchY = y;
					return true;
				}
				break;
			}

			case TOUCH_MODE_DRAGGING: {
				final float x = ev.getX();
				final float dx = x - mTouchX;
				mHandleX += dx;
				if(mHandleX < mHandleRadius + mHandleHeightOffset/2){
					mHandleX = mHandleRadius + mHandleHeightOffset/2;
				}else if(mHandleX > getWidth() - (mHandleRadius + mHandleHeightOffset/2)){
					mHandleX = getWidth() - (mHandleRadius + mHandleHeightOffset/2);
				}
				invalidate();
				return true;
			}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			if (mTouchMode == TOUCH_MODE_DRAGGING) {
				stopDrag(ev);
				invalidate();
				return true;
			}else if(mTouchMode == TOUCH_MODE_DOWN_IN_OTHER_AREA){
				onSingleTouch(ev);
				return true;
			}
			mTouchMode = TOUCH_MODE_IDLE;
			mVelocityTracker.clear();
			break;
		}
		}

		return super.onTouchEvent(ev);
	}
	
	private void cancelSuperTouch(MotionEvent ev) {
		MotionEvent cancel = MotionEvent.obtain(ev);
		cancel.setAction(MotionEvent.ACTION_CANCEL);
		super.onTouchEvent(cancel);
		cancel.recycle();
	}
	
	private void stopDrag(MotionEvent ev){
		mTouchMode = TOUCH_MODE_IDLE;
		// Up and not canceled, also checks the switch has not been disabled
		// during the drag
		boolean commitChange = (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
				&& isEnabled();

		cancelSuperTouch(ev);

		if (commitChange) {
			boolean newState;
			mVelocityTracker.computeCurrentVelocity(1000);
			float xvel = mVelocityTracker.getXVelocity();
			if (Math.abs(xvel) > mMinFlingVelocity) {
				newState = xvel > 0;
			} else {
				newState = getHandleSelectedState();
			}
			setChecked(newState);
		} else {
			setChecked(isChecked);
		}
	}
	
	private void onSingleTouch(MotionEvent ev){
		mTouchMode = TOUCH_MODE_IDLE;
		// Up and not canceled, also checks the switch has not been disabled
		// during the drag
		boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP
				&& isEnabled();

		cancelSuperTouch(ev);

		if (commitChange) {
			boolean newState = isChecked;
			float upX = ev.getX();
			float upY = ev.getY();
			
			if(mClipRect.contains(upX, upY)){
				if(upX > getWidth()/2){//true
					newState = true;
				}else if(upX < getWidth()/2){
					newState = false;
				}
				if(newState != isChecked){
					setChecked(newState);
				}
			}
		}
	}

	
	private boolean getHandleSelectedState() {
		return mHandleX >= getWidth() / 2;
	}
	
	private boolean hitHandle(float x, float y){
		if(x <= mHandleX + mHandleRadius && x >= mHandleX - mHandleRadius
				&& y <= mHandleDefaultY + mHandleRadius && y >= mHandleDefaultY - mHandleRadius){
			return true;
		}else{
			return false;
		}
	}
	
	public int getmBgColor() {
		return mBgColor;
	}

	public void setmBgColor(int mBgColor) {
		this.mBgColor = mBgColor;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		if(this.isChecked != isChecked){
			if(null != mOnSelectedChangeListener)
				mOnSelectedChangeListener.onSelectedChanged(this, isChecked);
		}
		this.isChecked = isChecked;
		if(isChecked){
			mHandleX = getWidth() - (mHandleRadius + mHandleHeightOffset/2);
		}else{
			mHandleX = mHandleRadius + mHandleHeightOffset/2;
		}
		invalidate();
	}
	
	public void initChecked(boolean isChecked){
		if(this.isChecked == isChecked)
			return;
		this.isChecked = isChecked;
		if(isChecked){
			mHandleX = getWidth() - (mHandleRadius + mHandleHeightOffset/2);
		}else{
			mHandleX = mHandleRadius + mHandleHeightOffset/2;
		}
		invalidate();
	}
	
	
	
    public OnSelectedChangeListener getmOnSelectedChangeListener() {
		return mOnSelectedChangeListener;
	}

	public void setOnSelectedChangeListener(
			OnSelectedChangeListener onSelectedChangeListener) {
		this.mOnSelectedChangeListener = onSelectedChangeListener;
	}


	public static interface OnSelectedChangeListener {
        void onSelectedChanged(MySwitch mSwitch, boolean isSelected);
    }
}
