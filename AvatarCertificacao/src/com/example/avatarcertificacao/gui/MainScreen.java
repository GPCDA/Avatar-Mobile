package com.example.avatarcertificacao.gui;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.R.idMainScreen;
import com.example.avatarcertificacao.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity implements OnClickListener {
	Button btnCourses;
	Button btnSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		btnCourses = (Button) findViewById(R.idMainScreen.btn_courses);
		btnCourses.setOnClickListener(this);

		btnSettings = (Button) findViewById(R.idMainScreen.btn_settings);
		btnSettings.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.idMainScreen.btn_courses:
				showCoursesListScreen();
				break;
			case R.idMainScreen.btn_settings:
				showSettingsScreen();
				break;
			default:
				break;
		}

	}

	private void showSettingsScreen() {
		Intent intent = new Intent(this, SettingsScreen.class);
		startActivity(intent);

	}

	private void showCoursesListScreen() {
		Intent intent = new Intent(this, CourseListActivity.class);
		startActivity(intent);

	}
}
