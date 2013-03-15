package com.example.avatarcertificacao.gui;

import java.util.ArrayList;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);

		ArrayList<SettingsListItem> dataGeral = new ArrayList<SettingsListItem>();
		dataGeral = createSettingsListItens(dataGeral, GERAL);
		
		SettingsListAdapter geralAdapter = new SettingsListAdapter(this, R.layout.settings_list_item, dataGeral);
		geralListView = (ListView) findViewById(R.idSettingsScreen.geral_list_view);
		geralListView.setAdapter(geralAdapter);
		geralListView.setOnItemClickListener(this);
		
		ArrayList<SettingsListItem> dataNotificacoes = new ArrayList<SettingsListItem>();
		dataNotificacoes = createSettingsListItens(dataNotificacoes, NOTIFICACOES);
		
		SettingsListAdapter notificacoesAdapter = new SettingsListAdapter(this, R.layout.settings_list_item, dataNotificacoes);
		notificacoesListView = (ListView) findViewById(R.idSettingsScreen.notificacoes_list_view);
		notificacoesListView.setAdapter(notificacoesAdapter);
		notificacoesListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	private ArrayList<SettingsListItem> createSettingsListItens(ArrayList<SettingsListItem> data, int tipo) {
		if (data != null) {
			switch (tipo) {
				case GERAL:
					data.add(new SettingsListItem(getString(R.string.url_padrao), getString(R.string.url_subtitle)));
					data.add(new SettingsListItem(getString(R.string.usuario_padrao), getString(R.string.usuario_subtitle)));
					break;

				case NOTIFICACOES:
					data.add(new SettingsListItem(getString(R.string.novas_atividades), getString(R.string.novas_atividades_subtitle)));
					data.add(new SettingsListItem(getString(R.string.datas_de_atividades), getString(R.string.datas_de_atividades_sub)));
					data.add(new SettingsListItem(getString(R.string.mensagens_do_prof), getString(R.string.mensagens_do_prof_subtitle)));
					data.add(new SettingsListItem(getString(R.string.novos_materiais), getString(R.string.novos_materiais_subtitle)));
					data.add(new SettingsListItem(getString(R.string.atualizacao), getString(R.string.atualizacao_subtitle)));
					break;
			}
		}
		return data;
	}
}
