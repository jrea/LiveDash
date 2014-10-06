package com.yahoo.livedash.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yahoo.livedash.R;
import com.yahoo.livedash.models.Event;
import com.yahoo.livedash.models.Ping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephr on 10/3/14.
 */
public class PingArrayAdapter extends ArrayAdapter<Ping> {
    public PingArrayAdapter(Context context, List<Ping> pings) {
        super(context, 0 , pings);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Ping ping = getItem(position);
        View v;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.ping_item, parent, false);
        } else {
            v = convertView;
        }
        TextView tv_name = (TextView) v.findViewById(R.id.tv_ping_name);

        tv_name.setText(ping.getName());

        return v;
    }
}
