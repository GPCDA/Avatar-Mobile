package com.example.avatarcertificacao.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.avatarcertificacao.model.Visema;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

public class Util {
	public static final int MASCULINO = 1;
	public static final int FEMININO = 2;
	
	
	public static ArrayList<Visema> loadList(Context context,String fileName) {
		ArrayList<Visema>mList = new ArrayList<Visema>();
		try {
			Scanner scanner = new Scanner(context.getAssets().open(fileName));
			while (scanner.hasNextLine()) {
				mList.add(processLine(context,scanner.nextLine()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mList;
	}

	protected static Visema processLine(Context context, String aLine) {
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

		return createLexemaListItem(context, startTick, endTick, filename);

	}

	private static Visema createLexemaListItem(Context context,String startTick, String endTick, String filename) {
		float delay = Float.parseFloat(endTick) - Float.parseFloat(startTick);
		filename = filename.substring(0,filename.indexOf("."));
		String uri = "drawable/" + filename;
		System.out.println("filename: "+filename+"\nuri: "+uri);
		int id = context.getResources().getIdentifier(uri, null, context.getPackageName());

		BitmapDrawable d = (BitmapDrawable) context.getResources().getDrawable(id);
		Visema lex = new Visema(delay, filename, d.getBitmap());
		
		return lex;

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
