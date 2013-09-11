package com.lx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.uieffects.R;
/**
 * 向上拉的抽屉
 * @author lingxin
 *
 */
public class SlidingDrawerDown extends LinearLayout implements OnTouchListener {

	// 手柄
	private Button mHandle;
	// 滑动
	private Scroller mScroller;
	// 抽屉状态
	private int currentState = 0;
	// 抽屉的总高度 手柄高度 距离顶部的高度 移动距离
	private int viewHeight, handlerHeight, moveY;

	// 按下y轴的位置
	private float downY = 0;

	// 打开状态
	protected static final int STATE_OPEN = 1;
	// 关闭状态
	protected static final int STATE_CLOSE = 2;

	public SlidingDrawerDown(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		mHandle = new Button(context);
		mHandle.setBackgroundResource(R.drawable.bottom_switcher_collapsed);
		this.addView(mHandle, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mHandle.setOnTouchListener(this);
		this.addView(LayoutInflater.from(context).inflate(R.layout.content_down, null), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// 默认情况是打开状态
		currentState = STATE_OPEN;
	}

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		viewHeight = this.getHeight();
		handlerHeight = mHandle.getHeight();
		Log.i("onTouch", " \nHEIGHT:" + viewHeight + " \nHANDLE_HEIGHT:" + handlerHeight);
		close();
	}

	/**
	 * 关闭抽屉
	 */
	public void close() {
		int cuY = moveY;
		mScroller.startScroll(0, -cuY, 0, -((viewHeight - handlerHeight) - cuY) , 1000);
		this.invalidate();
		currentState = STATE_CLOSE;
		mHandle.setBackgroundResource(R.drawable.bottom_switcher_expanded);
		moveY = viewHeight - handlerHeight;
	}

	/**
	 * 打开抽屉
	 * 
	 */
	public void open() {
		int cuY = moveY;
		mScroller.startScroll(0, -cuY, 0, cuY, 1000);
		this.invalidate();
		currentState = STATE_OPEN;
		mHandle.setBackgroundResource(R.drawable.bottom_switcher_collapsed);
		moveY = 0;
	}

	@Override
	public void computeScroll() {
		Log.i("computeScroll", "computeScroll");
		if (mScroller.computeScrollOffset()) {
			Log.i("onTouch", "computeScrollOffset＝＝＝＝＝"+mScroller.getCurrY());
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("onTouch", "ACTION_DOWN");
			if (currentState == STATE_OPEN) {
				mHandle.setBackgroundResource(R.drawable.bottom_switcher_collapsed_selected);
			} else if (currentState == STATE_CLOSE) {
				mHandle.setBackgroundResource(R.drawable.bottom_switcher_expanded_selected);
			}
			downY = event.getRawY();
			if (mScroller.computeScrollOffset()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("scrollTo", "ACTION_MOVE");
			if(currentState == STATE_OPEN){
				moveY = (int) (event.getRawY()-downY);
				if(moveY<0){
					// 打开状态 禁止向上滑动 
					moveY = 0;
				}else if(moveY > (viewHeight-handlerHeight)){
					// 打开状态 禁止滑到顶部还继续滑的情况 
					moveY = (viewHeight-handlerHeight);
				}
			}else if(currentState == STATE_CLOSE){
				moveY = (int) (downY-event.getRawY());
				if(moveY<0){
					// 关闭状态 禁止向上滑动 
					moveY = (viewHeight-handlerHeight);
				}else if(moveY > (viewHeight-handlerHeight)){
					moveY = 0;
				}else{
					moveY = viewHeight-handlerHeight - moveY;
				}
			}
			scrollTo(0, -moveY);
			break;
		case MotionEvent.ACTION_UP:
			Log.i("onTouch", "ACTION_UP" + moveY);
			if (currentState == STATE_OPEN) {
				// 当打开状态点击手柄
				close();
			} else if (currentState == STATE_CLOSE) {
				// 当关闭状态点击手柄
				open();
			}
			break;
		}
		return true;
	}

}
