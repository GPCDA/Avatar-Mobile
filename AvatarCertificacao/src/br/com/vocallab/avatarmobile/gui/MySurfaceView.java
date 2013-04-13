package br.com.vocallab.avatarmobile.gui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import br.com.vocallab.avatarmobile.data.MessageController;
import br.com.vocallab.avatarmobile.model.Message;
import br.com.vocallab.avatarmobile.model.Visema;
import br.com.vocallab.avatarmobile.util.Util;


class MySurfaceView extends SurfaceView implements Runnable {

	Thread thread = null;
	SurfaceHolder surfaceHolder;
	volatile boolean running = false;
	private int play_frame = 0;
	private long last_tick = 0;
	Bitmap bitMap;
	private ArrayList<Visema> mVisemaList;

	int i = 1;

	public MySurfaceView(Context context) {
		super(context);
		init(context);
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		surfaceHolder = getHolder();

		running = true;
		thread = new Thread(this);

		loadAnimation("", 0);

		thread.start();
	}

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

	//////////////////////////////////////////////////////////

	public void run() {

		while (running) {
			if (play_frame >= mVisemaList.size()) {
				running = false;
			} else {
				long time = (System.currentTimeMillis() - last_tick);
				int draw_x = 0;
				int draw_y = 0;
				if (mVisemaList.get(play_frame) != null) {
					if (time >= mVisemaList.get(play_frame).getDelay()) //the delay time has passed. set next frame
					{
						if (surfaceHolder.getSurface().isValid()) {

							Canvas canvas = surfaceHolder.lockCanvas();

							if (canvas == null) {
								Log.e("null", "n");
							} else {
								canvas = prepareAndDraw(draw_x, draw_y, canvas);
								canvas.save();
								surfaceHolder.unlockCanvasAndPost(canvas);
								play_frame++;
							}

						}
					} else {//still within delay.  redraw current frame
						if (surfaceHolder.getSurface().isValid()) {

							Canvas canvas = surfaceHolder.lockCanvas();

							if (canvas == null) {
								Log.e("null", "n");
							} else {
								canvas = prepareAndDraw(draw_x, draw_y, canvas);
								canvas.save();
								surfaceHolder.unlockCanvasAndPost(canvas);
								play_frame++;
							}

						}
					}
				}

			}

		}
	}

	private Canvas prepareAndDraw(int draw_x, int draw_y, Canvas canvas) {
		System.out.println("delay:" + mVisemaList.get(play_frame).getDelay());
		last_tick = System.currentTimeMillis();
		//Bitmap bm = mVisemaList.get(play_frame).getImage();
		String filename = mVisemaList.get(play_frame).getFileName();
		String uri = "drawable/" + filename;
		int id = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
		BitmapDrawable d = (BitmapDrawable) getContext().getResources().getDrawable(id);
		Bitmap bm = d.getBitmap();

		System.out.println("desenhei " + filename);
		canvas.drawBitmap(bm, draw_x, draw_y, null);
		
		return canvas;

	}
}
