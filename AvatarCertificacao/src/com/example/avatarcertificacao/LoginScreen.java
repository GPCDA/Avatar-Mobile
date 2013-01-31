package com.example.avatarcertificacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginScreen extends Activity implements OnClickListener {
	
	private Button btnLogin;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        btnLogin = (Button)findViewById(R.idLoginScreen.login_btn);
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
		Intent intent = new Intent(this, MainScreen.class);
		startActivity(intent);
	}
    

}
