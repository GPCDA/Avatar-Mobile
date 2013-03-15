package com.example.avatarcertificacao.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.util.CourseListAdapter;

public class CourseListActivity extends Activity {

	ListView courseList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list_screen);

		CourseListAdapter adapter = new CourseListAdapter(this, R.layout.course_list_item, MessageController.getInstance(this).getCourseMessageList());

		courseList = (ListView) findViewById(R.idCourseListScreen.courseListView);
		courseList.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
