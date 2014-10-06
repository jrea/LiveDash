package com.yahoo.livedash.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josephr on 10/3/14.
 */
public class Ping {
    private String name;

    public static Ping fromJSON(JSONObject json) {
        Ping ping = new Ping();
        try {
            ping.name = json.getString("name");
            ping.name = "LOL TROLLFACE";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ping;
    }
    public static ArrayList<Ping> fromJSONArray(JSONArray json) {
        ArrayList<Ping> pings = new ArrayList<Ping>(json.length());

        for(int i = 0; i < json.length(); i++) {
            JSONObject eventJson = null;
            try{
                /*
                Get the parse data for this
                 */
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Log.i("DEBUG", eventJson.toString());
            Ping ping = Ping.fromJSON(eventJson);
            if (ping != null) {
                pings.add(ping);
            }

        }
        return pings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
