package com.example.avatarcertificacao.model;

import android.content.Context;

public class Message {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String pf;
	private int avt;
	private int msgu;
	private int notu;
	private int msgn;
	private int notn;
	private String msga;
	private String nota;
	private String msgv;
	private String notv;

	public Message(int id, String name, String perfil, int avatarId, int isMsgUpdate, int isNotifUpdate, String msgAudio,
			String notifAudio, String msgVisema, String notifVisema, int msgn, int notn) {
		super();
		this.id = id;
		this.name = name;
		this.pf = perfil;
		this.avt = avatarId;
		this.msgu = isMsgUpdate;
		this.notu = isNotifUpdate;
		this.msga = msgAudio;
		this.nota = notifAudio;
		this.msgv = msgVisema;
		this.notv = notifVisema;
		this.notn = notn;
		this.msgn = msgn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerfil() {
		return pf;
	}

	public void setPerfil(String perfil) {
		this.pf = perfil;
	}

	public int getAvatarId() {
		return avt;
	}

	public void setAvatarId(int avatarId) {
		this.avt = avatarId;
	}

	public boolean isMsgUpdate() {
		return msgu == 1;
	}

	public void setMsgUpdate(int isMsgUpdate) {
		this.msgu = isMsgUpdate;
	}

	public boolean isNotifUpdate() {
		return notu == 1;
	}

	public void setNotifUpdate(int isNotifUpdate) {
		this.notu = isNotifUpdate;
	}

	public Message() {
		super();
	}

	public String getMsgAudio() {
		return msga;
	}

	public void setMsgAudio(String msgAudio) {
		this.msga = msgAudio;
	}

	public String getNotifAudio() {
		return nota;
	}

	public void setNotifWate(String notifAudio) {
		this.nota = notifAudio;
	}

	public String getMsgVisema() {
		return msgv;
	}

	public void setMsgVisema(String msgVisema) {
		this.msgv = msgVisema;
	}

	public String getNotifVisema() {
		return notv;
	}

	public void setNotifVisema(String notifVisema) {
		this.notv = notifVisema;
	}

	public int getMsgn() {
		return msgn;
	}

	public void setMsgn(int msgn) {
		this.msgn = msgn;
	}

	public int getNotn() {
		return notn;
	}

	public void setNotn(int notn) {
		this.notn = notn;
	}

}
