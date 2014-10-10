package com.yahoo.livedash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.livedash.R;
import com.yahoo.livedash.activities.AlbumActivity;
import com.yahoo.livedash.activities.EventActivity;
import com.yahoo.livedash.activities.ShareActivity;
import com.yahoo.livedash.activities.StreamActivity;
import com.yahoo.livedash.activities.TweetsActivity;
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
    private String title;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_events_list, container, false);
        lvEvents = (ListView) v.findViewById(R.id.lv_events);
        lvEvents.setAdapter(aEvents);
        lvEvents.setLongClickable(true);
        lvEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFilterPopup(view);
                return true;
            }
        });
       return v;
    }

    // Display anchored popup menu based on view selected
    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.action_event_popup, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_activity:
                        Intent i = new Intent(getActivity(), StreamActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.menu_album:
                        Intent intent = new Intent(getActivity(), AlbumActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_share:
                        Intent i2 = new Intent(getActivity(), ShareActivity.class);
                        startActivity(i2);
                        return true;
                    case R.id.menu_tweets:
                        Intent i3 = new Intent(getActivity(), TweetsActivity.class);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<Event>();
        aEvents = new EventArrayAdapter(getActivity(), events);
        title = getArguments().getString("someTitle");
        load();
    }

    public void addAll(ArrayList<Event> events) {
        aEvents.addAll(events);
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

    // newInstance constructor for creating fragment with arguments
    public static EventsFragment newInstance(int page, String title) {
        EventsFragment fragmentEvents = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentEvents.setArguments(args);
        return fragmentEvents;
    }
}
