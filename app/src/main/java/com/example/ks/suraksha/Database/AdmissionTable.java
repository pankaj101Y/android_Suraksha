package com.example.ks.suraksha.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ks.suraksha.AdmissionInfo;

import java.util.ArrayList;

class AdmissionTable implements Table<AdmissionInfo>{
    private static final String ADMISSION_TABLE ="admission_table";

    private static final String ID ="id";
    private static final String SERVER_ID ="server_id";
    private static final String PHOTO ="photo";
    private static final String PHOTO_SYNC_STATUS ="photoSyncStatus";
    private static final String NAME ="name";
    private static final String AGE ="age";
    private static final String MOTHER_NAME ="mother_name";
    private static final String FATHER_NAME ="father_name";
    private static final String NO_SIBLINGS ="no_siblings";
    private static final String OCCUPATION ="occupation";
    private static final String NATIVE_PLACE ="native_place";
    private static final String ENROLLMENT_STATUS ="enrollmentStatus";
    private static final String AADHAR_STATUS ="aadhar_status";
    private static final String AADHAR_REASON ="aadhar_reason";
    private static final String GENDER ="gender";
    private static final String CATEGORY ="category";

    private static ArrayList<AdmissionInfo>admissions=new ArrayList<>();

    ArrayList<AdmissionInfo> getAdmissions() {
        return admissions;
    }

    @Override
    public void create(SQLiteDatabase db) {
        String createQuery="create table "+ ADMISSION_TABLE +" " +
                "(" +
                ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SERVER_ID +" serverId,"+
                PHOTO +" varchar(100),"+
                PHOTO_SYNC_STATUS +" int(1) default 0,"+
                NAME +" varchar(32) not null,"+
                AGE +  " int not null,"+
                MOTHER_NAME + " varchar(32),"+
                FATHER_NAME + " varchar(32),"+
                NO_SIBLINGS + " int,"+
                OCCUPATION +" varchar(32) not null,"+
                NATIVE_PLACE + " varchar(32) not null,"+
                ENROLLMENT_STATUS +" varchar(16) not null,"+
                AADHAR_STATUS + " varchar(3) not null,"+
                AADHAR_REASON +" varchar(64) not null,"+
                GENDER + " varchar(6) not null,"+
                CATEGORY +" varchar(8) not null"+
                ")";
        db.execSQL(createQuery);
    }

    @Override
    public void insert(SQLiteDatabase db, AdmissionInfo obj) {
        ContentValues values=new ContentValues();
        values.put(SERVER_ID,obj.getServerId());
        values.put(PHOTO,obj.getPhoto());
        values.put(PHOTO_SYNC_STATUS,obj.isPhotoSync());
        values.put(NAME,obj.getName());
        values.put(AGE,obj.getAge());
        values.put(MOTHER_NAME,obj.getMother_name());
        values.put(FATHER_NAME,obj.getFather_name());
        values.put(NO_SIBLINGS,obj.getNoSiblings());
        values.put(OCCUPATION,obj.getOccupation());
        values.put(NATIVE_PLACE,obj.getNativePlace());
        values.put(ENROLLMENT_STATUS,obj.getEnrollment());
        values.put(AADHAR_STATUS,obj.getAadharStatus());
        values.put(AADHAR_REASON,obj.getAadharReason());
        values.put(GENDER,obj.getGender());
        values.put(CATEGORY,obj.getCategory());
        obj.setId(db.insert(ADMISSION_TABLE,null,values));
    }

    @Override
    public void delete(SQLiteDatabase db, AdmissionInfo admissionInfo) {
        String delete="delete from "+ADMISSION_TABLE +" where "+ ID +" = "+admissionInfo.getId();
        db.execSQL(delete);
        admissions.remove(admissionInfo);
    }

    @Override
    public void update(SQLiteDatabase db,AdmissionInfo previous,AdmissionInfo updated) {
        String selectionArgs[]=new String[16];

        selectionArgs[0]=updated.getServerId();
        selectionArgs[1]=updated.getPhoto();
        selectionArgs[2]= previous.getPhoto().equals(updated.getPhoto())&&previous.isPhotoSync()?"1":"0";
        selectionArgs[3]=updated.getName();
        selectionArgs[4]= String.valueOf(updated.getAge());
        selectionArgs[5]=updated.getMother_name();
        selectionArgs[6]=updated.getFather_name();
        selectionArgs[7]= String.valueOf(updated.getNoSiblings());
        selectionArgs[8]=updated.getOccupation();
        selectionArgs[9]=updated.getNativePlace();
        selectionArgs[10]=updated.getEnrollment();
        selectionArgs[11]=updated.getAadharStatus();
        selectionArgs[12]=updated.getAadharReason();
        selectionArgs[13]=updated.getGender();
        selectionArgs[14]=updated.getCategory();
        selectionArgs[15]= String.valueOf(updated.getId());

        String update="update "+ADMISSION_TABLE+" SET "+ SERVER_ID +" =? ,"+
                PHOTO +" =?,"+
                PHOTO_SYNC_STATUS +" =?,"+
                NAME +" =?,"+
                AGE +" =?,"+
                MOTHER_NAME +" =?,"+
                FATHER_NAME +" =?,"+
                NO_SIBLINGS +" =?,"+
                OCCUPATION +" =?,"+
                NATIVE_PLACE +" =?,"+
                ENROLLMENT_STATUS +" =?,"+
                AADHAR_STATUS +" =?,"+
                AADHAR_REASON +" =?,"+
                GENDER +" =?,"+
                CATEGORY +" =?,"+
                "where "+ ID +" = ?";
        db.execSQL(update,selectionArgs);
    }

    @Override
    public ArrayList<AdmissionInfo> getNextRecords(SQLiteDatabase db, int limit) {
        String id="0";
        if (admissions.size()!=0)
            id= String.valueOf(admissions.get(admissions.size()-1).getId());

        if (limit<0||limit>DEFAULT_LIMIT)
            limit=DEFAULT_LIMIT;

        addToList(db.query(ADMISSION_TABLE,null, ID +">?", new String[]{id},
                null,null,null, String.valueOf(limit)),AdmissionTable.admissions);
        return admissions;
    }

    private void addToList(Cursor cursor,ArrayList<AdmissionInfo>admissions) {
        int isPhotoSync,age,siblings;
        long id;
        String serverID,photo,name,category,mother,father,occupation,
                nativePlace,enrollment,aadharStatus, aadharReason,gender;
        if (cursor.moveToFirst()){
            do {
                id=cursor.getLong(cursor.getColumnIndex(ID));
                serverID=cursor.getString(cursor.getColumnIndex(SERVER_ID));
                photo=cursor.getString(cursor.getColumnIndex(PHOTO));
                isPhotoSync=cursor.getInt(cursor.getColumnIndex(PHOTO_SYNC_STATUS));
                name=cursor.getString(cursor.getColumnIndex(NAME));
                age=cursor.getInt(cursor.getColumnIndex(AGE));
                mother=cursor.getString(cursor.getColumnIndex(MOTHER_NAME));
                father=cursor.getString(cursor.getColumnIndex(FATHER_NAME));
                siblings=cursor.getInt(cursor.getColumnIndex(NO_SIBLINGS));
                occupation=cursor.getString(cursor.getColumnIndex(OCCUPATION));
                nativePlace=cursor.getString(cursor.getColumnIndex(NATIVE_PLACE));
                enrollment=cursor.getString(cursor.getColumnIndex(ENROLLMENT_STATUS));
                aadharStatus=cursor.getString(cursor.getColumnIndex(AADHAR_STATUS));
                aadharReason=cursor.getString(cursor.getColumnIndex(AADHAR_REASON));
                gender=cursor.getString(cursor.getColumnIndex(GENDER));
                category=cursor.getString(cursor.getColumnIndex(CATEGORY));
                admissions.add(new AdmissionInfo(id,serverID,photo,isPhotoSync==1,name,age,
                        mother,father,siblings,occupation,nativePlace,enrollment,aadharStatus,aadharReason,gender,category));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    ArrayList<AdmissionInfo> getSearchResult(SQLiteDatabase db,String name){
        ArrayList<AdmissionInfo>temp=new ArrayList<>();

        Cursor cursor=db.query(ADMISSION_TABLE, null, NAME + " LIKE ?",
                new String[] {"%"+ name+ "%" }, null, null, null,
                String.valueOf(DEFAULT_LIMIT));

        addToList(cursor,temp);
        Log.e("debug",""+temp.size());
        return temp;
    }
}
