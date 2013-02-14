package com.example.avatarcertificacao.gui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.db.DatabaseHandler;
import com.example.avatarcertificacao.model.Course;
import com.example.avatarcertificacao.model.Message;
import com.example.avatarcertificacao.util.CourseListAdapter;

public class CourseListActivity extends Activity implements OnClickListener, OnItemClickListener {

	ListView courseList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list_screen);

		Course[] data = loadCourses();
		CourseListAdapter adapter = new CourseListAdapter(this, R.layout.course_list_item, data);

		courseList = (ListView) findViewById(R.idCourseListScreen.courseListView);
		courseList.setAdapter(adapter);
		courseList.setOnItemClickListener(this);

	}

	public Course[] loadCourses() {
		List<Message> msgList = MessageController.getInstance(this).getMessageList();
		Course[] data = new Course[msgList.size()];
		for (int i = 0; i < msgList.size(); i++) {
			data[i] = new Course(msgList.get(i).getName(), i + 1, i + 1);
		}
		return data;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Implementar o click dos itens da lista
		MessageController.getInstance(this).setSelectedMessage(MessageController.getInstance(this).getMessageList().get(position));
		Intent intent = new Intent(this, MediaPlayerActivity.class);
		startActivity(intent);

	}
}
