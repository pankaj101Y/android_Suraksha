package com.example.ks.suraksha;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.ks.suraksha.UserProfile;

public class AppData {
    private static final String APP_DATA="APP_DATA";

    private static final String isSignedUp="signUpStatus";
    private static final String emailKey="username";
    private static final String passwordKey="password";
    private static String email=null;
    private static String password=null;

    private static final String isProfileSetup="profile_status";
    private static final String nameKey="name";
    private static final String phoneKey="phone";

    private static final String isPermanentAddressProvided="permanentAddressStatus";
    private static final String streetNoKey= "streetNo";
    private static final String landMarkKey= "landmark";
    private static final String cityKey= "city";
    private static final String zipCodeKey= "ZipCode";
    private static final String stateKey= "state";
    private static final String countryKey= "country";

    static boolean isSignUp(Context context){
        SharedPreferences appPrefs=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        return appPrefs.getBoolean(isSignedUp,false);
    }

    static String getPassword(Context context){
        if (password==null)
            initialiseCredentials(context);
        return password;
    }

    static String getEmail(Context context){
        if (email==null)
            initialiseCredentials(context);
        return email;
    }

    private static void initialiseCredentials(Context context){
        SharedPreferences appPrefs=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        email=appPrefs.getString(emailKey,null);
        password=appPrefs.getString(passwordKey,null);
    }

    public static void signUpLogIn(Context c, String email, String password){
        SharedPreferences.Editor e=c.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        e.putString(emailKey,email);
        e.putString(passwordKey,password);
        e.putBoolean(isSignedUp,true);
        e.apply();
    }

    static void LogOut(Context context){
        SharedPreferences.Editor e=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        e.putString(emailKey,null);
        e.putString(passwordKey,null);
        e.putBoolean(isSignedUp,false);
        e.apply();
    }



    public static boolean isUserProfileSetup(Context context){
        SharedPreferences s=context.getSharedPreferences(APP_DATA,Context.MODE_PRIVATE);
        return s.getBoolean(isProfileSetup,false);
    }

    public static void setUserProfile(Context context, String name, String phone){
        SharedPreferences.Editor e=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        e.putString(nameKey,name);
        e.putString(phoneKey,phone);
        e.putBoolean(isProfileSetup,true);
        e.apply();
    }

    public static UserProfile getUserProfile(Context context) {
        SharedPreferences userData=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        String name= userData.getString(nameKey,null);
        String phone=userData.getString(phoneKey,null);
        return new UserProfile(name,phone);
    }



    static boolean iPermanentAddressProvided(Context context){
        SharedPreferences appData=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        return appData.getBoolean(isPermanentAddressProvided,false);
    }

    public static void saveUserAddress(Context context, String streetNo, String landMark, String zipCode, String city, String state, String country){
        SharedPreferences.Editor e=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        e.putString(streetNoKey,streetNo);
        e.putString(landMarkKey,landMark);
        e.putString(zipCodeKey,zipCode);
        e.putString(cityKey,city);
        e.putString(stateKey,state);
        e.putString(countryKey,country);
        e.putBoolean(isPermanentAddressProvided,true);
        e.apply();
    }

    static UserProfile.Address getUserAddress(Context context){
        SharedPreferences userData=context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        String street=userData.getString(streetNoKey,null);
        String landmark=userData.getString(landMarkKey,null);
        String city=userData.getString(cityKey,null);
        String zipCode=userData.getString(zipCodeKey,null);
        String state=userData.getString(stateKey,null);
        String country=userData.getString(countryKey,null);
        return new UserProfile.Address(street,landmark,city,zipCode,state,country);
    }

    static void clearCredentials(Context c){
        SharedPreferences.Editor e=c.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        e.putString(emailKey,null);
        e.putString(passwordKey,null);
        e.putBoolean(isSignedUp,false);

        e.putString(nameKey,null);
        e.putString(phoneKey,null);
        e.putBoolean(isProfileSetup,false);

        e.putString(streetNoKey,null);
        e.putString(landMarkKey,null);
        e.putString(zipCodeKey,null);
        e.putString(cityKey,null);
        e.putString(stateKey,null);
        e.putString(countryKey,null);
        e.putBoolean(isPermanentAddressProvided,false);
        e.apply();
    }
}
