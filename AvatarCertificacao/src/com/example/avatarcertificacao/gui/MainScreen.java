package com.example.avatarcertificacao.gui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.model.Message;
import com.example.avatarcertificacao.util.SessionStore;
import com.example.avatarcertificacao.util.Util;

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
		
		loadMessages();

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
			progressDialog = ProgressDialog.show(this,
					getString(R.string.empty), getString(R.string.loading));
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
//		case R.optionMenu.settings:
//			intent = new Intent(this, SettingsScreen.class);
//			startActivity(intent);
//			break;
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
	
	   
}
