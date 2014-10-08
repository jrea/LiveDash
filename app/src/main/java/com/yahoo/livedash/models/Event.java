package com.yahoo.livedash.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josephr on 9/30/14.
 */
public class Event {
    private String description, title, artist_name, venue_city, image;

    public static Event fromJSON(JSONObject json) {
        Event event = new Event();
        try {
            event.description = json.getJSONObject("metadata").getString("description");
            event.title = json.getJSONObject("metadata").getString("title");
            event.artist_name = json.getJSONObject("metadata").getString("artist_name");
            event.venue_city = json.getJSONObject("metadata").getString("venue_city");
            event.image = json.getJSONArray("images").getJSONObject(0).getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }

    public static ArrayList<Event> fromJSONArray(JSONArray json) {
        ArrayList<Event> events = new ArrayList<Event>(json.length());

        for(int i = 0; i < json.length(); i++) {
            JSONObject eventJson = null;
            try{
                eventJson = json.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Log.i("DEBUG", eventJson.toString());
            Event event = Event.fromJSON(eventJson);
            if (event != null) {
                events.add(event);
            }

        }
        return events;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getVenue_city() {
        return venue_city;
    }

    public String getImage() {
        return image;
    }
}
