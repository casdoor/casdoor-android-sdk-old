package com.casbin.casdoor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;

public class CasdoorLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_casdoor_login);

        WebView webView = findViewById(R.id.casdoor_login_webview);
        webView.setWebViewClient(new CasdoorWebView(this));

        Intent intent = this.getIntent();
        CasdoorConfig.InitConfig(
                this,
                intent.getStringExtra(CasdoorConfig.ENDPOINT),
                intent.getStringExtra(CasdoorConfig.CLIENTID),
                intent.getStringExtra(CasdoorConfig.CLIENTSECRET),
                intent.getStringExtra(CasdoorConfig.JWTSECRET),
                intent.getStringExtra(CasdoorConfig.ORGANIZATIONNAME)
        );

        try {
            CasdoorWebView.LoadLoginPage(this, webView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
