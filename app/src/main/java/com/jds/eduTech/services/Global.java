package com.jds.eduTech.services;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;


public final class Global implements Serializable, Constants {

    private Boolean isLogin;
    private String fullName;
    private String login_id;
    private String email;
    private String role_id;
    private String user_id;
    private String district_id;
    private String roleName;
    private String schoolId;
    private String sessionId;

    private Context context;
    private JsonObject session_obj;

    public JsonObject getSession_obj() {
        return session_obj;
    }

    public void setSession_obj(JsonObject session_obj) {
        this.session_obj = session_obj;
    }

    private static  Global mInstance;

    private static final Gson gson = new Gson();


    private Global(Context context) {
        if (mInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.context=context;
        SharedPreferences spf_login = context.getSharedPreferences(Constants.SHARED_PREF_NAME, 0);
        isLogin = spf_login.getBoolean(Constants.SHARED_PREF_IS_LOGIN,false);
        fullName = spf_login.getString(Constants.SHARED_PREF_FULL_NAME, "");
        login_id = spf_login.getString(Constants.LOGIN_ID, "");
        email = spf_login.getString(Constants.EMAIL, "");
        role_id = spf_login.getString(Constants.ROLE_ID, "");
        user_id = spf_login.getString(Constants.USER_ID, "");
        district_id = spf_login.getString(Constants.DISTRICT_ID, "");
        roleName = spf_login.getString(Constants.ROLE_NAME, "");
        schoolId = spf_login.getString(Constants.SCHOOL_ID, "");
        sessionId = spf_login.getString(Constants.SESSION_ID, "");

    }

    public static synchronized Global getInstance(Context context) {
        if (mInstance == null) {
            /*synchronized (Global.class) {
                if (mInstance == null) {*/
                    mInstance = new Global(context);
                }
            /*}
        }*/
        return mInstance;
    }

    protected Global readResolve(Context context2) {
        return getInstance(context2);
    }


    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constants.SHARED_PREF_IS_LOGIN,isLogin);
        editor.apply();
        this.isLogin = isLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.SHARED_PREF_FULL_NAME,fullName);
        editor.apply();


        this.fullName = fullName;
    }
    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOGIN_ID, login_id);
        editor.apply();


        this.login_id = login_id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.EMAIL, email);
        editor.apply();


        this.email = email;
    }
    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.ROLE_ID, role_id);
        editor.apply();


        this.role_id = role_id;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USER_ID, user_id);
        editor.apply();


        this.user_id = user_id;
    }

    public String getDistrict_id() {
        return district_id;
    }
    public void setDistrict_id(String district_id) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.DISTRICT_ID, district_id);
        editor.apply();


        this.district_id = district_id;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.ROLE_NAME, roleName);
        editor.apply();


        this.roleName = roleName;
    }

    public String getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(String schoolId) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.SCHOOL_ID, schoolId);
        editor.apply();


        this.schoolId = schoolId;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {

        this.sessionId = sessionId;
    }




}
