package br.com.vocallab.avatarmobile.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.vocallab.avatarmobile.data.MessageController;
import br.com.vocallab.avatarmobile.model.Message;
import br.com.vocallab.avatarmobile.model.Visema;
import br.com.vocallab.avatarmobile.util.Util;

import com.example.avatarcertificacao.R;

public class MediaPlayerActivity extends Activity implements OnClickListener {

	public ImageView currentImageView;

	private static final int WARNING = 0;
	private static final int MESSAGE = 1;
	private static final int BLINKING_TIME = 200;
	private static final int EYESOPEN_DELAY = 5000;

	private MediaPlayer mp;
	private LinearLayout btnPlayWarning;
	private LinearLayout btnPlayMessage;
	private TextView courseTextView;
	public ArrayList<Visema> mVisemaList;
	private ArrayList<byte[]> bufferedImgs;
	public ImageView image;
	public int count = 0;
	private int current = 0;
	private final Handler handler = new Handler();
	private boolean isEyeOpen;
	private int avatarResourceId;
	private int avatarEyesClosedResourceId;

	Message message;
	int type;
	

	private ImageView avatarImgView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.anim_view);
		avatarImgView = (ImageView) findViewById(R.id.avatar_imgview);

		btnPlayWarning = (LinearLayout) findViewById(R.id.playAvisoButton);
		btnPlayWarning.setOnClickListener(this);
		btnPlayMessage = (LinearLayout) findViewById(R.id.playMessageButton);
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
		
		this.pickAvatarImages();
		
		isEyeOpen = true;

		doBlink();
	}

	private void pickAvatarImages() {
		
		String resourceName = Util.defineAvatarType(Integer.valueOf(message.getAvatarId()));
		
		String uri = "drawable/" + resourceName;
		avatarResourceId = this.getResources().getIdentifier(uri, null, this.getPackageName());
		
		String eyesClosedResourceUri = uri + Util.RESOURCE_EYES_CLOSED_SUFIX; 
		
		avatarEyesClosedResourceId = this.getResources().getIdentifier(eyesClosedResourceUri, null, this.getPackageName());
		
	}

	private void doBlink() {
		this.runOnUiThread(new Runnable() {
			int delay;

			@Override
			public void run() {
				if (isEyeOpen) {
					delay = BLINKING_TIME;
					avatarImgView.setImageResource(avatarEyesClosedResourceId);
					isEyeOpen = false;
				} else {
					delay = EYESOPEN_DELAY;
					avatarImgView.setImageResource(avatarResourceId);
					isEyeOpen = true;
				}
				handler.postDelayed(this, delay);
			}
		});

	}

	@Override
	public void onPause() {
		super.onPause();
		stopSound();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.playAvisoButton:
				if (!isEmptyList(WARNING))
					new LoadImagesTask(WARNING).execute();
				break;
			case R.id.playMessageButton:
				if (!isEmptyList(MESSAGE))
					new LoadImagesTask(MESSAGE).execute();
				break;
		}
	}

	private boolean isEmptyList(int listType) {
		boolean isEmptyList = false;
		switch (listType) {
			case MESSAGE:
				if (message.getMsgVisema().equals("")) {
					showWarningMessage(getString(R.string.no_messages));
					isEmptyList = true;
				}
				break;

			case WARNING:
				if (message.getNotifVisema().equals("")) {
					showWarningMessage(getString(R.string.no_warning));
					isEmptyList = true;
				}

				break;
		}
		return isEmptyList;
	}

	private void playMedia(int ID) {
		dodraw();
		this.playSound(ID);
	}

	private void dodraw() {
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
			//writeToFile(getString(R.string.stub_sound));
			//decoded = Base64.decode(getString(R.string.stub_sound), 0);
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

			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					btnPlayMessage.setEnabled(false);
					btnPlayWarning.setEnabled(false);
				}
			});

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					image.setImageResource(R.drawable.repouso);
					current = mVisemaList.size();
					btnPlayMessage.setEnabled(true);
					btnPlayWarning.setEnabled(true);
				}
			});

		} catch (Exception ex) {
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
			loadAnimation(id);
			loadImagesBytes();
			return null;
		}

		private void loadImagesBytes() {
			bufferedImgs = new ArrayList<byte[]>();
			for (int i = 0; i < mVisemaList.size(); i++) {
				bufferedImgs.add(getBytesFromResource(mVisemaList.get(i).getId()));
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			current = 0;
			playMedia(id);
			super.onPostExecute(result);
		}

	}

	public void loadAnimation(int ID) {
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
		}
	}

	private void showWarningMessage(String warningMessage) {
		Toast.makeText(this, warningMessage, Toast.LENGTH_SHORT).show();
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

}
