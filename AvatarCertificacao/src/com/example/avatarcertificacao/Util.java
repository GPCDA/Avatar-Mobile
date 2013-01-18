package com.example.avatarcertificacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

public class Util {
	public static final int MASCULINO = 1;
	public static final int FEMININO = 2;
	
	
	public static ArrayList<Lexema> loadList(Context context,String fileName) {
		ArrayList<Lexema>mList = new ArrayList<Lexema>();
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

	protected static Lexema processLine(Context context, String aLine) {
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

	private static Lexema createLexemaListItem(Context context,String startTick, String endTick, String filename) {
		float delay = Float.parseFloat(endTick) - Float.parseFloat(startTick);
		filename = filename.substring(0,filename.indexOf("."));
		String uri = "drawable/" + filename;
		System.out.println("filename: "+filename+"\nuri: "+uri);
		int id = context.getResources().getIdentifier(uri, null, context.getPackageName());

		BitmapDrawable d = (BitmapDrawable) context.getResources().getDrawable(id);
		Lexema lex = new Lexema(delay, filename, d.getBitmap());
		
		return lex;

	}
}
