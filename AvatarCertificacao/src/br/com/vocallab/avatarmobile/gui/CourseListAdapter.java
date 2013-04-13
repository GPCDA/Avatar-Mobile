package br.com.vocallab.avatarmobile.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.vocallab.avatarmobile.model.Message;

import com.example.avatarcertificacao.R;

public class CourseListAdapter extends ArrayAdapter<Message> {

	Context context;
	int layoutResourceId;
	ArrayList<Message> data;
	
	public CourseListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public CourseListAdapter(Context context, int layoutResourceId, ArrayList<Message> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		View row = convertView;
		CourseHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			final Message message = data.get(position);
			holder = new CourseHolder();
			
			holder.txtCourse = (TextView) row.findViewById(R.id.courseTextView);
			holder.txtCourse.setText(message.getName());
			holder.newMessageTextView = (TextView) row.findViewById(R.id.newMessageTextView);
			if (message.isNewMessage() || message.isNewNotification()) {
				holder.newMessageTextView.setVisibility(View.VISIBLE);
				holder.newMessageTextView.setText(R.string.course_new_message);
			} else {
				holder.newMessageTextView.setVisibility(View.GONE);
			}
			row.setTag(holder);
		} else {
			holder = (CourseHolder) row.getTag();
		}

		return row;
	}

	static class CourseHolder {
		TextView txtCourse;
		TextView newMessageTextView;
		
	}
}
