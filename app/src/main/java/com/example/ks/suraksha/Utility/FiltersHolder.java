package com.example.ks.suraksha.Utility;

import android.util.Log;
import android.util.SparseBooleanArray;

import com.example.ks.suraksha.AdmissionInfo;

import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.FEMALE;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.HAVE_AADHAR;


public class FiltersHolder {

    private SparseBooleanArray booleanArray =new SparseBooleanArray();
    private static FiltersHolder filtersHolder=null;

    public static FiltersHolder getFiltersHolder() {
        if (filtersHolder==null)
            filtersHolder=new FiltersHolder();
        return filtersHolder;
    }

    public void putFilter(int filterName){
        if (filterName>=HAVE_AADHAR&&filterName<=FEMALE)
            booleanArray.put(filterName,true);
    }

    public void removeFilter(int filterName){
        if (filterName>=HAVE_AADHAR&&filterName<=FEMALE)
            booleanArray.delete(filterName);
    }

    public boolean getFilter(int filterName){
        return booleanArray.get(filterName,false);
    }

    public static class Filters{
        public static final int HAVE_AADHAR=0;
        public static final int NOT_HAVE_AADHAR=1;
        public static final int AGE_GROUP_1=2;//4-10
        public static final int AGE_GROUP_2=3;//11-15
        public static final int AGE_GROUP_3=4;//16-20
        public static final int DROP_OUT=5;
        public static final int SCHOOL_GOING=6;
        public static final int MALE=7;
        public static final int FEMALE=8;
    }
}
