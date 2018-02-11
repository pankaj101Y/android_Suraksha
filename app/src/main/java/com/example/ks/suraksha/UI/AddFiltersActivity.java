package com.example.ks.suraksha.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;
import com.example.ks.suraksha.Utility.FiltersHolder;

import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_1;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_2;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.AGE_GROUP_3;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.DROP_OUT;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.HAVE_AADHAR;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.NOT_HAVE_AADHAR;
import static com.example.ks.suraksha.Utility.FiltersHolder.Filters.SCHOOL_GOING;


public class AddFiltersActivity extends AppCompatActivity {
    private Spinner enrollStatusSpinner, ageSpinner, aadharSpinner;
    private static int enroll=0,age=0,aadhar=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filters);
        enrollStatusSpinner = findViewById(R.id.enrollment_status_spinner);
        ageSpinner = findViewById(R.id.age_spinner);
        aadharSpinner = findViewById(R.id.aadhar_spinner);

        ArrayAdapter<String> enrollAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getEnrollOps());
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getAgeOps());
        ArrayAdapter<String> aadharAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getAadharOps());
        enrollStatusSpinner.setAdapter(enrollAdapter);
        ageSpinner.setAdapter(ageAdapter);
        aadharSpinner.setAdapter(aadharAdapter);

        enrollStatusSpinner.setSelection(enroll);
        ageSpinner.setSelection(age);
        aadharSpinner.setSelection(aadhar);
    }

    public void addFilters(View view) {
        FiltersHolder filtersHolder=FiltersHolder.getFiltersHolder();
        boolean isFilterApplicable=false;
        if (enroll!=enrollStatusSpinner.getSelectedItemPosition()){
            enroll=enrollStatusSpinner.getSelectedItemPosition();
            if (enroll==0){
                filtersHolder.removeFilter(DROP_OUT);
                filtersHolder.removeFilter(SCHOOL_GOING);
            }
            isFilterApplicable=true;
        }

        if (age!=ageSpinner.getSelectedItemPosition()){
            age=ageSpinner.getSelectedItemPosition();
            if (age==0){
                filtersHolder.removeFilter(AGE_GROUP_1);
                filtersHolder.removeFilter(AGE_GROUP_2);
                filtersHolder.removeFilter(AGE_GROUP_3);
            }
            isFilterApplicable=true;
        }


        if (aadhar!=aadharSpinner.getSelectedItemPosition()){
            aadhar=aadharSpinner.getSelectedItemPosition();
            if (aadhar==0){
                filtersHolder.removeFilter(HAVE_AADHAR);
                filtersHolder.removeFilter(NOT_HAVE_AADHAR);
            }
            isFilterApplicable=true;
        }

        if (enroll==1)
            filtersHolder.putFilter(DROP_OUT);
        else if (enroll==2)
            filtersHolder.putFilter(SCHOOL_GOING);
        else if (age==1)
            filtersHolder.putFilter(AGE_GROUP_1);
        else if (age==2)
            filtersHolder.putFilter(AGE_GROUP_2);
        else if (age==3)
            filtersHolder.putFilter(AGE_GROUP_3);
        else if (aadhar==1)
            filtersHolder.putFilter(HAVE_AADHAR);
        else if (aadhar==2)
            filtersHolder.putFilter(NOT_HAVE_AADHAR);



        Intent filterResultIntent=new Intent();
        filterResultIntent.putExtra("isFilterHasResult",isFilterApplicable);
        setResult(Activity.RESULT_OK,filterResultIntent);
        finish();
    }
}