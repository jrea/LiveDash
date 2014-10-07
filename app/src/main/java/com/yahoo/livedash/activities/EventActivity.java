package com.yahoo.livedash.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yahoo.livedash.R;
import com.yahoo.livedash.adapters.EventArrayAdapter;
import com.yahoo.livedash.adapters.EventWithExtrasArrayAdapter;
import com.yahoo.livedash.asynctasks.YqlVideoEvents;
import com.yahoo.livedash.handlers.EventsCallbackHandler;
import com.yahoo.livedash.models.Event;

import org.json.JSONArray;

import java.util.ArrayList;

public class EventActivity extends Activity {
    private ArrayList<Event> events;
    private EventWithExtrasArrayAdapter aEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        events = new ArrayList<Event>();
        aEvents = new EventWithExtrasArrayAdapter(this, events);

        ListView lv_events = (ListView) findViewById(R.id.lv_event_activity);
        lv_events.setAdapter(aEvents);
        load();
    }

    public void load() {
        new YqlVideoEvents(new EventsCallbackHandler() {

            @Override
            public void onSuccess(JSONArray json) {
                Log.i("DEBUG", json.toString());
                aEvents.addAll(Event.fromJSONArray(json));
            }
        }).execute("http://video.stage.media.yql.yahoo.com/v1/video/events?dev_type=int");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
