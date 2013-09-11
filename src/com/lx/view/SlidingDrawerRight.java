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
 * �������ĳ���
 * 
 * @author lingxin
 * 
 */
public class SlidingDrawerRight extends LinearLayout implements OnTouchListener {

	// �ֱ�
	private Button mHandle;
	// ����
	private Scroller mScroller;
	// ����״̬
	private int currentState = 0;
	// ������ܿ�� �ֱ���� ���붥���Ŀ�� �ƶ�����
	private int viewWidth, handlerWidth, moveX;
	// ����x���λ��
	private float downX = 0;
	// ��״̬
	protected static final int STATE_OPEN = 1;
	// �ر�״̬
	protected static final int STATE_CLOSE = 2;

	public SlidingDrawerRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.content_right, null), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mHandle = (Button) findViewById(R.id.btnRight);
		mHandle.setBackgroundResource(R.drawable.right_switcher_collapsed);
		mHandle.setOnTouchListener(this);
		// Ĭ������Ǵ�״̬
		currentState = STATE_OPEN;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		viewWidth = this.getWidth();
		handlerWidth = mHandle.getWidth();
		Log.i("SlidingDrawerRight", "Width:" + viewWidth + " \nHANDLE_Width:" + handlerWidth);
		if (currentState == STATE_OPEN) {
			close();
		}
	}

	/**
	 * �رճ���
	 */
	public void close() {
		Log.i("SlidingDrawerRight", "close");
		int cuX = moveX;
		mScroller.startScroll(0 - cuX, 0, -((viewWidth - handlerWidth) - cuX), 0, 1000);
		this.invalidate();
		currentState = STATE_CLOSE;
		mHandle.setBackgroundResource(R.drawable.right_switcher_expanded);
		moveX = viewWidth - handlerWidth;
	}

	/**
	 * �򿪳���
	 * 
	 */
	public void open() {
		Log.i("SlidingDrawerRight", "open");
		int cuX = moveX;
		mScroller.startScroll(-cuX, 0, cuX, 0, 1000);
		this.invalidate();
		currentState = STATE_OPEN;
		mHandle.setBackgroundResource(R.drawable.right_switcher_collapsed);
		moveX = 0;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("SlidingDrawerRight", "ACTION_DOWN");
			if (currentState == STATE_OPEN) {
				mHandle.setBackgroundResource(R.drawable.right_switcher_collapsed_selected);
			} else if (currentState == STATE_CLOSE) {
				mHandle.setBackgroundResource(R.drawable.right_switcher_expanded_selected);
			}
			downX = event.getRawX();
			if (mScroller.computeScrollOffset()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (currentState == STATE_OPEN) {
				moveX = (int) (event.getRawX() -downX);
				if (moveX < 0) {
					moveX = 0;
				} else if (moveX > (viewWidth - handlerWidth)) {
					moveX = viewWidth - handlerWidth;
				}
			} else if (currentState == STATE_CLOSE) {
				moveX = (int) (downX - event.getRawX());
				Log.i("SlidingDrawerRight", "ACTION_MOVE" + moveX);
				if (moveX < 0) {
					moveX = viewWidth - handlerWidth;
				} else if (moveX > (viewWidth - handlerWidth)) {
					moveX = 0;
				} else {
					moveX = viewWidth - handlerWidth - moveX;
				}
			}
			scrollTo(-moveX, 0);
			break;
		case MotionEvent.ACTION_UP:
			Log.i("SlidingDrawerRight", "ACTION_UP" + moveX);
			if (currentState == STATE_OPEN) {
				// ����״̬����ֱ�
				close();
			} else if (currentState == STATE_CLOSE) {
				// ���ر�״̬����ֱ�
				open();
			}
			break;
		}
		return true;
	}

}
