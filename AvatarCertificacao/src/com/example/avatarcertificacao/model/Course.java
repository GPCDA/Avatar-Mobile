package com.example.avatarcertificacao.model;

public class Course {
	private String course;
	private int mensagens;
	private int avisos;

	public Course(String course, int mensagens, int avisos) {
		super();
		this.course = course;
		this.mensagens = mensagens;
		this.avisos = avisos;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getMensagens() {
		return mensagens;
	}

	public void setMensagens(int mensagens) {
		this.mensagens = mensagens;
	}

	public int getAvisos() {
		return avisos;
	}

	public void setAvisos(int avisos) {
		this.avisos = avisos;
	}

}
