package com.example.ks.suraksha.UI;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ks.suraksha.AdmissionInfo;
import com.example.ks.suraksha.Database.DatabaseManager;
import com.example.ks.suraksha.Networking.SyncAdmission;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;

import java.io.File;
import java.io.IOException;

public class AddAdmissionActivity extends AppCompatActivity implements SyncAdmission.AdmissionListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Spinner enrollStatusSpinner, aadharSpinner,genderSpinner,categorySpinner;
    private ImageView childImage;
    private static File childImageFile=null;

    private EditText name,age,mother,father,siblings,occupation, nativePlace;

    private SyncAdmission addAdmission;
    private AdmissionInfo admissionInfo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admission);

        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        mother=findViewById(R.id.motherName);
        father=findViewById(R.id.fatherName);
        siblings=findViewById(R.id.siblings);
        occupation=findViewById(R.id.familyOccupation);
        nativePlace=findViewById(R.id.nativePlace);

        childImage =findViewById(R.id.childImage);

        enrollStatusSpinner = findViewById(R.id.enrollment_status_spinner);
        aadharSpinner = findViewById(R.id.aadhar_spinner);
        genderSpinner=findViewById(R.id.genderSpinner);
        categorySpinner=findViewById(R.id.categorySpinner);

        ArrayAdapter<String> enrollAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getEnrollOps());
        ArrayAdapter<String> aadharAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getAadharOps());
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getGender());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.getCategory());

        enrollStatusSpinner.setAdapter(enrollAdapter);
        aadharSpinner.setAdapter(aadharAdapter);
        genderSpinner.setAdapter(genderAdapter);
        categorySpinner.setAdapter(categoryAdapter);

        if (childImageFile!=null) loadImage();
    }

    public void addChild(View view) {
        if (childImageFile==null){
            Toast.makeText(getApplicationContext(),"PROVIDE PHOTO",Toast.LENGTH_LONG).show();
            return;
        }
        String n=name.getText().toString();
        String ag=age.getText().toString();
        String mn=mother.getText().toString();
        String fn=father.getText().toString();
        String sib=siblings.getText().toString();
        String occ=occupation.getText().toString();
        String nativeP=nativePlace.getText().toString();

        if (TextUtils.isEmpty(n)||TextUtils.isEmpty(ag)||TextUtils.isEmpty(mn)||TextUtils.isEmpty(fn)
                ||TextUtils.isEmpty(sib)||TextUtils.isEmpty(occ)||TextUtils.isEmpty(nativeP)
                ||(enrollStatusSpinner.getSelectedItemPosition()<=0&&aadharSpinner.getSelectedItemPosition()<=0&&
                genderSpinner.getSelectedItemPosition()<=0&&categorySpinner.getSelectedItemPosition()<=0)){
            Toast.makeText(getApplicationContext(),"PLEASE PROVIDE ALL FIELDS",Toast.LENGTH_LONG).show();
            return;
        }

        admissionInfo=new AdmissionInfo(
                -1,null,childImageFile.getAbsolutePath(),false,n,Integer.parseInt(ag),mn,fn,Integer.parseInt(sib),occ,nativeP,
                Constants.getEnrollOps()[enrollStatusSpinner.getSelectedItemPosition()],
                Constants.getAadharOps()[aadharSpinner.getSelectedItemPosition()],
                "aadharReason",
                Constants.getGender()[genderSpinner.getSelectedItemPosition()],
                Constants.getCategory()[categorySpinner.getSelectedItemPosition()]
        );

        DatabaseManager.getDatabaseManager(this).insertAdmission(admissionInfo);
        addAdmission=new SyncAdmission(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void addChildImage(View view) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                childImageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (childImageFile!= null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.ks.suraksha",
                        childImageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File createImageFile() throws IOException {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String imageFileName = "IMG" + timeStamp;
        File storageDir = new File(Constants.getImageDirectory());
        if (!storageDir.isDirectory())
            storageDir.mkdirs();
        return new File(Constants.getImageDirectory() + imageFileName + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            loadImage();
        }
    }

    private void loadImage(){
        Glide.with(childImage)
                .load(childImageFile)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(childImage);
    }

    @Override
    public void finish() {
        super.finish();
        childImageFile=null;
    }

    @Override
    public void onAdmissionSuccess(String serverId) {
        try {
            AdmissionInfo update=admissionInfo.clone();
            update.setServerId(serverId);
            DatabaseManager.getDatabaseManager(this).updateAdmission(admissionInfo,update);
            finish();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdmissionFailed() {}
}
