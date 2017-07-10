package com.example.vmm408.taxiuserproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (userSigned() != null) startActivity(new Intent(getBaseContext(), MainActivity.class));
    }

    private String userSigned() {
        return getApplicationContext().getSharedPreferences("UserProfile", Context.MODE_APPEND)
                .getString("userId", null);
    }
}
