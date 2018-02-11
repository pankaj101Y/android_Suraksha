package com.example.ks.suraksha.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.ks.suraksha.AppData;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.UserProfile;

public class ProfileActivity extends AppCompatActivity {
    EditText name,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        final FloatingActionButton fab = findViewById(R.id.fab);

        if (AppData.isUserProfileSetup(this)){
            UserProfile userProfile=AppData.getUserProfile(this);
            name.setText(userProfile.getName());
            number.setText(userProfile.getPhone());
            setViewsState(false);
            fab.setImageResource(R.drawable.lock);
        }else fab.setImageResource(R.drawable.unlock);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.isEnabled()){
                    setViewsState(true);
                    fab.setImageResource(R.drawable.unlock);
                }
                else if (!TextUtils.isEmpty(name.getText().toString())&&!TextUtils.isEmpty(number.getText().toString())){
                    AppData.setUserProfile(ProfileActivity.this,name.getText().toString(),number.getText().toString());
                    setViewsState(false);
                    fab.setImageResource(R.drawable.lock);
                }
            }
        });
    }

    private void setViewsState(boolean state){
        name.setEnabled(state);
        number.setEnabled(state);
    }
}
