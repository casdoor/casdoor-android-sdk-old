package com.casbin.casdoor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class CasdoorAuth {

    public static boolean hasLoggedIn(FragmentActivity activity) {
        return Boolean.parseBoolean(activity.getSharedPreferences(
                "auth",
                Context.MODE_PRIVATE).getString("hasLoggedIn", String.valueOf(false)
        ));
    }

    static void setLogin(FragmentActivity activity, boolean hasLoggedIn) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hasLoggedIn", String.valueOf(hasLoggedIn));
        editor.apply();
    }

    public static void logout(FragmentActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hasLoggedIn", String.valueOf(false));
        editor.apply();

        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }
}
