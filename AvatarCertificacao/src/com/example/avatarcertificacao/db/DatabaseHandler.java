package com.example.avatarcertificacao.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.avatarcertificacao.model.Message;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "avatarMobile";

	// Messages table name
	private static final String TABLE_MESSAGES = "messages";

	// Messages Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PERFIL = "perfil";
	private static final String KEY_AVATAR = "avatar";
	private static final String KEY_IS_MSG_UPDATE = "is_msg_update";
	private static final String KEY_MSG_AUDIO = "msg_audio";
	private static final String KEY_MSG_VISEMA = "msg_visema";
	private static final String KEY_IS_NOTIF_UPDATE = "is_notif_update";
	private static final String KEY_NOTIF_AUDIO = "notif_audio";
	private static final String KEY_NOTIF_VISEMA = "notif_visema";
	private static final String KEY_MSG_N = "msg_n";
	private static final String KEY_NOTIF_N = "notif_n";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
				+ KEY_PERFIL + " TEXT, " + KEY_AVATAR + " INTEGER, " + KEY_IS_MSG_UPDATE + " INTEGER, " + KEY_MSG_AUDIO + " TEXT, "
				+ KEY_MSG_VISEMA + " TEXT, " + KEY_IS_NOTIF_UPDATE + " INTEGER, " + KEY_NOTIF_N + " INTEGER, " + KEY_MSG_N + " INTEGER, "
				+ KEY_NOTIF_AUDIO + " TEXT, " + KEY_NOTIF_VISEMA + " TEXT " + ")";

		db.execSQL(CREATE_MESSAGES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addMessage(Message msg) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, msg.getId()); // message Id
		values.put(KEY_NAME, msg.getName()); // message Name
		values.put(KEY_AVATAR, msg.getAvatarId()); // message Avatar
		values.put(KEY_IS_MSG_UPDATE, msg.isMsgUpdate()); // message msgu
		values.put(KEY_IS_NOTIF_UPDATE, msg.isNotifUpdate()); // message notu
		values.put(KEY_MSG_VISEMA, msg.getMsgVisema()); // message msgv
		values.put(KEY_MSG_AUDIO, msg.getMsgAudio()); // message msga
		values.put(KEY_NOTIF_VISEMA, msg.getNotifVisema()); // message notv
		values.put(KEY_NOTIF_AUDIO, msg.getNotifAudio()); // message nota
		values.put(KEY_PERFIL, msg.getPerfil()); // message pf
		values.put(KEY_NOTIF_N, msg.getNotn()); // message notn
		values.put(KEY_MSG_N, msg.getMsgn()); // message msgn

		// Inserting Row
		db.insert(TABLE_MESSAGES, null, values);
		db.close(); // Closing database connection
	}

	// Getting single message
	Message getMessage(int mId) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MESSAGES, new String[] { KEY_ID, KEY_NAME, KEY_AVATAR, KEY_IS_MSG_UPDATE, KEY_IS_NOTIF_UPDATE,
				KEY_MSG_VISEMA, KEY_MSG_AUDIO, KEY_NOTIF_VISEMA, KEY_NOTIF_AUDIO, KEY_PERFIL, KEY_MSG_N, KEY_NOTIF_N }, KEY_ID + "=?",
				new String[] { String.valueOf(mId) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		int id = cursor.getInt(0);
		String name = cursor.getString(1);
		int avatar = cursor.getInt(2);
		int isMsgUpdate = cursor.getInt(3);
		int isNotifUpdate = cursor.getInt(4);
		String msgVisema = cursor.getString(5);
		String msgAudio = cursor.getString(6);
		String notifVisema = cursor.getString(7);
		String notifAudio = cursor.getString(8);
		String perfil = cursor.getString(9);
		int msgn = cursor.getInt(10);
		int notn = cursor.getInt(11);

		Message msg = new Message(id, name, perfil, avatar, isMsgUpdate, isNotifUpdate, msgAudio, notifAudio, msgVisema, notifVisema,msgn,notn);

		if (db.isOpen()) {
			db.close();
		}

		// return message
		return msg;
	}

	// Getting All Messages
	public List<Message> getAllMessages() {
		List<Message> messageList = new ArrayList<Message>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Message msg = new Message();
				msg.setId(Integer.parseInt(cursor.getString(0)));
				msg.setName(cursor.getString(1));
				msg.setPerfil(cursor.getString(2));
				msg.setAvatarId(cursor.getInt(3));
				msg.setMsgUpdate(cursor.getInt(4));
				msg.setMsgAudio(cursor.getString(5));
				msg.setMsgVisema(cursor.getString(6));
				msg.setNotifUpdate(cursor.getInt(7));
				msg.setNotifWate(cursor.getString(8));
				msg.setNotifVisema(cursor.getString(9));

				// Adding msg to list
				messageList.add(msg);
			} while (cursor.moveToNext());
		}

		if (db.isOpen()) {
			db.close();
		}
		// return contact list
		return messageList;
	}

	//	// Updating single contact
	//	public int updateContact(Contact contact) {
	//		SQLiteDatabase db = this.getWritableDatabase();
	//
	//		ContentValues values = new ContentValues();
	//		values.put(KEY_NAME, contact.getName());
	//		values.put(KEY_PH_NO, contact.getPhoneNumber());
	//
	//		// updating row
	//		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
	//	}
	//
	//	// Deleting single contact
	//	public void deleteContact(Contact contact) {
	//		SQLiteDatabase db = this.getWritableDatabase();
	//		db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
	//		db.close();
	//	}
	//
	//	// Getting contacts Count
	//	public int getContactsCount() {
	//		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	//		SQLiteDatabase db = this.getReadableDatabase();
	//		Cursor cursor = db.rawQuery(countQuery, null);
	//		cursor.close();
	//
	//		// return count
	//		return cursor.getCount();
	//	}

}
