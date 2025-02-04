package br.com.vocallab.avatarmobile.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import br.com.vocallab.avatarmobile.db.DatabaseHandler;
import br.com.vocallab.avatarmobile.model.Message;

public class MessageController {
	private static MessageController instance = null;
	private Context ctx;
	private List<Message> msgList;
	private DatabaseHandler db;
	private Message selectedMsg;

	public static MessageController getInstance(Context ctx) {

		if (instance == null) {
			instance = new MessageController(ctx);
		}
		return instance;
	}

	private MessageController(Context ctx) {
		this.ctx = ctx;
		loadMessages();
	}

	public void loadMessages() {
		if (db == null) {
			db = new DatabaseHandler(ctx);
		}
		this.msgList = db.getAllMessages();
	}

	public void saveOnDB(String messageJson) {
		ArrayList<Message> messageList = null;
		try {
			if (!messageJson.isEmpty()) {
				messageList = new ArrayList<Message>();

				JSONObject json = new JSONObject(messageJson);

				JSONArray jArray = json.getJSONArray("content");

				for (int i = 0; i < jArray.length(); i++) {
					Message msg = new Message(jArray.getJSONObject(i));
					messageList.add(msg);
				}
				this.msgList = messageList;
				for (Message current : this.msgList) {

					db.addMessage(current);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean existNewMessage() {

		for (Message current : this.msgList) {
			if (current.msgn == 1 || current.notn == 1) {
				return true;
			}
		}

		return false;
	}

	public List<Message> getMessageList() {
		//loadMessages();
		return this.msgList;
	}

	public int countUnredMsgs() {
		//loadMessages();
		int count = 0;
		for (Message message : this.msgList) {
			if (message.isMsgUpdate()) {
				count++;
			}
		}
		return count;
	}

	public int countUnredNotif() {
		//loadMessages();
		int count = 0;
		for (Message message : this.msgList) {
			if (message.isNotifUpdate()) {
				count++;
			}
		}
		return count;
	}

	public void setSelectedMessage(Message msg) {
		this.selectedMsg = msg;
	}

	public Message getSelectedMessage() {
		return this.selectedMsg;
	}

	public boolean hasCourseMessages() {
		//loadMessages();
		for (Message message : this.msgList) {
			if (!message.isAdmin() && (!message.getMsgAudio().isEmpty() || !message.getNotifAudio().isEmpty())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAdmMessages() {
		//loadMessages();
		for (Message message : this.msgList) {
			if (message.isAdmin() && !message.getMsgAudio().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasUnreadCourseMessages() {
		//loadMessages();
		for (Message message : this.msgList) {
			if ((message.isNewMessage() || message.isNewNotification()) && !message.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasUnreadAdminMessages() {
		//loadMessages();
		for (Message message : this.msgList) {
			if ((message.isNewMessage() || message.isNewNotification()) && message.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Message> getCourseMessageList() {
		//loadMessages();
		ArrayList<Message> courseMessages = new ArrayList<Message>();
		for (Message message : this.msgList) {
			if (!message.isAdmin()) {
				courseMessages.add(message);
			}
		}
		return courseMessages;
	}

	public Message getMessage(int id) {
		//loadMessages();
		for (Message message : this.msgList) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}
}
