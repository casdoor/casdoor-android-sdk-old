package com.casbin.casdoor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONObject;

import java.util.Map;

public class CasdoorUser {
    String Owner;
    String Name;
    String CreatedTime;
    String UpdatedTime;

    String Id;
    String Type;
    String Password;
    String DisplayName;
    String Avatar;
    String Email;
    String Phone;
    String[] Address;
    String Affiliation;
    String Tag;
    String Language;
    int Score;
    boolean IsAdmin;
    boolean IsGlobalAdmin;
    boolean IsForbidden;
    String SignupApplication;
    String Hash;
    String PreHash;

    String Github;
    String Google;
    String QQ;
    String WeChat;
    String Facebook;
    String DingTalk;
    String Weibo;
    String Gitee;
    String LinkedIn;

    Map<String, String> Properties;

    // set users through the type of JSONObject
    static void SetUsers(FragmentActivity activity, JSONObject usersJsonObj) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("users", usersJsonObj.toString());
        editor.apply();
    }

    

    // set user through the type of JSONObject
    static void SetUser(FragmentActivity activity, JSONObject userJsonObj) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", userJsonObj.toString());
        editor.apply();
    }
}


