package com.example.avatarcertificacao.gui;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.R.idLoginScreen;
import com.example.avatarcertificacao.R.layout;
import com.example.avatarcertificacao.R.menu;
import com.example.avatarcertificacao.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends Activity implements OnClickListener {
	
	private Button btnLogin;
	private EditText moodleUrlEditText;
	private EditText usernameEditText;
	private EditText passwordEditText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        btnLogin = (Button)findViewById(R.idLoginScreen.login_btn);
        moodleUrlEditText = (EditText) findViewById(R.idLoginScreen.url_moodle_edit_text);
        usernameEditText = (EditText) findViewById(R.idLoginScreen.login_edit_text);
        passwordEditText = (EditText) findViewById(R.idLoginScreen.senha_edit_text);
        
        btnLogin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.idLoginScreen.login_btn:
				login();
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
				
				@Override
				protected void onPreExecute() {
					showDialog();
					if (moodleUrlEditText.getText().toString().endsWith(getString(R.string.bar))) {
						url = moodleUrlEditText.getText().toString()+getString(R.string.WSUrl);
					} else {
						url = moodleUrlEditText.getText().toString()+getString(R.string.bar)+getString(R.string.WSUrl);
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
						//{"tk":"c8a5c5b3d969bbd44c479e8527f77fc5","exp":1364715508}
//						acessToken = rootObject.getString(getString(R.string.token));
//						expiration = rootObject.getLong(getString(R.string.expiration));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(Void param) {
					dismissDialog();
					Intent intent = new Intent(LoginScreen.this, MainScreen.class);
					startActivity(intent);
					
				}
 
				
			}.execute();
			
			
		}
		
		
//		Intent intent = new Intent(this, MainScreen.class);
//		startActivity(intent);
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
