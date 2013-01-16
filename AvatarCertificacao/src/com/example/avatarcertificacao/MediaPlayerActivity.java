package com.example.avatarcertificacao;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPlayerActivity extends Activity implements OnClickListener {

	MyAnimationView animationView;
	MediaPlayer mp;
	Button btnPlayPause;
	Button btnStop;
	Button btnPrevious;
	Button btnNext;
	ArrayList<Lexema> mList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setContentView(R.layout.activity_main);

		animationView = (MyAnimationView) findViewById(R.id.anim_view);
		animationView.loadAnimation("shark", 16);

		btnPlayPause = (Button) findViewById(R.id.play_pause_button);
		btnPlayPause.setOnClickListener(this);

		btnNext = (Button) findViewById(R.id.next_button);
		btnNext.setOnClickListener(this);

		btnPrevious = (Button) findViewById(R.id.prev_button);
		btnPrevious.setOnClickListener(this);

		btnStop = (Button) findViewById(R.id.stop_button);
		btnStop.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.play_pause_button:
				playMedia();
				break;
			case R.id.stop_button:
				stopMedia();
				break;
			case R.id.prev_button:
				//TODO item anterior da lista
				break;
			case R.id.next_button:
				//TODO proximo item da lista
				break;
		}
	}

	private void stopMedia() {
		this.animationView.stopAnimation();
		this.stopSound();
	}

	private void playMedia() {
		this.animationView.playAnimation();
		this.playSound();
	}

	private void playSound() {
		mp = MediaPlayer.create(this, R.raw.teste_masculino);
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
		    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
		    AudioManager.FLAG_PLAY_SOUND);
		//mp.setVolume(currentVolume, currentVolume);
		mp.start();
	}

	private void stopSound() {
		mp.stop();
	}	

}
