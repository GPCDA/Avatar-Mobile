package com.example.avatarcertificacao.model;

import android.graphics.Bitmap;

public class Visema {
	private long delay;
	private Bitmap img;
	private String fileName;
	private int id;

	public Visema(long delay, Bitmap idImage) {
		super();
		this.delay = delay;
		this.img = idImage;
	}
	public Visema(long delay,String filename, int id) {
		super();
		this.delay = delay;
		this.id = id;
	}
	public Visema(long delay, String fileName,Bitmap img) {
		super();
		this.delay = delay;
		this.fileName = fileName;
		this.img = img;
	}
	
	public Visema(long delay, String fileName) {
		super();
		this.delay = delay;
		this.fileName = fileName;
	}
	
	public long getDelay() {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
