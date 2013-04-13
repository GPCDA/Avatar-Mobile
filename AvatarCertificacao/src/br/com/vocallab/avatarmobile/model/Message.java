package br.com.vocallab.avatarmobile.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

	public static final int MESSAGE = 0;
	public static final int NOTIFICATION = 1;

	public int id;
	public String name;
	public String pf;
	public String avt;
	public int msgu;
	public int notu;
	public int msgn;
	public int notn;
	public String msga;
	public String nota;
	public String msgv;
	public String notv;

	public Message(JSONObject obj) {
		super();
		try {
			if (obj.getString("id") == "null") {
				this.id = 0;
			} else {
				this.id = obj.getInt("id");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			this.name = obj.getString("name");
			this.pf = obj.getString("pf");
			this.avt = obj.getString("avt");
			this.msgu = obj.getInt("msgu");
			this.notu = obj.getInt("notu");
			this.msga = obj.getString("msga");
			this.nota = obj.getString("nota");
			this.msgv = obj.getString("msgv");
			this.notv = obj.getString("notv");
			this.notn = obj.getInt("notn");
			this.msgn = obj.getInt("msgn");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Message(int id, String name, String perfil, String avatarId, int isMsgUpdate, int isNotifUpdate, String msgAudio,
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

	public String getAvatarId() {
		return avt;
	}

	public void setAvatarId(String avatarId) {
		this.avt = avatarId;
	}

	public boolean isMsgUpdate() {
		return msgu == 1;
	}
	
	public boolean isNewMessage() {
		return msgn == 1;
	}
	public boolean isNewNotification() {
		return notn == 1;
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

//	public Message(JSONObject jsonObject, List<Message> oldMessageList) {
//		this(jsonObject);
//		Message oldMsg = null;
//		for (Message message : oldMessageList) {
//			if (message.getId() == this.id) {
//				oldMsg = message;
//			}
//		}
//		if (oldMsg != null) {
//			if ((this.msgn == 0) && (this.msgu == 0)) {
//				this.msga = oldMsg.msga;
//				this.msgv = oldMsg.msgv;
//			}
//			if ((this.notn == 0) && (this.notu == 0)) {
//				this.nota = oldMsg.nota;
//				this.notv = oldMsg.notv;
//			}
//		}
//	}

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

	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return (id==0);
	}

}
