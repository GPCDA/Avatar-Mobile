package com.example.avatarcertificacao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseListAdapter extends ArrayAdapter<Course> {

	public CourseListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	Context context;
	int layoutResourceId;
	Course[] data;

	public CourseListAdapter(Context context, int layoutResourceId, Course[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CourseHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new CourseHolder();
			
			holder.txtAvisos = (TextView) row.findViewById(R.id.avisosTextView);
			holder.txtCourse = (TextView) row.findViewById(R.id.courseTextView);
			holder.txtMensagens = (TextView) row.findViewById(R.id.mensagensTextView);
			
			holder.txtAvisos.setText("avisos("+data[position].getAvisos()+")");
			holder.txtMensagens.setText("mensagens("+data[position].getMensagens()+")");
			
			holder.txtCourse.setText(""+data[position].getCourse());
			
			row.setTag(holder);
		} else {
			holder = (CourseHolder) row.getTag();
		}

		return row;
	}

	static class CourseHolder {
		TextView txtCourse;
		TextView txtMensagens;
		TextView txtAvisos;
	}
}
