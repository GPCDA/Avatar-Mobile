package br.com.vocallab.avatarmobile.gui;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import br.com.vocallab.avatarmobile.R;

public class SplashScreen extends Activity implements OnClickListener{
    
	Timer timer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.splash_screen);

		((LinearLayout) findViewById(R.splash.splashScreen)).setOnClickListener(this);
		
		long timeout = 3000;
		TimerTask task = new TimerTask() {
	
			@Override
			public void run() {
				startActivity(new Intent(SplashScreen.this, LoginScreen.class));
				finish();
			}
		};
		timer = new Timer();
		timer.schedule(task, timeout);

	}

	@Override
	public void onClick(View v) {
		timer.cancel();
		startActivity(new Intent(SplashScreen.this, LoginScreen.class));
		finish();
	}
	 
}