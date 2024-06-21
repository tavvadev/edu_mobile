package com.jds.eduTech.services;


public interface Constants {

    String SHARED_PREF_NAME                 ="spf_login_details";
    String SHARED_PREF_IS_LOGIN             ="isLogin";
    String SHARED_PREF_FULL_NAME            ="fullName";
    String LOGIN_ID                         ="loginId";

    String PORT_PROD           = ".com";
    String PORT_DEV            = ".in";

    String PORT                = PORT_DEV;
    //String PORT                = BuildConfig.ENV;

   // String EDUTECH_API          = "http://3.91.54.205/api/";
    String EDUTECH_API          = "http://3.84.196.148/api/";


    String DISTRICT_ID = "districtId";
    String SCHOOL_ID = "schoolId";
    String ROLE_ID = "roleId";
    String USER_ID = "userId";
    String EMAIL = "email";
    String ROLE_NAME = "roleName";
    String SESSION_ID = "sessionId";
}
