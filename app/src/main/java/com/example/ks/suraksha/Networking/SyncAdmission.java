package com.example.ks.suraksha.Networking;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ks.suraksha.AdmissionInfo;
import com.example.ks.suraksha.Utility.Constants;
import com.example.ks.suraksha.Utility.ServerContacts;

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

public class SyncAdmission extends AsyncTask<AdmissionInfo,Void,String> {

    private AdmissionListener admissionListener=null;
    public SyncAdmission(Context context){
        admissionListener=(AdmissionListener) context;
    }

    @Override
    protected String doInBackground(AdmissionInfo... params) {
        String result = null;
        try {
            URL add=new URL(ServerContacts.getAddAdmission());
            HttpURLConnection addMissionConnection= (HttpURLConnection) add.openConnection();

            addMissionConnection.setConnectTimeout(4000);

            addMissionConnection.setDoOutput(true);
            addMissionConnection.setDoInput(true);
            addMissionConnection.setRequestMethod("POST");

            addMissionConnection.setRequestProperty("Content-Type", "application/json");
            addMissionConnection.setRequestProperty("Accept", "application/json");

            OutputStream outputStream=addMissionConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            bufferedWriter.write(params[0].toString());
            bufferedWriter.close();
            outputStream.close();

            InputStream in = addMissionConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in,"iso-8859-1");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            StringBuilder data= new StringBuilder();
            String temp;
            while ((temp=bufferedReader.readLine())!=null)
                data.append(temp);

            inputStreamReader.close();
            in.close();

            JSONObject jsonObject=new JSONObject(data.toString());
            result=jsonObject.getString("result");
        }  catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals(Constants.getFAILURE()))
            admissionListener.onAdmissionSuccess(s);
        else admissionListener.onAdmissionFailed();
    }

    @Override
    protected void onCancelled() {
        admissionListener=null;
    }

    public interface AdmissionListener{
        void onAdmissionSuccess(String s);
        void onAdmissionFailed();
    }
}
