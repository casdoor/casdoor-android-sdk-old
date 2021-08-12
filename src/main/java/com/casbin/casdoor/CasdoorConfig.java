package com.casbin.casdoor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class CasdoorConfig {
    public static final String ENDPOINT = "Endpoint";
    public static final String CLIENTID = "ClientID";
    public static final String CLIENTSECRET = "ClientSecret";
    public static final String JWTSECRET = "JWTSecret";
    public static final String ORGANIZATIONNAME = "OrganizationName";

    public static final String TOKENGRANTTYPE = "authorization_code";

    //    public static final String REDIRECTURI = "https://oa.casbin.com/callback";
    public static final String REDIRECTURI = "http://chenweihao.vip:8081/callback";


    public String Endpoint;
    public String ClientID;
    public String ClientSecret;
    public String JWTSecret;
    public String OrganizationName;

    CasdoorConfig(String endPoint, String clientID, String clientSecret, String jwtSecret, String organizationName) {
        this.Endpoint = endPoint;
        this.ClientID = clientID;
        this.ClientSecret = clientSecret;
        this.JWTSecret = jwtSecret;
        this.OrganizationName = organizationName;
    }

    // this method is used for SDK, config values should be sent through intent before start activity
    static void InitConfig(FragmentActivity activity, String endPoint, String clientID, String clientSecret, String jwtSecret, String organizationName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ENDPOINT, endPoint);
        editor.putString(CLIENTID, clientID);
        editor.putString(CLIENTSECRET, clientSecret);
        editor.putString(JWTSECRET, jwtSecret);
        editor.putString(ORGANIZATIONNAME, organizationName);

        editor.apply();
    }

    static CasdoorConfig GetConfig(FragmentActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);

        return new CasdoorConfig(
                sharedPreferences.getString(ENDPOINT, ""),
                sharedPreferences.getString(CLIENTID, ""),
                sharedPreferences.getString(CLIENTSECRET, ""),
                sharedPreferences.getString(JWTSECRET, ""),
                sharedPreferences.getString(ORGANIZATIONNAME, "")
        );
    }
}
