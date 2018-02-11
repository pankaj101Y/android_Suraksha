package com.example.ks.suraksha.Database;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

interface Table<T>{
//    this interface is implemented by each table


    //maximum no of records that can be retrieve from table by one query
    int DEFAULT_LIMIT=50;

    /**
     * this method create the table whichever table implements it
     *
     * @param db database in which table is to be created
     */

    void create(SQLiteDatabase db);

    /**
     *
     * @param db    database object to which the implementing belongs
     * @param obj   object of type which the table can save
     */
    void insert(SQLiteDatabase db, T obj);


    /**
     * this method deletes int row from table with given id
     * there should be a id field in implementing table
     *
     * @param db  database object to which the implementing belongs
     * @param t  object whose record to be deleted from table
     */
    void delete(SQLiteDatabase db, T t);


    /**
     *
     * @param db           database object to which the implementing belongs
     * @param previous     previous object whose corresponding row has to be updated
     * @param updated      updated object with whose attrs row has to update
     */
    void update(SQLiteDatabase db, T previous, T updated);


    /**
     *
     * @param db           database object to which the implementing belongs
     * @param limit        the maximum no of new records to be retrieve
     *                     if limit< 0 or limit > DEFAULT_LIMIT
     *                     then limit =DEFAULT_LIMIT
     * @return             updated array-list with new records
     */
    ArrayList<T> getNextRecords(SQLiteDatabase db, int limit);

}
