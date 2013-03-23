package com.example.avatarcertificacao.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.data.MessageController;
import com.example.avatarcertificacao.util.SessionStore;

public class CourseListActivity extends Activity implements OnItemClickListener {

	ListView courseList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list_screen);

		CourseListAdapter adapter = new CourseListAdapter(this, R.layout.course_list_item, MessageController.getInstance(this).getCourseMessageList());
		
		courseList = (ListView) findViewById(R.idCourseListScreen.courseListView);
		courseList.setAdapter(adapter);
		courseList.setOnItemClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg) {
		Intent intent = new Intent(this, MediaPlayerActivity.class);
		Bundle b = new Bundle();
		b.putInt("id", MessageController.getInstance(this).getCourseMessageList().get(position).getId());
		intent.putExtra("message.details", b);
		parent.getContext().startActivity(intent);	
		
	}
}
