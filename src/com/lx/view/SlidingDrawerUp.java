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
 * @author lingxin
 *
 */
public class SlidingDrawerUp extends LinearLayout implements OnTouchListener {

	// �ֱ�
	private Button mHandle;
	// ����
	private Scroller mScroller;
	// ����״̬
	private int currentState = 0;
	// ������ܸ߶� �ֱ��߶� ���붥���ĸ߶� �ƶ����� 
	private int viewHeight, handlerHeight, moveY;
	// ����y���λ��
	private float downY = 0;
	// ��״̬
	protected static final int STATE_OPEN = 1;
	// �ر�״̬
	protected static final int STATE_CLOSE = 2;

	public SlidingDrawerUp(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.content_up, null), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mHandle = (Button) findViewById(R.id.btnPull);
		mHandle.setBackgroundResource(R.drawable.top_switcher_collapsed);
		mHandle.setOnTouchListener(this);
		// Ĭ������Ǵ�״̬
		currentState = STATE_OPEN;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		viewHeight = this.getHeight();
		handlerHeight = mHandle.getHeight();
		Log.i("SlidingDrawerUp", "HEIGHT:" + viewHeight + " \nHANDLE_HEIGHT:" + handlerHeight);
		if (currentState == STATE_OPEN) {
			close();
		}
	}

	/**
	 * �رճ���
	 */
	public void close() {
		int cuY = moveY;
		mScroller.startScroll(0, cuY, 0, (viewHeight - handlerHeight) - cuY, 1000);
		this.invalidate();
		currentState = STATE_CLOSE;
		mHandle.setBackgroundResource(R.drawable.top_switcher_expanded);
		moveY = viewHeight - handlerHeight;
	}

	/**
	 * �򿪳���
	 * 
	 */
	public void open() {
		int cuY = moveY;
		mScroller.startScroll(0, cuY, 0, -cuY, 1000);
		this.invalidate();
		currentState = STATE_OPEN;
		mHandle.setBackgroundResource(R.drawable.top_switcher_collapsed);
		moveY = 0;
	}

	@Override
	public void computeScroll() {
		Log.i("computeScroll", "computeScroll");
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("SlidingDrawerUp", "ACTION_DOWN");
			if (currentState == STATE_OPEN) {
				mHandle.setBackgroundResource(R.drawable.top_switcher_collapsed_selected);
			} else if (currentState == STATE_CLOSE) {
				mHandle.setBackgroundResource(R.drawable.top_switcher_expanded_selected);
			}
			downY = event.getRawY();
			if (mScroller.computeScrollOffset()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(currentState == STATE_OPEN){
				moveY = (int) (downY-event.getRawY());
				if (moveY <0) {
					moveY = 0;
				} else if (moveY > (viewHeight - handlerHeight)) {
					moveY = viewHeight - handlerHeight;
				}
			}else if(currentState == STATE_CLOSE){
				moveY = (int) (event.getRawY()-downY);
				Log.i("SlidingDrawerUp", "ACTION_MOVE" + moveY);
				if (moveY < 0) {
					moveY = viewHeight - handlerHeight;
				} else if (moveY > (viewHeight - handlerHeight)) {
					moveY = 0;
				}else{
					moveY = viewHeight - handlerHeight - moveY;
				}
			}
			scrollTo(0, moveY);
			break;
		case MotionEvent.ACTION_UP:
			Log.i("SlidingDrawerUp", "ACTION_UP" + moveY);
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
