package com.example.ks.suraksha.UI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.ks.suraksha.AppData;
import com.example.ks.suraksha.MainActivity;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;
import com.example.ks.suraksha.Utility.ServerContacts;
import com.example.ks.suraksha.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class LoginSignUpActivity extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Spinner userTypeView;
    private ArrayAdapter<String> adapter;
    private static String email=null,password=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        userTypeView =findViewById(R.id.userType);

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,Constants.getUserTypes());
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginOrSignUp();
            }
        });

        userTypeView.setAdapter(adapter);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLoginOrSignUp() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!isPasswordValid(password)) {
            mPasswordView.setError("Please Provide Valid Password");
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Email Is Too Short");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("Invalid Email Address");
            focusView = mEmailView;
            cancel = true;
        }

        if (userTypeView.getSelectedItemPosition()==0) {
            focusView=userTypeView;
            cancel = true;
        }


        if (cancel) focusView.requestFocus();
         else {
            showProgress(true);
            mAuthTask = new UserLoginTask(Constants.getUserTypes()[userTypeView.getSelectedItemPosition()]);


            AppData.signUpLogIn(LoginSignUpActivity.this,email,password);
            Intent mainIntent=new Intent(LoginSignUpActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();

//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            HashMap<String,String>temp=new HashMap<>();
//            temp.put("email",email);
//            temp.put("password",password);
//            mAuthTask.execute(Utility.getJsonObject(temp));
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password){
        return password.length() > 4||!TextUtils.isEmpty(password);
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public class UserLoginTask extends AsyncTask<JSONObject, Void, Boolean> {
        String  requestingUrl=null;


        UserLoginTask(String s) {
            if (s.equals(Constants.getUserTypes()[1]))
                requestingUrl=ServerContacts.SUPER_ADMIN;
            else if (s.equals(Constants.getUserTypes()[2]))
                requestingUrl=ServerContacts.ADMIN;
            else requestingUrl=ServerContacts.USER;
        }

        @Override
        protected Boolean doInBackground(JSONObject... params) {
            boolean isVerified;
            try {
                URL logSignUrl=new URL(requestingUrl);

                HttpURLConnection sendOTPConnection= (HttpURLConnection) logSignUrl.openConnection();

                sendOTPConnection.setDoOutput(true);
                sendOTPConnection.setDoInput(true);
                sendOTPConnection.setRequestMethod("POST");

                sendOTPConnection.setRequestProperty("Content-Type", "application/json");
                sendOTPConnection.setRequestProperty("Accept", "application/json");

                OutputStream phoneOutputStream=sendOTPConnection.getOutputStream();
                BufferedWriter writePhone=new BufferedWriter(new OutputStreamWriter(phoneOutputStream,"UTF-8"));

                writePhone.write(params[0].toString());
                Log.e("debug",params[0].toString());
                writePhone.close();
                phoneOutputStream.close();

                InputStream in = sendOTPConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in,"iso-8859-1");
                BufferedReader OTPSendResponse=new BufferedReader(inputStreamReader);

                StringBuilder data= new StringBuilder();
                String temp;
                while ((temp=OTPSendResponse.readLine())!=null)
                    data.append(temp);

                inputStreamReader.close();
                in.close();

                Log.e("debug",data.toString());
                JSONObject verificationObject=new JSONObject(data.toString());
                isVerified=verificationObject.getString("state").equals(Constants.getSUCCESS());
            }  catch (IOException e) {
                return false;
            } catch (JSONException e) {
                return false;
            }
            return isVerified;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                AppData.signUpLogIn(LoginSignUpActivity.this,email,password);
                Intent mainIntent=new Intent(LoginSignUpActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            } else {
                mPasswordView.setError("Failed Please Try Again");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}