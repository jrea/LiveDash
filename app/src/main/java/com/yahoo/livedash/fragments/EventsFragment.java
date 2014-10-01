package com.yahoo.livedash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yahoo.livedash.R;
import com.yahoo.livedash.asynctasks.YqlVideoEvents;
import com.yahoo.livedash.handlers.EventsCallbackHandler;
import com.yahoo.livedash.models.Event;
import com.yahoo.livedash.adapters.EventArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josephr on 9/30/14.
 */
public class EventsFragment extends Fragment {
    private ArrayList<Event> events;
    private ArrayAdapter<Event> aEvents;
    private ListView lvEvents;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_events_list, container, false);
        lvEvents = (ListView) v.findViewById(R.id.lv_events);
        lvEvents.setAdapter(aEvents);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<Event>();
        aEvents = new EventArrayAdapter(getActivity(), events);
    }

    public void addAll(ArrayList<Event> tweets) {
        aEvents.addAll(tweets);
    }

    public void load() {
       new YqlVideoEvents(new EventsCallbackHandler() {

           @Override
           public void onSuccess(JSONArray json) {
               Log.i("DEBUG", json.toString());
               addAll(Event.fromJSONArray(json));
           }
       }).execute("http://video.stage.media.yql.yahoo.com/v1/video/events?dev_type=int");
    }
}
