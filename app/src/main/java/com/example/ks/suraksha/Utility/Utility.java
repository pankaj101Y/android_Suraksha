package com.example.ks.suraksha.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pankaj kumar on 10-02-2018.
 */

public class Utility {

    public static JSONObject getJsonObject(HashMap<String,String> keyValues){
        JSONObject jsonObject=new JSONObject();
        Set<String > keys=keyValues.keySet();
        for (String key : keys) {
            try {
                jsonObject.put(key, keyValues.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
