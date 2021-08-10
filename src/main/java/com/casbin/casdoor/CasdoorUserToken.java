package com.casbin.casdoor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class CasdoorUserToken {
    public static String GetUserToken(FragmentActivity activity) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(
                    "user_token", Context.MODE_PRIVATE
            );
            return sharedPreferences.getString("user_token", "");
        } else {
            return "";
        }
    }

    static void SetUserToken(FragmentActivity activity, String userToken) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(
                "user_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_token", userToken);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static CasdoorClaims ParseJwtToken(FragmentActivity activity, String token) {
        CasdoorClaims casdoorClaims = new CasdoorClaims();
        if (!CasdoorConfig.GetConfig(activity).JWTSecret.equals("") && !token.equals("") && !CasdoorUserToken.GetUserToken(activity).equals("")) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(CasdoorConfig.GetConfig(activity).JWTSecret.getBytes()))
                    .build().parseClaimsJws(token).getBody();

            casdoorClaims.Affiliation = (String) claims.get("affiliation");
            casdoorClaims.Aud = (String) claims.get("aud");
            casdoorClaims.Avatar = (String) claims.get("avatar");
            casdoorClaims.Email = (String) claims.get("email");
            casdoorClaims.Exp = (int) claims.get("exp");
            casdoorClaims.Iat = (int) claims.get("iat");
            casdoorClaims.IsAdmin = (boolean) claims.get("isAdmin");
            casdoorClaims.Iss = (String) claims.get("iss");
            casdoorClaims.Language = (String) claims.get("language");
            casdoorClaims.Name = (String) claims.get("name");
            casdoorClaims.Nbf = (int) claims.get("nbf");
            casdoorClaims.Organization = (String) claims.get("organization");
            casdoorClaims.Phone = (String) claims.get("phone");
            casdoorClaims.Score = (int) claims.get("score");
            casdoorClaims.Sub = (String) claims.get("sub");
            casdoorClaims.Tag = (String) claims.get("tag");
            casdoorClaims.Type = (String) claims.get("type");
            casdoorClaims.UserName = (String) claims.get("userName");
        }
        return casdoorClaims;
    }
}
