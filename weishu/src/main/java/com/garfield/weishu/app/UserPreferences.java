package com.garfield.weishu.app;

import android.content.Context;
import android.content.SharedPreferences;


public class UserPreferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";

    public static void saveUserAccount(String account) {
        AppCache.setAccount(account.toLowerCase());
        saveString(KEY_USER_ACCOUNT, account.toLowerCase());
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return AppCache.getContext().getSharedPreferences("weishu", Context.MODE_PRIVATE);
    }
}
