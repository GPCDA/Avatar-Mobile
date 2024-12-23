package br.com.vocallab.avatarmobile.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.vocallab.avatarmobile.R;
import br.com.vocallab.avatarmobile.data.MessageController;
import br.com.vocallab.avatarmobile.model.Message;
import br.com.vocallab.avatarmobile.service.NotificationService;
import br.com.vocallab.avatarmobile.util.SessionStore;
import br.com.vocallab.avatarmobile.util.Util;

public class MainScreen extends Activity implements OnClickListener {
	LinearLayout btnCourses;
	LinearLayout btnAdmin;
	TextView coursesMsgTextView;
	TextView adminMsgTextView;
	ArrayList<Message> msgList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		btnCourses = (LinearLayout) findViewById(R.idMainScreen.btn_courses);
		btnCourses.setOnClickListener(this);

		btnAdmin = (LinearLayout) findViewById(R.idMainScreen.btn_admin);
		btnAdmin.setOnClickListener(this);

		coursesMsgTextView = (TextView) findViewById(R.idMainScreen.courseMessagetextView);
		adminMsgTextView = (TextView) findViewById(R.idMainScreen.admMessagetextView);

		if (Util.isNetworkAvailable(this)) {
			//loadMessages();
		}

	}

	private void loadMessages() {

		new AsyncTask<Void, Void, Void>() {

			String url;
			String token;

			@Override
			protected void onPreExecute() {
				showDialog();
				url = SessionStore.getUrl(MainScreen.this);
				token = SessionStore.getUserToken(MainScreen.this);

				if (url.endsWith(getString(R.string.bar))) {
					url = url + getString(R.string.WSUrl);
				} else {
					url = url + getString(R.string.bar) + getString(R.string.WSUrl);
				}
			}

			@Override
			protected Void doInBackground(Void... params) {

				MessageController.getInstance(MainScreen.this).saveOnDB(Util.loadMessages(url, token));
				return null;
			}

			@Override
			protected void onPostExecute(Void param) {
				updateMessageStatus();
				dismissDialog();
			}

		}.execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
		updateMessageStatus();
	}

	private void updateMessageStatus() {

		if (MessageController.getInstance(MainScreen.this).hasUnreadCourseMessages()) {
			coursesMsgTextView.setText(R.string.course_new_message);
			coursesMsgTextView.setTypeface(null, Typeface.BOLD);
		} else if (MessageController.getInstance(MainScreen.this).hasCourseMessages()) {
			coursesMsgTextView.setText(R.string.course_message);
			coursesMsgTextView.setTypeface(null, Typeface.NORMAL);
		} else {
			coursesMsgTextView.setText(R.string.course_no_message);
			coursesMsgTextView.setTypeface(null, Typeface.NORMAL);
		}

		if (MessageController.getInstance(MainScreen.this).hasUnreadAdminMessages()) {
			adminMsgTextView.setText(R.string.adm_new_message);
			adminMsgTextView.setTypeface(null, Typeface.BOLD);
		} else if (MessageController.getInstance(MainScreen.this).hasAdmMessages()) {
			adminMsgTextView.setText(R.string.adm_message);
			adminMsgTextView.setTypeface(null, Typeface.NORMAL);
		} else {
			adminMsgTextView.setText(R.string.adm_no_message);
			adminMsgTextView.setTypeface(null, Typeface.NORMAL);
		}

	}

	ProgressDialog progressDialog;

	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, getString(R.string.empty), getString(R.string.loading));
		} else {
			progressDialog.setTitle(getString(R.string.empty));
			progressDialog.setMessage(getString(R.string.loading));
			if (!progressDialog.isShowing()) {
				progressDialog.show();
			}
		}
	}

	public void dismissDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.idMainScreen.btn_courses:
				showCoursesListScreen();
				break;
			case R.idMainScreen.btn_admin:
				loadAdminMessage();
				break;
			default:
				break;
		}

	}

	private void loadAdminMessage() {
		Message message = MessageController.getInstance(this).getMessage(0);
		if (!message.getMsgAudio().isEmpty()) {
			Intent intent = new Intent(this, MediaPlayerActivity.class);
			Bundle b = new Bundle();
			b.putInt("id", message.getId());
			b.putInt("type", 0);
			intent.putExtra("message.details", b);
			startActivity(intent);
		} else {
			Toast.makeText(this, R.string.no_messages, Toast.LENGTH_LONG).show();
		}

	}

	private void showCoursesListScreen() {
		Intent intent = new Intent(this, CourseListActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
			case R.optionMenu.settings:
				showSettingsOptions();
				break;
			case R.optionMenu.logout:
				if (SessionStore.logout(this)) {
					intent = new Intent(this, LoginScreen.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(this, R.string.logout_problem, Toast.LENGTH_LONG).show();
				}

				break;

			default:
				break;
		}
		return false;

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	public void showSettingsOptions() {
		int lastSelectedItem = getIntervalFromPreferences();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.pick_interval)).setCancelable(true)
				.setSingleChoiceItems(R.array.syncFrequency, lastSelectedItem, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainScreen.this.getApplicationContext());
						Editor editor = prefs.edit();

						String[] items = getResources().getStringArray(R.array.syncFrequency);
						String value = items[which];
						int interval = 0;
						if (!value.equals("Nunca")) {
							interval = Integer.valueOf(value.replace(" Minutos", ""));
						} else {
							interval = 0;
						}

						editor.putInt("interval", interval);
						editor.putInt("optionIndex", which);
						editor.commit();
						Util.startService(MainScreen.this);
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private int getIntervalFromPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainScreen.this.getApplicationContext());
		return prefs.getInt("optionIndex", 0);

	}
}
