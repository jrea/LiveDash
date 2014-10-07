package com.yahoo.livedash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yahoo.livedash.R;
import com.yahoo.livedash.models.Event;

import java.util.List;

/**
 * Created by josephr on 9/30/14.
 */
public class EventWithExtrasArrayAdapter extends ArrayAdapter<Event> {
    public EventWithExtrasArrayAdapter(Context context, List<Event> events) {
        super(context, 0 , events);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);
        View v;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.event_item_with_extras, parent, false);
        } else {
            v = convertView;
        }
        TextView tv_name = (TextView) v.findViewById(R.id.tv_event_name);

        tv_name.setText(event.getTitle());

        return v;
    }
}
