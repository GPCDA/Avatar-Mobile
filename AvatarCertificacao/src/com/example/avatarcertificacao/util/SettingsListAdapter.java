package com.example.avatarcertificacao.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.util.CourseListAdapter.CourseHolder;

public class SettingsListAdapter extends ArrayAdapter<SettingsListItem> {

	Context context;
	int layoutResourceId;
	ArrayList<SettingsListItem> data;
	
	
	public SettingsListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}


	public SettingsListAdapter(Context context, int layoutResourceId, ArrayList<SettingsListItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		SettingsHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new SettingsHolder();
			
			holder.txtDescription = (TextView) row.findViewById(R.idSettingsListItem.descriptionTextView);
			holder.txtTitle = (TextView) row.findViewById(R.idSettingsListItem.titleTextView);
			holder.txtDescription.setText(data.get(position).getDescription());
			holder.txtTitle.setText(data.get(position).getTitle());
			
			row.setTag(holder);
		} else {
			holder = (SettingsHolder) row.getTag();
		}

		return row;
	}

	static class SettingsHolder {
		TextView txtTitle;
		TextView txtDescription;
	}
}
