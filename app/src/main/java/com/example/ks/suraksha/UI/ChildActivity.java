package com.example.ks.suraksha.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ks.suraksha.Adapter.SearchAdapter;
import com.example.ks.suraksha.AdmissionInfo;
import com.example.ks.suraksha.Database.DatabaseManager;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;

import javax.microedition.khronos.opengles.GL;

public class ChildActivity extends AppCompatActivity {
    private AdmissionInfo admissionInfo=null;
    int index;
    TextView name,age,mother,father,siblings,occupation,nativePlace,enrollment,aadhar,
    aadharReason,gender,category;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);


        int sendingActivity=getIntent().getIntExtra("sendingActivity",0);
        index=getIntent().getExtras().getInt("admissionIndex");
        if (sendingActivity==Constants.MAIN)
            admissionInfo=DatabaseManager.getDatabaseManager(this).getAdmissions().get(index);
        else if (sendingActivity==Constants.SEARCHABLE)
            admissionInfo= SearchAdapter.getSearchResult(index);

        name=findViewById(R.id.name_view);
        age=findViewById(R.id.age_view);
        mother=findViewById(R.id.motherName_view);
        father=findViewById(R.id.fatherName_view);
        siblings=findViewById(R.id.siblings_view);
        occupation=findViewById(R.id.occupation_view);
        nativePlace=findViewById(R.id.nativePlace_view);
        enrollment=findViewById(R.id.enrollment_view);
        aadhar=findViewById(R.id.aadhar_view);
        aadharReason=findViewById(R.id.aadharReason_view);
        gender=findViewById(R.id.gender_view);
        category=findViewById(R.id.category_view);
        imageView=findViewById(R.id.childImage);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(ChildActivity.this,AddAdmissionActivity.class);
                editIntent.putExtra("admissionIndex",index);
                startActivity(editIntent);
            }
        });

        setInfo();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo(){
        name.setText("NAME : "+admissionInfo.getName());
        age.setText("AGE : "+Integer.toString(admissionInfo.getAge()));
        mother.setText("MOTHER : "+admissionInfo.getMother_name());
        father.setText("FATHER : "+admissionInfo.getFather_name());
        siblings.setText("NO OF SIBLINGS : "+admissionInfo.getNoSiblings());
        occupation.setText("OCCUPATION : "+admissionInfo.getOccupation());
        nativePlace.setText("NATIVE PLACE : "+admissionInfo.getNativePlace());
        enrollment.setText("ENROLLMENT : "+admissionInfo.getEnrollment());
        aadhar.setText("HAVE AADHAR : "+admissionInfo.getAadharStatus());
        aadharReason.setText("REASON : "+admissionInfo.getAadharReason());
        gender.setText("GENDER : "+admissionInfo.getGender());
        category.setText("CATEGORY : "+admissionInfo.getCategory());

        Glide.with(imageView).load(admissionInfo.getPhoto())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(imageView);
    }

}
