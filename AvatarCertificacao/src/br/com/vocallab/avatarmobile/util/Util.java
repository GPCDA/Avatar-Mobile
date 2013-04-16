package br.com.vocallab.avatarmobile.util;

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

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import br.com.vocallab.avatarmobile.model.Visema;
import br.com.vocallab.avatarmobile.service.NotificationService;

public class Util {
	public static final String FEMININO = "8";
	public static final String MASCULINO = "2";
	public static final String RESOURCE_PREFIX = "avatar_";
	public static final String RESOURCE_EYES_CLOSED_SUFIX = "_closed";
	public static final boolean isStub = false;

	public static String loadList(Context context, String fileName) {
		StringBuilder builder = new StringBuilder();
		try {
			Scanner scanner = new Scanner(context.getAssets().open(fileName));
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static ArrayList<Visema> createVisemaList(Context context, String visemaList, String avatarId) {
		if (Util.isStub) {
			visemaList = "0.0 101.7868 repouso.png\n101.7868 203.5736 o(min).png\n203.5736 254.467 l2(min).png\n254.467 356.25378 a.png\n356.25378 458.0406 a.png\n458.0406 508.934 t1.png\n508.934 559.8274 p1.png\n559.8274 661.6142 i1(min).png\n661.6142 712.50757 t1.png\n712.50757 814.2944 i1(min).png\n814.2944 865.18774 s1(min).png\n865.18774 916.0811 t1.png\n916.0811 966.9745 r1.png\n966.9745 1068.7612 a.png\n1068.7612 1119.6547 t1.png\n1119.6547 1221.4414 o(min).png\n1323.2281 1425.0149 repouso.png\n1425.0149 1475.9083 f1.png\n1475.9083 1577.6951 o(min).png\n1577.6951 1628.5885 s1(min).png\n1628.5885 1730.3752 e(min).png\n1730.3752 1781.2687 p1.png\n1781.2687 1883.0554 o(min).png\n1883.0554 1933.9489 s2(min).png\n1933.9489 2035.7356 u.png\n2035.7356 2137.5225 i1(min).png\n2137.5225 2188.4158 k2.png\n2188.4158 2290.2026 u.png\n2290.2026 2391.9895 a.png\n2391.9895 2442.8828 t1.png\n2442.8828 2493.7761 r2.png\n2493.7761 2595.563 u.png\n2595.563 2697.3499 a.png\n2697.3499 2748.2432 t1.png\n2748.2432 2850.03 i1(min).png\n2850.03 2900.9233 f1.png\n2900.9233 3002.7102 i1(min).png\n3002.7102 3053.6035 t2.png\n3053.6035 3155.3904 a.png\n3155.3904 3206.2837 t1.png\n3206.2837 3308.0706 i1(min).png\n3308.0706 3358.9639 s1(min).png\n3358.9639 3409.8572 p1.png\n3409.8572 3511.644 e(min).png\n3511.644 3562.5374 t1.png\n3562.5374 3664.3242 e(min).png\n3664.3242 3715.2175 t1.png\n3715.2175 3817.0044 i1(min).png\n3817.0044 3867.8977 s1(min).png\n3867.8977 3969.6846 repouso.png\n3969.6846 4020.578 s2(min).png\n4020.578 4122.3647 u.png\n4122.3647 4173.2583 S1(mai).png\n4173.2583 4275.045 i1(min).png\n4275.045 4325.9385 r2.png\n4325.9385 4427.725 u.png\n4427.725 4478.6187 k1.png\n4478.6187 4580.4053 i1(min).png\n4580.4053 4631.299 t1.png\n4631.299 4733.0854 e(min).png\n4733.0854 4783.979 L1(mai).png\n4783.979 4885.7656 a.png\n4885.7656 4936.659 p1.png\n4936.659 5038.446 a.png\n5038.446 5140.2324 i1(min).png\n5140.2324 5191.126 s1(min).png\n5191.126 5242.0195 k3.png\n5242.0195 5343.806 u.png\n5343.806 5445.593 i1(min).png\n5445.593 5496.4863 t2.png\n5496.4863 5598.273 a.png\n5598.273 5649.1665 t1.png\n5649.1665 5750.953 u.png\n5750.953 5801.8467 f1.png\n5801.8467 5903.6333 o(min).png\n5903.6333 5954.527 s1(min).png\n5954.527 6056.3135 e(min).png\n6056.3135 6158.1 e(min).png\n6158.1 6208.9937 s1(min).png\n6208.9937 6259.887 t2.png\n6259.887 6361.674 a.png\n6361.674 6463.4604 a.png\n6463.4604 6514.354 k3.png\n6514.354 6616.1406 u.png\n6616.1406 6667.034 p2.png\n6667.034 6768.821 u.png\n6768.821 6819.7144 l1(min).png\n6819.7144 6921.501 a.png\n6921.501 6972.3945 t1.png\n6972.3945 7074.181 u.png\n7074.181 7125.0747 p2.png\n7125.0747 7226.8613 u.png\n7226.8613 7328.648 i1(min).png\n7328.648 7379.5415 t2.png\n7379.5415 7481.328 a.png\n7481.328 7532.2217 s1(min).png\n7532.2217 7634.0083 a.png\n7634.0083 7684.902 t1.png\n7684.902 7786.6885 i1(min).png\n7786.6885 7837.582 f1.png\n7837.582 7939.3687 i1(min).png\n7939.3687 7990.262 t2.png\n7990.262 8092.049 a.png\n8092.049 8142.9424 t1.png\n8142.9424 8244.7295 i1(min).png\n8244.7295 8295.623 s1(min).png\n8295.623 8346.517 t2.png\n8346.517 8448.304 a.png\n8448.304 8550.091 u.png\n8550.091 8600.984 r1.png\n8600.984 8702.771 e(min).png\n8702.771 8753.665 s1(min).png\n8753.665 8804.559 p1.png\n8804.559 8906.346 o(min).png\n8906.346 8957.239 t1.png\n8957.239 9059.026 i1(min).png\n9059.026 9109.92 t2.png\n9109.92 9211.707 a.png\n9211.707 9262.601 s1(min).png\n9262.601 9364.388 repouso.png\n9364.388 9415.281 t1.png\n9415.281 9517.068 a.png\n9517.068 9618.855 u.png\n9618.855 9669.749 p1.png\n9669.749 9771.536 e(min).png\n9873.323 9924.217 k2.png\n9924.217 10026.004 a.png\n10026.004 10076.897 t1.png\n10076.897 10178.685 e(min).png\n10178.685 10229.578 p2.png\n10229.578 10331.365 u.png\n10331.365 10433.152 i1(min).png\n10433.152 10534.939 e(min).png\n10534.939 10585.833 s1(min).png\n10585.833 10636.727 t1.png\n10636.727 10738.514 u.png\n10738.514 10789.407 t1.png\n10789.407 10891.194 i1(min).png\n10891.194 10992.981 a.png\n10992.981 11043.875 k2.png\n11043.875 11145.662 o(min).png\n11145.662 11196.556 r1.png\n11196.556 11298.343 a.png\n11298.343 11349.236 p1.png\n11349.236 11451.023 e(min).png\n11451.023 11501.917 s1(min).png\n11501.917 11552.811 p2.png\n11552.811 11654.598 u.png\n11654.598 11756.385 repouso.png\n";
		}
		visemaList = visemaList.replace("\n", " ");
		Scanner scanner = new Scanner(visemaList);

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
		long delayInMilis = (long) Math.floor(delay);
		filename = filename.substring(0, filename.indexOf("."));
		filename = getLocalFileName(filename, avatarId);
		String uri = "drawable/" + filename;
		int id = context.getResources().getIdentifier(uri, null, context.getPackageName());

		Visema lex = new Visema(delayInMilis, filename, id);

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

	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;

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

	public static String defineAvatarType(int avatarId) {
		String resourceFile = RESOURCE_PREFIX;
		if (avatarId <= 9) {
			resourceFile = resourceFile + "0";
		}

		resourceFile = resourceFile + "" + avatarId;

		return resourceFile;
	}

	public static void startService(Activity activity) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		int minutes = prefs.getInt("interval", 0);
		AlarmManager am = (AlarmManager) activity.getSystemService(Activity.ALARM_SERVICE);
		Intent i = new Intent(activity, NotificationService.class);
		PendingIntent pi = PendingIntent.getService(activity, 0, i, 0);
		am.cancel(pi);
		// by my own convention, minutes <= 0 means notifications are disabled
		if (minutes > 0) {
			am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + minutes * 60 * 1000,
					minutes * 60 * 1000, pi);
		}
	}

}
