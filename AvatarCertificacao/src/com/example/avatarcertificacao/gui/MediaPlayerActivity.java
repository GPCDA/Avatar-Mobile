package com.example.avatarcertificacao.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.model.Message;
import com.example.avatarcertificacao.model.Visema;
import com.example.avatarcertificacao.util.Util;

public class MediaPlayerActivity extends Activity implements OnClickListener {

	//	private ImageView a_;
	//	private ImageView a;
	//	private ImageView emai;
	//	private ImageView emin;
	//	private ImageView f1;
	//	private ImageView f2;
	//	private ImageView i1min;
	//	private ImageView i2min;
	//	private ImageView lmai;
	//	private ImageView k1;
	//	private ImageView k2;
	//	private ImageView k3;
	//	private ImageView l1mai;
	//	private ImageView l1min;
	//	private ImageView l2mai;
	//	private ImageView l3mai;
	//	private ImageView l3min;
	//	private ImageView l4min;
	//	private ImageView omai;
	//	private ImageView omin;
	//	private ImageView p1;
	//	private ImageView p2;
	//	private ImageView r1;
	//	private ImageView r2;
	//	private ImageView repouso;
	//	private ImageView s1mai;
	//	private ImageView s2mai;
	//	private ImageView s2min;
	//	private ImageView t1;
	//	private ImageView t2;
	//	private ImageView u;
	//	private ImageView u_;

	public ImageView currentImageView;

	private static final int WARNING = 0;
	private static final int MESSAGE = 1;
	MediaPlayer mp;
	ImageView btnPlayWarning;
	ImageView btnPlayMessage;
	TextView courseTextView;
	ArrayList<Visema> mList;
	ArrayList<Bitmap> mBitmapList;
	public ArrayList<Visema> mVisemaList;
	ArrayList<byte[]> bufferedImgs;
	public ImageView image;
	public int count = 0;
	private int current = 0;
	AnimationDrawable teste;
	private LruCache<Integer, Bitmap> mMemoryCache;

	final Handler handler = new Handler();

	Message message;
	int type;
	private ImageView l2min;
	private ImageView imai;
	private ImageView s1min;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//animationView = (MyAnimationView) findViewById(R.id.anim_view);
		image = (ImageView) findViewById(R.id.anim_view);
		//animationView.loadAnimation("shark", 16, Util.MASCULINO);

		btnPlayWarning = (ImageView) findViewById(R.id.playAvisoButton);
		btnPlayWarning.setOnClickListener(this);
		btnPlayMessage = (ImageView) findViewById(R.id.playMessageButton);
		btnPlayMessage.setOnClickListener(this);

		courseTextView = (TextView) findViewById(R.id.courseNameTextView);

		Bundle extraBundle = getIntent().getBundleExtra("message.details");
		int id = extraBundle.getInt("id");
		message = MessageController.getInstance(this).getMessage(id);

		if (message.getName().isEmpty()) {
			courseTextView.setText(R.string.admin);
		} else {
			courseTextView.setText(message.getName());
		}

	}

	//	private void findImageViews() {
	//		repouso = (ImageView) findViewById(R.idImage.repouso);
	//
	//		a = (ImageView) findViewById(R.idImage.a);
	//		a.setVisibility(View.GONE);
	//
	//		a_ = (ImageView) findViewById(R.idImage.a_);
	//		a_.setVisibility(View.GONE);
	//
	//		emai = (ImageView) findViewById(R.idImage.emai);
	//		emai.setVisibility(View.GONE);
	//
	//		emin = (ImageView) findViewById(R.idImage.emin);
	//		emin.setVisibility(View.GONE);
	//
	//		f1 = (ImageView) findViewById(R.idImage.f1);
	//		f1.setVisibility(View.GONE);
	//
	//		f2 = (ImageView) findViewById(R.idImage.f2);
	//		f2.setVisibility(View.GONE);
	//
	//		i1min = (ImageView) findViewById(R.idImage.i1min);
	//		i1min.setVisibility(View.GONE);
	//
	//		i2min = (ImageView) findViewById(R.idImage.i2min);
	//		i2min.setVisibility(View.GONE);
	//
	//		imai = (ImageView) findViewById(R.idImage.imai);
	//		imai.setVisibility(View.GONE);
	//
	//		k1 = (ImageView) findViewById(R.idImage.k1);
	//		k1.setVisibility(View.GONE);
	//
	//		k2 = (ImageView) findViewById(R.idImage.k2);
	//		k2.setVisibility(View.GONE);
	//
	//		k3 = (ImageView) findViewById(R.idImage.k3);
	//		k3.setVisibility(View.GONE);
	//
	//		l1mai = (ImageView) findViewById(R.idImage.l1mai);
	//		l1mai.setVisibility(View.GONE);
	//
	//		l1min = (ImageView) findViewById(R.idImage.l1min);
	//		l1min.setVisibility(View.GONE);
	//
	//		l2min = (ImageView) findViewById(R.idImage.l2min);
	//		l2min.setVisibility(View.GONE);
	//
	//		l3mai = (ImageView) findViewById(R.idImage.l3mai);
	//		l3mai.setVisibility(View.GONE);
	//
	//		l3mai = (ImageView) findViewById(R.idImage.l3mai);
	//		l3mai.setVisibility(View.GONE);
	//
	//		l3min = (ImageView) findViewById(R.idImage.l3min);
	//		l3min.setVisibility(View.GONE);
	//
	//		l4min = (ImageView) findViewById(R.idImage.l4min);
	//		l4min.setVisibility(View.GONE);
	//
	//		omai = (ImageView) findViewById(R.idImage.omai);
	//		omai.setVisibility(View.GONE);
	//
	//		omin = (ImageView) findViewById(R.idImage.omin);
	//		omin.setVisibility(View.GONE);
	//
	//		p1 = (ImageView) findViewById(R.idImage.p1);
	//		p1.setVisibility(View.GONE);
	//
	//		p2 = (ImageView) findViewById(R.idImage.p2);
	//		p2.setVisibility(View.GONE);
	//
	//		r1 = (ImageView) findViewById(R.idImage.r1);
	//		r1.setVisibility(View.GONE);
	//
	//		r2 = (ImageView) findViewById(R.idImage.r2);
	//		r2.setVisibility(View.GONE);
	//
	//		s1mai = (ImageView) findViewById(R.idImage.s1mai);
	//		s1mai.setVisibility(View.GONE);
	//
	//		s1min = (ImageView) findViewById(R.idImage.s1min);
	//		s1min.setVisibility(View.GONE);
	//
	//		s2mai = (ImageView) findViewById(R.idImage.s2mai);
	//		s2mai.setVisibility(View.GONE);
	//
	//		s2min = (ImageView) findViewById(R.idImage.s2min);
	//		s2min.setVisibility(View.GONE);
	//
	//		t1 = (ImageView) findViewById(R.idImage.t1);
	//		t1.setVisibility(View.GONE);
	//
	//		t2 = (ImageView) findViewById(R.idImage.t2);
	//		t2.setVisibility(View.GONE);
	//
	//		u = (ImageView) findViewById(R.idImage.u);
	//		u.setVisibility(View.GONE);
	//
	//		u_ = (ImageView) findViewById(R.idImage.u_);
	//		u_.setVisibility(View.GONE);
	//
	//	}

	@Override
	public void onPause() {
		super.onPause();
		stopSound();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.playAvisoButton:
				new LoadImagesTask(WARNING).execute();
				break;
			case R.id.playMessageButton:
				new LoadImagesTask(MESSAGE).execute();
				break;
		}
	}

	private void playMedia(int ID) {
		dodraw();
		this.playSound(ID);
	}

	private void dodraw() {
		// TODO Auto-generated method stub
		this.runOnUiThread(new Runnable() {

			private Bitmap bm;

			@Override
			public void run() {
				if (current < mVisemaList.size()) {
					if (bm != null && !bm.isRecycled()) {
						bm.recycle();
						bm = null;
						System.gc();
					}
					image.setDrawingCacheQuality(ImageView.DRAWING_CACHE_QUALITY_LOW);
					bm = BitmapFactory.decodeByteArray(bufferedImgs.get(current), 0, bufferedImgs.get(current).length);
					image.setImageBitmap(bm);
					handler.postDelayed(this, mVisemaList.get(current).getDelay());
					current++;
				}

			}
		});
	}

	public void createAnimation() {
		String filename;
		String uri;
		int id;
		teste = new AnimationDrawable();
		for (int i = 0; i < mVisemaList.size(); i++) {
			filename = mVisemaList.get(i).getFileName();
			uri = "drawable/" + filename;
			id = this.getResources().getIdentifier(uri, null, this.getPackageName());
			teste.addFrame(getResources().getDrawable(id), (int) mVisemaList.get(i).getDelay());
		}
		teste.setOneShot(true);
		image.setBackgroundDrawable(teste);
		//		playMedia(message.getAvatarId());
		image.post(new Runnable() {
			@Override
			public void run() {
				teste.start();
			}
		});
	}

	private void writeToFile(String str) {
		String filename = "filename.txt";
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		FileOutputStream fos;
		byte[] data = str.getBytes();
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// handle exception
		} catch (IOException e) {
			// handle exception
		}

	}

	private void playSound(int ID) {

		byte[] decoded;
		
		if (Util.isStub) {
			writeToFile(getString(R.string.stub_sound));
			decoded = Base64.decode(getString(R.string.stub_sound), 0);
		} else if (ID == MESSAGE) {
			writeToFile(message.getMsgAudio());
			decoded = Base64.decode(message.getMsgAudio(), 0);
		} else {
			writeToFile(message.getNotifAudio());
			decoded = Base64.decode(message.getNotifAudio(), 0);
		}

		try {
			File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
			tempMp3.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(tempMp3);
			fos.write(decoded);
			fos.close();
			MediaPlayer mediaPlayer = new MediaPlayer();
			FileInputStream fis = new FileInputStream(tempMp3);
			mediaPlayer.setDataSource(fis.getFD());
			mediaPlayer.prepare();
			mp = mediaPlayer;
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					current = mVisemaList.size() - 2;
				}
			});

		} catch (IOException ex) {
			String s = ex.toString();
			ex.printStackTrace();
		}
	}

	private void stopSound() {
		if (mp != null) {
			mp.stop();
		}
	}

	final Runnable animation = new Runnable() {
		private int current = 0;
		private Bitmap bm;

		@Override
		public void run() {
			if (current < mVisemaList.size()) {
				if (bm != null && !bm.isRecycled()) {
					bm.recycle();
					bm = null;
					System.gc();
				}
				image.setDrawingCacheQuality(ImageView.DRAWING_CACHE_QUALITY_LOW);
				bm = BitmapFactory.decodeByteArray(bufferedImgs.get(current), 0, bufferedImgs.get(current).length);
				image.setImageBitmap(bm);
				handler.postDelayed(this, 1);
				current++;
			}
		}
	};

	public class LoadImagesTask extends AsyncTask<Integer, Void, Void> {
		int id;

		public LoadImagesTask(int id) {
			this.id = id;
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			loadAnimation(id);
			loadImagesBytes();
			return null;
		}

		private void loadImagesBytes() {
			String uri = "";
			String filename = "";
			int id = 0;
			bufferedImgs = new ArrayList<byte[]>();
			for (int i = 0; i < mVisemaList.size(); i++) {
				bufferedImgs.add(getBytesFromResource(mVisemaList.get(i).getId()));
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			playMedia(id);
			super.onPostExecute(result);
		}

	}

	public void loadAnimation(int ID) {
		String visemaList;
		if (mVisemaList != null) {
			mVisemaList.clear();
		}

		switch (ID) {
			case MESSAGE:
				mVisemaList = Util.createVisemaList(this, message.getMsgVisema(), message.getAvatarId());
				break;
			case WARNING:
				mVisemaList = Util.createVisemaList(this, message.getNotifVisema(), message.getAvatarId());
				break;
			default:
				break;
		}
	}

	//	private void setImagesViewsToVisemas() {
	//		// TODO Auto-generated method stub
	//		for (Visema visema : mVisemaList) {
	//			switch (visema.getId()) {
	//				case R.drawable.a:
	//					visema.setImgView(a);
	//					break;
	//				case R.drawable.a_:
	//					visema.setImgView(a_);
	//					break;
	//				case R.drawable.emai:
	//					visema.setImgView(emai);
	//					break;
	//				case R.drawable.emin:
	//					visema.setImgView(emin);
	//					break;
	//				case R.drawable.f1:
	//					visema.setImgView(f1);
	//					break;
	//				case R.drawable.f2:
	//					visema.setImgView(f2);
	//					break;
	//				case R.drawable.i1min:
	//					visema.setImgView(i1min);
	//					break;
	//				case R.drawable.i2min:
	//					visema.setImgView(i2min);
	//					break;
	//				case R.drawable.imai:
	//					visema.setImgView(imai);
	//					break;
	//				case R.drawable.k1:
	//					visema.setImgView(k1);
	//					break;
	//				case R.drawable.k2:
	//					visema.setImgView(k2);
	//					break;
	//				case R.drawable.k3:
	//					visema.setImgView(k3);
	//					break;
	//				case R.drawable.l1min:
	//					visema.setImgView(l1min);
	//					break;
	//				case R.drawable.l1mai:
	//					visema.setImgView(l1mai);
	//					break;
	//				case R.drawable.l2min:
	//					visema.setImgView(l2min);
	//					break;
	//				case R.drawable.l2mai:
	//					visema.setImgView(l2mai);
	//					break;
	//				case R.drawable.l3mai:
	//					visema.setImgView(l3mai);
	//					break;
	//				case R.drawable.l3min:
	//					visema.setImgView(l3min);
	//					break;
	//				case R.drawable.l4min:
	//					visema.setImgView(l4min);
	//					break;
	//				case R.drawable.omai:
	//					visema.setImgView(omai);
	//					break;
	//				case R.drawable.omin:
	//					visema.setImgView(omin);
	//					break;
	//				case R.drawable.p1:
	//					visema.setImgView(p1);
	//					break;
	//				case R.drawable.p2:
	//					visema.setImgView(p2);
	//					break;
	//				case R.drawable.r1:
	//					visema.setImgView(r1);
	//					break;
	//				case R.drawable.r2:
	//					visema.setImgView(r2);
	//					break;
	//				case R.drawable.repouso:
	//					visema.setImgView(repouso);
	//					break;
	//				case R.drawable.s1mai:
	//					visema.setImgView(s1mai);
	//					break;
	//				case R.drawable.s2mai:
	//					visema.setImgView(s2mai);
	//					break;
	//				case R.drawable.s1min:
	//					visema.setImgView(s1min);
	//					break;
	//				case R.drawable.s2min:
	//					visema.setImgView(s2min);
	//					break;
	//				case R.drawable.t1:
	//					visema.setImgView(t1);
	//					break;
	//				case R.drawable.t2:
	//					visema.setImgView(t2);
	//					break;
	//
	//				case R.drawable.u:
	//					visema.setImgView(u);
	//					break;
	//				case R.drawable.u_:
	//					visema.setImgView(u_);
	//					break;
	//			}
	//
	//		}
	//
	//	}

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

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//	    MenuInflater inflater = getMenuInflater();
	//	    inflater.inflate(R.menu.option_menu, menu);
	//	    return true;
	//	}
	//	
	//	@Override
	//	public boolean onOptionsItemSelected(MenuItem item) {
	//		Intent intent;
	//		switch (item.getItemId()) {
	////		case R.optionMenu.settings:
	////			intent = new Intent(this, SettingsScreen.class);
	////			startActivity(intent);
	////			break;
	//		case R.optionMenu.logout:
	//			if (SessionStore.logout(this)) {
	//				intent = new Intent(this, LoginScreen.class);
	//				startActivity(intent);
	//				finish();
	//			} else {
	//				Toast.makeText(this, R.string.logout_problem, Toast.LENGTH_LONG).show();
	//			}
	//			
	//			break;
	//
	//		default:
	//			break;
	//		}
	//		return false;
	//		
	//	}
	public class LoadAnimationListTask extends AsyncTask<Void, Void, Void> {
		int delay;
		int id;

		public LoadAnimationListTask(int id, int delay) {
			this.id = id;
			this.delay = delay;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			teste.addFrame(getResources().getDrawable(id), delay);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			count++;
			super.onPostExecute(result);
		}

	}

	public void createBitmap() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.id.anim_view, options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		String imageType = options.outMimeType;

	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public void createBitmapList() {
		mBitmapList = new ArrayList<Bitmap>();
		for (int i = 0; i < mVisemaList.size(); i++) {
			mBitmapList.add(decodeSampledBitmapFromResource(getResources(), mVisemaList.get(i).getId(), 500, 500));

		}
	}

}
