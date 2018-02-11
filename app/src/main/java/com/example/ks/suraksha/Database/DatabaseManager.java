package com.example.ks.suraksha.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ks.suraksha.AdmissionInfo;

import java.util.ArrayList;


public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE="surakshaDb";
    private static final int VERSION =1;

    private static AdmissionTable admissionTable=null;
    private static DatabaseManager databaseManager=null;

    public static DatabaseManager getDatabaseManager(Context context){
        if (databaseManager==null)
            databaseManager=new DatabaseManager(context);
        return databaseManager;
    }

    private DatabaseManager(Context context) {
        super(context, DATABASE, null, VERSION);
        admissionTable=new AdmissionTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        admissionTable.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertAdmission(AdmissionInfo admissionInfo){
        admissionTable.insert(getWritableDatabase(),admissionInfo);
    }

    public void updateAdmission(AdmissionInfo previous,AdmissionInfo updated){
        admissionTable.update(getWritableDatabase(),previous,updated);
    }

    public void deleteAdmission(AdmissionInfo admission){
        admissionTable.delete(getWritableDatabase(),admission);
    }

    public ArrayList<AdmissionInfo> getAdmissions(){
        return admissionTable.getNextRecords(getReadableDatabase(),-1);
    }

    public ArrayList<AdmissionInfo> getAdmissions(int limit){
        return admissionTable.getNextRecords(getReadableDatabase(),limit);
    }

    public void refreshAdmissions(){
         admissionTable.getNextRecords(getReadableDatabase(),-1);
    }

    public ArrayList<AdmissionInfo>getAdmissionsList(){
        return admissionTable.getAdmissions();
    }

    public ArrayList<AdmissionInfo>getSearchResult(String name){
        return admissionTable.getSearchResult(getReadableDatabase(),name);
    }
}
