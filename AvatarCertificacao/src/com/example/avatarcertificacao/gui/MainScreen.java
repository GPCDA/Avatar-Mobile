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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
			coursesMsgTextView.setText(R.string.mensagem_nova);
			coursesMsgTextView.setTypeface(null, Typeface.BOLD);
		} else {
			coursesMsgTextView.setText(R.string.mensagem);
			coursesMsgTextView.setTypeface(null, Typeface.NORMAL);
		}
		if (MessageController.getInstance(MainScreen.this).hasUnreadAdminMessages()) {
			adminMsgTextView.setText(R.string.mensagem_nova);
			adminMsgTextView.setTypeface(null, Typeface.BOLD);
		} else {
			adminMsgTextView.setText(R.string.mensagem);
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
			// showSettingsScreen();
			break;
		default:
			break;
		}

	}

	private void showCoursesListScreen() {
		Intent intent = new Intent(this, CourseListActivity.class);
		startActivity(intent);

	}
}
