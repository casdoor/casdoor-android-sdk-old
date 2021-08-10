package com.casbin.casdoor;

public class CasdoorClaims {
    String Organization;
    String UserName;
    String Type;

    String Name;
    String Avatar;
    String Email;
    String Phone;

    String Affiliation;
    String Tag;
    String Language;
    int Score;

    boolean IsAdmin;
    String Aud;
    int Exp;
    int Iat;
    String Iss;
    int Nbf;

    public String getOrganization() {
        return Organization;
    }

    public String getUserName() {
        return UserName;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAffiliation() {
        return Affiliation;
    }

    public String getTag() {
        return Tag;
    }

    public String getLanguage() {
        return Language;
    }

    public int getScore() {
        return Score;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public String getAud() {
        return Aud;
    }

    public int getExp() {
        return Exp;
    }

    public int getIat() {
        return Iat;
    }

    public String getIss() {
        return Iss;
    }

    public int getNbf() {
        return Nbf;
    }

    public String getSub() {
        return Sub;
    }

    String Sub;
}
