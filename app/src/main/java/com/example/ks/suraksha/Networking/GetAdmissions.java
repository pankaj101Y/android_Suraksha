package com.example.ks.suraksha.Networking;

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


public class GetAdmissions extends Thread{
    private GetAdmissionsListener getAdmissionsListener;
    private String filterString=null;

    public GetAdmissions(GetAdmissionsListener getAdmissionsListener,String filterString){
        this.getAdmissionsListener=getAdmissionsListener;
        this.filterString=filterString;

    }
    @Override
    public void run() {
        String result;
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

            bufferedWriter.write(filterString);
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
            if (!result.equals(Constants.getFAILURE()))
                getAdmissionsListener.onResult(result);
        }  catch (IOException ignored) {}
        catch (JSONException ignored) {}
    }

    public interface GetAdmissionsListener{
        void onResult(String result);
    }
}
