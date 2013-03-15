package com.example.avatarcertificacao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.example.avatarcertificacao.model.Visema;

public class Util {
	public static final String FEMININO = "8";
	public static final String MASCULINO = "2";

	public static String loadList(Context context, String fileName) {
		ArrayList<Visema> mList = new ArrayList<Visema>();
		StringBuilder builder = new StringBuilder();
		try {
			Scanner scanner = new Scanner(context.getAssets().open(fileName));
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
				//mList.add(processLine(context, scanner.nextLine(), 1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static ArrayList<Visema> createVisemaList(Context context, String visemaList, String avatarId) {
		ArrayList<Visema> mList = new ArrayList<Visema>();
		visemaList = visemaList.replace("\n", " ");
		Scanner scanner = new Scanner(visemaList);
		//		mList.add(processLine(context, scanner.nextLine(), avatarId));
		//		while (scanner.hasNextLine()) {
		//			mList.add(processLine(context, scanner.nextLine(), avatarId));
		//		}

		return temp(context, scanner.nextLine(), avatarId);
	}

	protected static Visema processLine(Context context, String aLine, String avatarId) {
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

	protected static ArrayList<Visema> temp(Context context, String aLine, String avatarId) {
		ArrayList<Visema> mList = new ArrayList<Visema>();
		String startTick = "";
		String endTick = "";
		String filename = "";

		Scanner scanner = new Scanner(aLine);

		while (scanner.hasNext()) {
			startTick = scanner.next();
			scanner.useDelimiter(" ");
			if (scanner.hasNext()) {
				endTick = scanner.next();
				filename = scanner.next();
				mList.add(createVisemaListItem(context, startTick, endTick, filename, avatarId));
			}

		}
		return mList;
	}

	private static Visema createVisemaListItem(Context context, String startTick, String endTick, String filename, String avatarId) {
		
		if (endTick.equals("") || startTick.equals("") || filename.equals("")) {
			return null;
		}
		System.out.println(startTick + "\t" + endTick + "\t" + filename);
		
		double delay = Double.parseDouble(endTick) - Double.parseDouble(startTick);
		long delayInMilis= (long) Math.floor(delay);
		filename = filename.substring(0, filename.indexOf("."));
		filename = getLocalFileName(filename, avatarId);
		String uri = "drawable/" + filename;
		int id = context.getResources().getIdentifier(uri, null, context.getPackageName());
		//System.out.println("filename " + filename);
//		BitmapDrawable d = (BitmapDrawable) context.getResources().getDrawable(id);
//		Visema lex = new Visema(delay, filename, d.getBitmap());
		
		//Visema lex = new Visema(delayInMilis, filename);
		
		Visema lex = new Visema(delayInMilis,filename, id);
		
		//System.out.println("delay" + delay);
		
		
//		Bitmap bm = d.getBitmap();
//
//		if (bm != null) {
//			bm.recycle();
//			try {
//				Thread.sleep(10);
//			} catch (Exception e) {
//			}
//			bm = null;
//			
//			try {
//				Thread.sleep(10);
//			} catch (Exception e) {
//			}
//		}

		return lex;

	}

	private static String getLocalFileName(String filename, String avatarId) {
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

	public static String login(String url, String username, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json", "token"));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		return loadUrl(url, params);
	}
	
	public static String loadMessages(String url, String token) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json", "content"));
		params.add(new BasicNameValuePair("token", token));
		return loadUrl(url, params);
	}

	public static String loadUrl(String url, List<NameValuePair> params) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response;
		int statusCode;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				return streamToString(response.getEntity().getContent());
			} else {
				return "Error:" + response.getStatusLine().getStatusCode();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Error:HTTP";
	}

	public static String streamToString(InputStream is) throws IOException {

		BufferedReader r = new BufferedReader(new InputStreamReader(is));

		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}

		return total.toString();
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
