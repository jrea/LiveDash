package com.yahoo.livedash.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

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
    // Tracks current contextual action mode
    private ActionMode currentActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        events = new ArrayList<Event>();
        aEvents = new EventWithExtrasArrayAdapter(this, events);

        ListView lv_events = (ListView) findViewById(R.id.lv_event_activity);
        lv_events.setAdapter(aEvents);
        load();
        lv_events.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFilterPopup(view);
                //Toast.makeText(EventActivity.this, "You've got an event on " + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void load() {
        new YqlVideoEvents(new EventsCallbackHandler() {

            @Override
            public void onSuccess(JSONArray json) {
                aEvents.addAll(Event.fromJSONArray(json));
            }
        }).execute("http://video.stage.media.yql.yahoo.com/v1/video/events?dev_type=int");
    }

    public void goToProfile(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void goToEvents(MenuItem item) {
        //do nothing
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
        return super.onOptionsItemSelected(item);
    }

    // Display anchored popup menu based on view selected
    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.action_event_popup, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_activity:
                        Intent i = new Intent(EventActivity.this, StreamActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.menu_album:
                        Intent intent = new Intent(EventActivity.this, AlbumActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_share:
                        Intent i2 = new Intent(EventActivity.this, ShareActivity.class);
                        startActivity(i2);
                        return true;
                    case R.id.menu_tweets:
                        Intent i3 = new Intent(EventActivity.this, TweetsActivity.class);
                        startActivity(i3);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }
}
