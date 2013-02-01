package com.example.avatarcertificacao.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Visema {
	private float delay;
	private Bitmap img;
	private String fileName;

	public Visema(float delay, Bitmap idImage) {
		super();
		this.delay = delay;
		this.img = idImage;
	}
	
	public Visema(float delay, String fileName,Bitmap img) {
		super();
		this.delay = delay;
		this.fileName = fileName;
		this.img = img;
	}
	
	public float getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Bitmap getImage() {
		return img;
	}

	public void setIdImage(Bitmap idImage) {
		this.img = idImage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
