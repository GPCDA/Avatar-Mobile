package com.example.avatarcertificacao.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.avatarcertificacao.db.DatabaseHandler;
import com.example.avatarcertificacao.model.Message;
import com.example.avatarcertificacao.util.Util;
import com.google.gson.Gson;

public class MessageController {
	private static MessageController instance;
	private Context ctx;
	private List<Message> msgList;
	private DatabaseHandler db;
	private Message selectedMsg;

	public static MessageController getInstance(Context ctx) {

		if (instance == null) {
			instance = new MessageController(ctx.getApplicationContext());
		}
		return instance;
	}

	private MessageController(Context ctx) {
		this.ctx = ctx;
		loadMessages();
		this.db = new DatabaseHandler(ctx);
	}

	public void loadMessages() {
		if (db == null) {
			db = new DatabaseHandler(ctx);
		}
		this.msgList = db.getAllMessages();
//		if (this.msgList == null || this.msgList.isEmpty()) {
//			this.msgList = this.downloadMessages();
//			this.saveOnDB();
//		}
	}

	private ArrayList<Message> downloadMessages() {

		ArrayList<Message> messageList = null;
		try {
			Gson gson = new Gson();
			try {
				messageList = new ArrayList<Message>();

				String fileString = Util.readfile("json.txt", ctx);
				JSONObject json = new JSONObject(fileString);

				JSONArray jArray = json.getJSONArray("content");

				for (int i = 0; i < jArray.length(); i++) {
					String jsonString = jArray.getJSONObject(i).toString();
					Message msg = gson.fromJson(jsonString, Message.class);
					messageList.add(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//gson.fromJson(json, classOfT)

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageList;
	}

	public void saveOnDB(String messageJson) {
		ArrayList<Message> messageList = null;
//		Gson gson = new Gson();
		try {
			if (!messageJson.isEmpty()) {
				messageList = new ArrayList<Message>();
	
				JSONObject json = new JSONObject(messageJson);
	
				JSONArray jArray = json.getJSONArray("content");
	
				for (int i = 0; i < jArray.length(); i++) {
					Message msg =  new Message(jArray.getJSONObject(i));
					messageList.add(msg);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		msgList = messageList;
		for (Message current : msgList) {
			db.addMessage(current);
		}
	}
	
	private void saveOnDB() {
		for (Message current : msgList) {
			db.addMessage(current);
		}
	}

	public List<Message> getMessageList() {
		return this.msgList;
	}

	public int countUnredMsgs() {
		int count = 0;
		for (Message message : this.msgList) {
			if (message.isMsgUpdate()) {
				count++;
			}
		}
		return count;
	}

	public int countUnredNotif() {
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

	public boolean hasUnreadCourseMessages() {
		for (Message message : msgList) {
			if ((message.isMsgUpdate() || message.isNotifUpdate()) && !message.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasUnreadAdminMessages() {
		for (Message message : msgList) {
			if ((message.isMsgUpdate() || message.isNotifUpdate()) && message.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Message> getCourseMessageList() {
		ArrayList<Message> courseMessages = new ArrayList<Message>();
		for (Message message : msgList) {
			if (!message.isAdmin()) {
				courseMessages.add(message);
			}
		}
		return courseMessages;
	}

	public Message getMessage(int id) {
		for (Message message : msgList) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}
}
