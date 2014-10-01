package com.yahoo.livedash.handlers;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by josephr on 9/30/14.
 */
public abstract class EventsCallbackHandler {
    public abstract void onSuccess(JSONArray response);
}
