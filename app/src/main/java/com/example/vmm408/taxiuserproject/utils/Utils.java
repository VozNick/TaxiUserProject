package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static final String SHARED_KEY = "UserProfile";
    private static final String SHARED_STRING_KEY = "userId";

    public void saveToShared(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND);
        preferences.edit().putString(SHARED_STRING_KEY, userId).apply();
    }

    public void deleteFromShared(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND);
        preferences.edit().remove(SHARED_STRING_KEY).apply();
    }

    public String userSigned(Context context) {
        return context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND)
                .getString(SHARED_STRING_KEY, null);
    }
}
