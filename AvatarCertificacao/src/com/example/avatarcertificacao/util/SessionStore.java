/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.avatarcertificacao.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SessionStore {

    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String URL = "url";
    
    private static final String KEY = "AvatarVocallabMob";
    
    public static boolean save(Context context, String token, long expiration) {
        Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.putLong(EXPIRES, expiration);
		return editor.commit();
    }
    
    public static boolean save(Context context, String username, String password, String url) {
        Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putString(URL, url);
		return editor.commit();
    }
    
    public static String getUserToken(Context context) {
        if (context.getSharedPreferences(KEY, Context.MODE_PRIVATE).contains(TOKEN)) {
        	return context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getString(TOKEN, "");
        } else {
        	return "";
        }
    }
    
    public static String getUsername(Context context) {
        if (context.getSharedPreferences(KEY, Context.MODE_PRIVATE).contains(USERNAME)) {
        	return context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getString(USERNAME, "");
        } else {
        	return "";
        }
    }
    
    public static String getUrl(Context context) {
        if (context.getSharedPreferences(KEY, Context.MODE_PRIVATE).contains(URL)) {
        	return context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getString(URL, "");
        } else {
        	return "";
        }
    }
    
    public static String getPassword(Context context) {
        if (context.getSharedPreferences(KEY, Context.MODE_PRIVATE).contains(PASSWORD)) {
        	return context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getString(PASSWORD, "");
        } else {
        	return "";
        }
    }

    public static Long getExpirationData(Context context) {
        if (context.getSharedPreferences(KEY, Context.MODE_PRIVATE).contains(TOKEN)) {
        	return context.getSharedPreferences(KEY, Context.MODE_PRIVATE).getLong(EXPIRES,0);
        } else {
        	return 0L;
        }
    }
    
    public static void clear(Context context) {
        Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

}
