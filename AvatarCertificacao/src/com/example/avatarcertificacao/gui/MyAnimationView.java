package com.example.avatarcertificacao.gui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.model.Message;
import com.example.avatarcertificacao.model.Visema;
import com.example.avatarcertificacao.util.Util;

public class MyAnimationView extends ImageView {
	private static final String TAG = "AnimationTest:AnimationView";

	private boolean mIsPlaying = false;
	private boolean mStartPlaying = false;
	private Context mContext = null;

	private ArrayList<Visema> mVisemaList;

	private int play_frame = 0;
	private long last_tick = 0;

	public MyAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/*ideally this should be in a background thread*/
	public void loadAnimation(String prefix, int nframes, int genero) {
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
		if (mVisemaList != null) {
			mVisemaList.clear();
		}
		/*if (genero == Util.MASCULINO) {
			mLexemaList = Util.loadList(this.getContext(), "masculino.txt");
		} else {
			mLexemaList = Util.loadList(this.getContext(), "feminino.txt");
		}*/
		Message msg = MessageController.getInstance(getContext()).getSelectedMessage();

		mVisemaList = Util.createVisemaList(this.getContext(), msg.getMsgVisema(), msg.getAvatarId());
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
		if (play_frame >= mVisemaList.size()) {
			mIsPlaying = false;
		} else {
			long time = (System.currentTimeMillis() - last_tick);
			int draw_x = 0;
			int draw_y = 0;
			if (mVisemaList.get(play_frame) != null) {
				if (time >= mVisemaList.get(play_frame).getDelay()) //the delay time has passed. set next frame
				{
					System.out.println("delay:" + mVisemaList.get(play_frame).getDelay());
					last_tick = System.currentTimeMillis();
					c.drawBitmap(mVisemaList.get(play_frame).getImage(), draw_x, draw_y, null);
					play_frame++;
					postInvalidate();
				} else //still within delay.  redraw current frame
				{
					c.drawBitmap(mVisemaList.get(play_frame).getImage(), draw_x, draw_y, null);
					postInvalidate();
				}
			}
		}
	}

	public void playAnimation() {
		mStartPlaying = true;
		postInvalidate();
	}

	public void stopAnimation() {
		mStartPlaying = false;
		postInvalidate();
	}

	public void rewindAnimation() {
		play_frame = 0;
		postInvalidate();
	}
}
