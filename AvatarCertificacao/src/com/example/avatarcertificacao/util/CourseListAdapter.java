package com.example.avatarcertificacao.util;

import java.util.ArrayList;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.gui.MediaPlayerActivity;
import com.example.avatarcertificacao.model.Message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseListAdapter extends ArrayAdapter<Message> {

	public CourseListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	Context context;
	int layoutResourceId;
	ArrayList<Message> data;

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
			
			holder.messageLayoutContent = (LinearLayout) row.findViewById(R.id.messageLayoutContent);
			holder.warningLayoutContent = (LinearLayout) row.findViewById(R.id.warningLayoutContent);
			holder.txtCourse = (TextView) row.findViewById(R.id.courseTextView);
			holder.txtCourse.setText(message.getName());
			
			if (message.getMsgAudio().isEmpty()) {
				holder.messageLayoutContent.setVisibility(View.GONE);
			} else {
				holder.messageLayoutContent.setVisibility(View.VISIBLE);
				holder.messageLayoutContent.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(parent.getContext(), MediaPlayerActivity.class);
						Bundle b = new Bundle();
						b.putInt("id", message.getId());
						b.putInt("type", 0);
						intent.putExtra("message.details", b);
						parent.getContext().startActivity(intent);	
					}
				});
			}
			
			if (message.getNotifAudio().isEmpty()) {
				holder.warningLayoutContent.setVisibility(View.GONE);
			} else {
				holder.warningLayoutContent.setVisibility(View.VISIBLE);
				holder.warningLayoutContent.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(parent.getContext(), MediaPlayerActivity.class);
						Bundle b = new Bundle();
						b.putInt("id", message.getId());
						b.putInt("type", 1);
						intent.putExtra("message.details", b);
						parent.getContext().startActivity(intent);	
						
					}
				});
			}
			row.setTag(holder);
		} else {
			holder = (CourseHolder) row.getTag();
		}

		return row;
	}

	static class CourseHolder {
		TextView txtCourse;
		LinearLayout messageLayoutContent;
		LinearLayout warningLayoutContent;
		
	}
}
