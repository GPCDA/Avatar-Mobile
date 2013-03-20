package com.example.avatarcertificacao.gui;


<<<<<<< HEAD
import com.example.avatarcertificacao.R;

import android.app.Activity;
import android.os.Bundle;
=======
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.avatarcertificacao.R;
import com.example.avatarcertificacao.util.SettingsListAdapter;
import com.example.avatarcertificacao.util.SettingsListItem;

public class SettingsScreen extends Activity implements OnItemClickListener {
	private static final int GERAL = 0;
	private static final int NOTIFICACOES = 1;

	Button btnCourses;
	Button btnSettings;

	ListView geralListView;
	ListView notificacoesListView;
>>>>>>> experimental

public class SettingsScreen extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);

	}


}
