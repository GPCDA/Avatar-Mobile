package br.com.vocallab.avatarmobile.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import br.com.vocallab.avatarmobile.R;
import br.com.vocallab.avatarmobile.data.MessageController;
import br.com.vocallab.avatarmobile.gui.LoginScreen;
import br.com.vocallab.avatarmobile.gui.MainScreen;
import br.com.vocallab.avatarmobile.util.SessionStore;
import br.com.vocallab.avatarmobile.util.Util;

public class NotificationService extends Service {

	private WakeLock mWakeLock;

	/**
	 * Simply return null, since our Service will not be communicating with any
	 * other components. It just does its work silently.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * This is where we initialize. We call this when onStart/onStartCommand is
	 * called by the system. We won't do anything with the intent here, and you
	 * probably won't, either.
	 */
	private void handleIntent(Intent intent) {
		// obtain the wake lock
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wake_lock");
		mWakeLock.acquire();

		// check the global background data setting
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (!cm.getBackgroundDataSetting()) {
			stopSelf();
			return;
		}

		// do the actual work, in a separate thread
		new CheckNewMessagesTask().execute();
	}

	private class CheckNewMessagesTask extends AsyncTask<Void, Void, Void> {
		String url;
		String token;
		boolean existNewMessages = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			url = SessionStore.getUrl(NotificationService.this);
			token = SessionStore.getUserToken(NotificationService.this);

			if (url.endsWith(getString(R.string.bar))) {
				url = url + getString(R.string.WSUrl);
			} else {
				url = url + getString(R.string.bar) + getString(R.string.WSUrl);
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (!token.equals("")) {
					MessageController.getInstance(NotificationService.this).saveOnDB(Util.loadMessages(url, token));
					
					existNewMessages = MessageController.getInstance(NotificationService.this).existNewMessage();
					
				} else {
					this.cancel(true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			NotificationService.this.stopSelf();
		}

		@Override
		protected void onPostExecute(Void result) {
			// handle your data
			
			Log.i("MENSAGENS", "NEW MESSAGES" + existNewMessages);
			
			if (existNewMessages) {
				showNotification();
			}
			stopSelf();
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		handleIntent(intent);
	}

	/**
	 * This is called on 2.0+ (API level 5 or higher). Returning
	 * START_NOT_STICKY tells the system to not restart the service if it is
	 * killed because of poor resource (memory/cpu) conditions.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handleIntent(intent);
		return START_NOT_STICKY;
	}

	/**
	 * In onDestroy() we release our wake lock. This ensures that whenever the
	 * Service stops (killed for resources, stopSelf() called, etc.), the wake
	 * lock will be released.
	 */
	public void onDestroy() {
		super.onDestroy();
		mWakeLock.release();
	}

	private void showNotification() {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// In this sample, we'll use the same text for the ticker and the expanded notification
		CharSequence text = getText(R.string.course_new_message);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		Intent intent = new Intent(this, LoginScreen.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// The PendingIntent to launch our activity if the user selects this notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, getText(R.string.app_name), text, contentIntent);

		// Send the notification.
		// We use a layout id because it is a unique number.  We use it later to cancel.
		nm.notify(R.string.course_new_message, notification);

	}

}