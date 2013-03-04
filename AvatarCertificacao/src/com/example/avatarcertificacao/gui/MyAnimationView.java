package com.example.avatarcertificacao.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.avatarcertificacao.R;
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
	ArrayList<byte[]> bufferedImgs;
	Bitmap bitmap;
	int delayCount = 0;
	int i = 1;
	BitmapFactory.Options op;

	public MyAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		 bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

		
		new LoadImagesTask().execute();
	}

	/*ideally this should be in a background thread*/
	public void loadAnimation() {
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
		String visemaList = Util.loadList(this.getContext(), "teste.txt");
		/*if (genero == Util.MASCULINO) {
			mLexemaList = Util.loadList(this.getContext(), "masculino.txt");
		} else {
			mLexemaList = Util.loadList(this.getContext(), "feminino.txt");
		}*/
		Message msg = MessageController.getInstance(getContext()).getSelectedMessage();

		mVisemaList = Util.createVisemaList(this.getContext(), visemaList, msg.getAvatarId());
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
					System.out.println("Delay de " + play_frame + " a " + (play_frame + 1) + " = " + mVisemaList.get(play_frame).getDelay());
					System.out.println("passaram de " + play_frame + " a " + play_frame + 1 + " "
							+ (time - mVisemaList.get(play_frame).getDelay()));
					last_tick = System.currentTimeMillis();
					delayCount += Math.round(mVisemaList.get(play_frame).getDelay());
					bitmap = BitmapFactory.decodeByteArray(bufferedImgs.get(play_frame), 0, bufferedImgs.get(play_frame).length,op);
					c.drawBitmap(bitmap, draw_x, draw_y, null);

					play_frame++;
					postInvalidate();
				} else //still within delay.  redraw current frame
				{
					bitmap = BitmapFactory.decodeByteArray(bufferedImgs.get(play_frame), 0, bufferedImgs.get(play_frame).length,op);
					c.drawBitmap(bitmap, c.getWidth()/2, (float) (c.getHeight()/2.), null);
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

	public byte[] getBytesFromResource(final int res) {
		byte[] buffer = null;
		InputStream input = null;

		try {

			input = getResources().openRawResource(res);
			buffer = new byte[input.available()];
			if (input.read(buffer, 0, buffer.length) != buffer.length) {
				buffer = null;
			}
		} catch (IOException e) {
			System.out.println("EXCEPTION" + e.getMessage());
			buffer = null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}

		return buffer;
	}

	public class LoadImagesTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			loadAnimation();
			loadImagesBytes();
			return null;
		}

		private void loadImagesBytes() {
			String uri = "";
			String filename = "";
			int id = 0;

			bufferedImgs = new ArrayList<byte[]>();

			for (int i = 0; i < mVisemaList.size(); i++) {
				filename = mVisemaList.get(i).getFileName();
				uri = "drawable/" + filename;
				id = MyAnimationView.this.getContext().getResources()
						.getIdentifier(uri, null, MyAnimationView.this.getContext().getPackageName());
				bufferedImgs.add(getBytesFromResource(id));
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
