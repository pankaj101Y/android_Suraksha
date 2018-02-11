package com.example.ks.suraksha.Networking;

import android.util.Log;

import com.example.ks.suraksha.AdmissionInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class NetworkManager implements GetAdmissions.GetAdmissionsListener {

    private static NetworkManager networkManager=null;
    private static Queue<AdmissionInfo> pendingAdmissions = new LinkedList<>();
    private static ArrayList<AdmissionInfo> admissions = new ArrayList<>();

    public static NetworkManager getNetworkManager(){
        if (networkManager==null)
            networkManager=new NetworkManager();
        return networkManager;
    }

    public void addPendingAdmission(AdmissionInfo admissionInfo) {
        pendingAdmissions.add(admissionInfo);
    }

    public AdmissionInfo getPendingAdmission() {
        return pendingAdmissions.peek();
    }

    public void removePendingAdmission(AdmissionInfo a) {
        pendingAdmissions.remove(a);
    }

    public ArrayList<AdmissionInfo> getAdmissions(String jsonString) {
        return admissions;
    }

    public  void getNewAdmissions(JSONObject jsonObject){
        GetAdmissions getAdmissionsWorker=new GetAdmissions(this,
                jsonObject.toString());
        getAdmissionsWorker.run();
    }

    //runs on thread
    //called when admissions fetching from server is done
    @Override
    public void onResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.e("debug", "admissions json string= \n" + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: 10-02-2018 /*de jsonated json string and add to admissions*/
    }
}
