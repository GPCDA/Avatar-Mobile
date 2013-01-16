package com.example.avatarcertificacao;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class MyAnimationView extends ImageView {
	private static final String TAG = "AnimationTest:AnimationView";
	private Context mContext = null;

	private boolean mIsPlaying = false;
	private boolean mStartPlaying = false;

	private ArrayList<Lexema> mLexemaList;

	private int play_frame = 0;
	private long last_tick = 0;

	public MyAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/*ideally this should be in a background thread*/
	public void loadAnimation(String prefix, int nframes) {
//		mLexemaList.clear();
//		int delay = 100;
//		for (int x = 0; x < nframes; x++) {
//			String name = prefix + "_" + x;
//			Log.d(TAG, "loading animation frame: " + name);
//			int res_id = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
//			BitmapDrawable d = (BitmapDrawable) mContext.getResources().getDrawable(res_id);
//			Lexema lexema = new Lexema(delay,d.getBitmap());
//			delay += 50;
//			mLexemaList.add(lexema);
//		}
		
		mLexemaList = Util.loadList(this.getContext(), "masculino.txt");
	}

	@Override
	protected void onDraw(Canvas c) {
		Log.d(TAG, "onDraw called");
		if (mStartPlaying) {
			Log.d(TAG, "starting animation...");
			play_frame = 0;
			mStartPlaying = false;
			mIsPlaying = true;
			postInvalidate();
		} else if (mIsPlaying) {
			drawBitmap(c);
		}
	}

	private void drawBitmap(Canvas c) {
		if (play_frame >= mLexemaList.size()) {
			mIsPlaying = false;
		} else {
			long time = (System.currentTimeMillis() - last_tick);
			int draw_x = 0;
			int draw_y = 0;
			if (time >= mLexemaList.get(play_frame).getDelay()) //the delay time has passed. set next frame
			{
				System.out.println("delay:"+mLexemaList.get(play_frame).getDelay());
				last_tick = System.currentTimeMillis();
				c.drawBitmap(mLexemaList.get(play_frame).getImage(), draw_x, draw_y, null);
				play_frame++;
				postInvalidate();
			} else //still within delay.  redraw current frame
			{
				c.drawBitmap(mLexemaList.get(play_frame).getImage(), draw_x, draw_y, null);
				postInvalidate();
			}
		}
	}

	public void playAnimation() {
		mStartPlaying = true;
		postInvalidate();
	}
	
	public void stopAnimation(){
		mStartPlaying = false;
		postInvalidate();
	}
	public void rewindAnimation(){
		play_frame = 0;
		postInvalidate();
	}
}
