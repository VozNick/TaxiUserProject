package com.example.vmm408.taxiuserproject;

import android.app.Application;
import android.content.Intent;

import com.example.vmm408.taxiuserproject.utils.Utils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (new Utils().userSigned(getApplicationContext()) != null) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
    }
}
