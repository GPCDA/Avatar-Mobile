package com.example.avatarcertificacao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import com.example.avatarcertificacao.model.Visema;
import com.google.gson.Gson;

public class Util {
	public static final int FEMININO = 8;
	public static final int MASCULINO = 2;

	public static ArrayList<Visema> loadList(Context context, String fileName) {
		ArrayList<Visema> mList = new ArrayList<Visema>();
		try {
			Scanner scanner = new Scanner(context.getAssets().open(fileName));
			while (scanner.hasNextLine()) {
				mList.add(processLine(context, scanner.nextLine(), 1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mList;
	}

	public static ArrayList<Visema> createVisemaList(Context context, String visemaList, int avatarId) {
		ArrayList<Visema> mList = new ArrayList<Visema>();
		Scanner scanner = new Scanner(visemaList);
		while (scanner.hasNextLine()) {
			mList.add(processLine(context, scanner.nextLine(), avatarId));
		}

		return mList;
	}

	protected static Visema processLine(Context context, String aLine, int avatarId) {
		String startTick = "";
		String endTick = "";
		String filename = "";

		Scanner scanner = new Scanner(aLine);
		startTick = scanner.next();

		scanner.useDelimiter(" ");

		if (scanner.hasNext()) {
			endTick = scanner.next();
			filename = scanner.next();
		} else {
			System.out.println("Empty or invalid line. Unable to process.");
		}

		return createVisemaListItem(context, startTick, endTick, filename, avatarId);

	}

	private static Visema createVisemaListItem(Context context, String startTick, String endTick, String filename, int avatarId) {
		
		
		if(endTick.equals("") || startTick.equals("") || filename.equals("")){
			return null;
		}
		float delay = Float.parseFloat(endTick) - Float.parseFloat(startTick);
		filename = filename.substring(0, filename.indexOf("."));
		filename = getLocalFileName(filename, avatarId);
		String uri = "drawable/" + filename;
		int id = context.getResources().getIdentifier(uri, null, context.getPackageName());
		System.out.println("filename "+ filename);
		BitmapDrawable d = (BitmapDrawable) context.getResources().getDrawable(id);
		Visema lex = new Visema(delay, filename, d.getBitmap());
		
		System.out.println("delay" + delay);
		
		return lex;
		
	}

	private static String getLocalFileName(String filename, int avatarId) {
		String prefix = "";
		String localFileName = "";

		if (avatarId == FEMININO) {
			prefix = "f";
		}

		localFileName = filename.replaceAll("\\(", "").replaceAll("\\)", "").toLowerCase().trim();

		return prefix + localFileName;
	}

	public static String readfile(String fileName, Context context) throws IOException {
		//InputStream is = new ByteArrayInputStream("file content".getBytes());
		InputStream is = context.getAssets().open(fileName);
		//read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		br.close();

		return sb.toString();
	}
	//  public void createNotification(){
	//	// Prepare intent which is triggered if the
	//    // notification is selected
	//
	//    Intent intent = new Intent(this, MediaPlayerActivity.class);
	//    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
	//
	//    // Build notification
	//    // Actions are just fake
	//    Notification noti = new NotificationCompat.Builder(this)
	//            .setContentTitle("Nova notificacao")
	//            .setContentText("Shark").setSmallIcon(R.drawable.ic_launcher)
	//            .setContentIntent(pIntent)
	//            .addAction(R.drawable.ic_launcher, "Call", pIntent)
	//            .addAction(R.drawable.ic_launcher, "More", pIntent)
	//            .addAction(R.drawable.ic_launcher, "And more", pIntent).build();
	//        
	//      
	//    NotificationManager notificationManager = 
	//      (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	//
	//    // Hide the notification after its selected
	//    noti.flags |= Notification.FLAG_AUTO_CANCEL;
	//
	//    notificationManager.notify(0, noti); 
	//}
}
