package com.example.ks.suraksha.Utility;


import android.os.Environment;

public class Constants {
    private static final String enrollOps[] = {"ENROLLMENT","DROP OUT", "SCHOOL GOING"};
    private static final String aadharOps[] = {"AADHAR CARD","YES", "NO"};
    private static final String ageOps[] = {"AGE GROUP","4-10", "11-16","16-20"};
    private static final String gender[]={"GENDER","Female","Male","Other"};
    private static final String category[]={"CATEGORY","OBC ","SC","GENERAL","ST","NO CATEGORY"};
    private static final String MainDirectory= Environment.getExternalStorageDirectory().getAbsolutePath() + "/Suraksha/";
    private static final String ImageDirectory= MainDirectory + "/Images/";
    private static final String SUCCESS="success";
    private static final String FAILURE="failed";
    private static String userTypes[]={"USER TYPE","SUPER ADMIN","ADMIN","USER"};

    public static final int MAIN=1;
    public static final int SEARCHABLE=2;

    public static final int ADD_CHILD_Intent=1;

    // TODO: 10-02-2018 just do it


    public static String[] getEnrollOps() {
        return enrollOps;
    }

    public static String[] getAadharOps() {
        return aadharOps;
    }

    public static String[] getAgeOps() {
        return ageOps;
    }

    public static String[] getGender() {
        return gender;
    }

    public static String getImageDirectory() {
        return ImageDirectory;
    }

    public static String[] getCategory() {
        return category;
    }



    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILURE() {
        return FAILURE;
    }

    public static String[] getUserTypes() {
        return userTypes;
    }
}
