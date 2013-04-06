package com.example.avatarcertificacao.gui;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.util.SessionStore;
import com.example.avatarcertificacao.util.Util;

public class LoginScreen extends Activity implements OnClickListener {
	
	private Button btnLogin;
	private EditText moodleUrlEditText;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Toast toast;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        btnLogin = (Button)findViewById(R.idLoginScreen.login_btn);
        moodleUrlEditText = (EditText) findViewById(R.idLoginScreen.url_moodle_edit_text);
        usernameEditText = (EditText) findViewById(R.idLoginScreen.login_edit_text);
        passwordEditText = (EditText) findViewById(R.idLoginScreen.senha_edit_text);
        Date today = new Date();
        if (!SessionStore.getUserToken(this).isEmpty()) {
        	if (SessionStore.getExpirationData(this) > today.getTime()) {
				Intent intent = new Intent(LoginScreen.this, MainScreen.class);
				startActivity(intent);
				finish();
        	}
        }
        
		moodleUrlEditText.setText(SessionStore.getUrl(this));
		usernameEditText.setText(SessionStore.getUsername(this));
		passwordEditText.setText(SessionStore.getPassword(this));
		if (!(SessionStore.getUrl(this).isEmpty() || 
			SessionStore.getUsername(this).isEmpty() || 
			SessionStore.getPassword(this).isEmpty())) {
    		login();
		}
        
        btnLogin.setOnClickListener(this);
		toast = Toast.makeText(this, R.string.login_erro,Toast.LENGTH_LONG);

    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.idLoginScreen.login_btn:
				if (!moodleUrlEditText.getText().toString().isEmpty()) {
					login();
				} else {
					toast.show();
				}
				break;

			default:
				break;
		}
	}

	private void login() {
		if ((moodleUrlEditText.getTextSize() > 0) &&
			(usernameEditText.getTextSize() > 0) &&
			(passwordEditText.getTextSize() > 0)) {
			
			new AsyncTask<Void, Void, Void>() {
				
				String url;
				String username;
				String password;
				boolean loggedIn;
				
				@Override
				protected void onPreExecute() {
					showDialog();
					loggedIn = false;
					if (moodleUrlEditText.getText().toString().endsWith(getString(R.string.bar))) {
						url = moodleUrlEditText.getText().toString()+getString(R.string.WSUrl);
					} else {
						url = moodleUrlEditText.getText().toString()+getString(R.string.bar)+getString(R.string.WSUrl);
					}
					if (!url.startsWith("http://")) {
						url = "http://"+url;
					}
					username = usernameEditText.getText().toString();
					password = passwordEditText.getText().toString();
				}
				
				@Override
				protected Void doInBackground(Void... params) {
					
					String response = Util.login(url, username, password);
					JSONObject rootObject;
					try {
						rootObject = new JSONObject(response);
						if (rootObject.getString(getString(R.string.token)) != null) {
							loggedIn = true;
							SessionStore.save(LoginScreen.this, rootObject.getString(getString(R.string.token)), rootObject.getLong(getString(R.string.expiration)));
							SessionStore.save(LoginScreen.this, username, password, url);
						}
						//{"tk":"c8a5c5b3d969bbd44c479e8527f77fc5","exp":1364715508}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(Void param) {
					dismissDialog();
					if (loggedIn) {
						Intent intent = new Intent(LoginScreen.this, MainScreen.class);
						startActivity(intent);
						finish();
					} else {
						toast.show();
					}
				}
				
			}.execute();
			
		}
	}
	
	ProgressDialog progressDialog;

	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, getString(R.string.empty), getString(R.string.logging));
		} else {
			progressDialog.setTitle(getString(R.string.empty));
			progressDialog.setMessage(getString(R.string.logging));
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
    

}
