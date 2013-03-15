package com.example.avatarcertificacao.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.avatarcertificacao.model.Visema;

public class AnimationsContainer {
	public int FPS = 30; // animation FPS
	private ArrayList<Visema> mVisemaList;
	private ArrayList<Bitmap> mBitmapList;
	private Context context;
	private int index;
	private int localIndex = 0;
	private Resources res;

	// single instance procedures
	private static AnimationsContainer mInstance;

	private AnimationsContainer(Context context) {
		this.context = context;
	};

	public static AnimationsContainer getInstance(Context context) {
		if (mInstance == null)
			mInstance = new AnimationsContainer(context);
		return mInstance;
	}

	/**
	 * @param imageView
	 * @return progress dialog animation
	 */
	//    public FramesSequenceAnimation createProgressDialogAnim(ImageView imageView) {
	//        return new FramesSequenceAnimation(imageView, mProgressAnimFrames);
	//    }

	/**
	 * @param imageView
	 * @return splash screen animation
	 */
	//    public FramesSequenceAnimation createSplashAnim(ImageView imageView) {
	//        return new FramesSequenceAnimation(imageView, mSplashAnimFrames);
	//    }
	public FramesSequenceAnimation createAvatarAnimation(ImageView imageView, ArrayList<Visema> visemaList) {
		int[] frames = new int[visemaList.size()];
		for (int i = 0; i < visemaList.size(); i++) {
			frames[i] = visemaList.get(i).getId();
		}
		this.mVisemaList = visemaList;
		mBitmapList = new ArrayList<Bitmap>();
		for (int j = 0; j < 5; j++) {
			mBitmapList.add(BitmapFactory.decodeResource(context.getResources(), visemaList.get(j).getId()));
		}
		return new FramesSequenceAnimation(imageView, frames);
	}

	/**
	 * AnimationPlayer. Plays animation frames sequence in loop
	 */
	public class FramesSequenceAnimation {
		private int[] mFrames; // animation frames
		private int mIndex; // current frame
		private boolean mShouldRun; // true if the animation should continue running. Used to stop the animation
		private boolean mIsRunning; // true if the animation currently running. prevents starting the animation twice
		private SoftReference<ImageView> mSoftReferenceImageView; // Used to prevent holding ImageView when it should be dead.
		private Handler mHandler;

		public FramesSequenceAnimation(ImageView imageView, int[] frames) {
			mHandler = new Handler();
			mFrames = frames;
			mIndex = -1;
			mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
			mShouldRun = false;
			mIsRunning = false;
		}

		private int getNext() {
			if (mIndex == 0) {
				new ImageCacheTask().execute();
			}
			mIndex++;
			index = mIndex;
			if (mIndex >= mFrames.length) {
				mIndex = 0;
				mShouldRun = false;
			}
			new RecyclerTask().execute();
			return mFrames[mIndex];
		}

		//		public void recycleImages() {
		//			if (mIndex >= 2) {
		//				localIndex++;
		//				mBitmapList.get(mIndex - 2).recycle();
		//				if(localIndex == 10){
		//				System.gc();
		//				localIndex = 0;
		//				}
		//			}
		//		}

		/**
		 * Starts the animation
		 */
		public synchronized void start() {
			mShouldRun = true;
			if (mIsRunning)
				return;

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					ImageView imageView = mSoftReferenceImageView.get();
					if (!mShouldRun || imageView == null) {
						mIsRunning = false;
						return;
					}

					mIsRunning = true;
					if (mIndex < mBitmapList.size()-1) {
						getNext();
						System.out.println("mIndex" + mIndex);
						System.out.println("size " + mBitmapList.size());
						if (imageView.isShown())
							imageView.setImageBitmap(mBitmapList.get(mIndex));
						mHandler.postDelayed(this, mVisemaList.get(mIndex).getDelay() - 60);
					} else if(mIndex == mBitmapList.size()-1){
						imageView.setImageBitmap(mBitmapList.get(mIndex-1));
					}
					Log.i("DELAY", "" + mVisemaList.get(mIndex).getDelay());
				}
			};
			mHandler.post(runnable);
		}

		/**
		 * Stops the animation
		 */
		public synchronized void stop() {
			mShouldRun = false;
		}
	}

	public ArrayList<Visema> getmVisemaList() {
		return mVisemaList;
	}

	public void setmVisemaList(ArrayList<Visema> mVisemaList) {
		this.mVisemaList = mVisemaList;
	}

	public class ImageCacheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			int count = 0;
			for (int i = 5; i < mVisemaList.size(); i++) {
				mBitmapList.add(decodeSampledBitmapFromResource(context.getResources(), mVisemaList.get(i).getId(), 500, 500));
				count++;
				if (count == 3) {

				}
			}
			return null;
		}

	}

	public class RecyclerTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			if (index >= 1) {
				localIndex++;
				if (index < mBitmapList.size() - 1) {
					mBitmapList.get(index - 1).recycle();
				}
				if (localIndex == 1) {
					System.gc();
					localIndex = 0;
				}
			}
			return null;
		}

	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = true;
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		options.inPurgeable = true;
		options.inSampleSize = 8;
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
}
