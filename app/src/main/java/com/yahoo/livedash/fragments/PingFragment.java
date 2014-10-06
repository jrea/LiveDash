package com.yahoo.livedash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.livedash.R;
import com.yahoo.livedash.adapters.EventArrayAdapter;
import com.yahoo.livedash.adapters.PingArrayAdapter;
import com.yahoo.livedash.asynctasks.YqlVideoEvents;
import com.yahoo.livedash.handlers.EventsCallbackHandler;
import com.yahoo.livedash.models.Event;
import com.yahoo.livedash.models.Ping;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephr on 10/3/14.
 */
public class PingFragment extends Fragment{
    private static final int MAX_PINGS_TO_SHOW = 10;
    private ArrayList<Ping> pings;
    private ListView lvPings;
    private ArrayAdapter<Ping> aPings;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_pings_list, container, false);
        lvPings = (ListView) v.findViewById(R.id.lv_pings);
        lvPings.setAdapter(aPings);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pings = new ArrayList<Ping>();
        aPings = new PingArrayAdapter(getActivity(), pings);
        load();
    }

    public void load() {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Ping");
            query.setLimit(MAX_PINGS_TO_SHOW);
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> items, ParseException e) {
                    if (e == null) {
                        final ArrayList<Ping> pings = new ArrayList<Ping>();
                        for (int i = items.size() - 1; i >= 0; i--) {
                            final Ping ping = new Ping();
                            //ping.id = messages.get(i).getString(USER_ID_KEY);
                            ping.setName(items.get(i).getString("name"));
                            pings.add(ping);
                        }
                        aPings.clear();
                        addAll(pings);
                        aPings.notifyDataSetChanged();
                        lvPings.invalidate();
                    } else {
                        Log.d("message", "Error: " + e.getMessage());
                    }
                }
            });
    }
    public void addAll(ArrayList<Ping> pings) {
        aPings.addAll(pings);
    }

    public static Fragment newInstance(int page, String title) {
        PingFragment PingFragment = new PingFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        PingFragment.setArguments(args);
        return PingFragment;
    }
}
