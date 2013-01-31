package com.example.avatarcertificacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CourseListActivity extends Activity implements OnClickListener, OnItemClickListener {

	ListView courseList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list_screen);

		Course[] data = new Course[3];
		data[0] = new Course("Curso 1", 1, 1);
		data[1] = new Course("Curso 2", 2, 2);
		data[2] = new Course("Curso 3", 3, 3);
		CourseListAdapter adapter = new CourseListAdapter(this, R.layout.course_list_item, data);

		courseList = (ListView) findViewById(R.idCourseListScreen.courseListView);
		courseList.setAdapter(adapter);
		courseList.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Implementar o click dos itens da lista
		Intent intent = new Intent(this, MediaPlayerActivity.class);
		startActivity(intent);

	}
}
