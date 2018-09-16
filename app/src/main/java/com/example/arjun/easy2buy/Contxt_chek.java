package com.example.arjun.easy2buy;

import android.app.Application;
import android.content.Context;

public class Contxt_chek extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
}
