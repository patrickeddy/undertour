package com.patrickeddy.undertour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrickeddy.undertour.R;
import com.patrickeddy.undertour.model.Location;

import java.util.List;

/**
 * Created by patrickeddy on 3/19/16.
 */
public class LocationAdapter extends BaseAdapter{

    private Context myContext;
    private List<Location> myLocations;
    private LayoutInflater inflater;

    public LocationAdapter(final Context theContext, final List<Location> theLocations) {
        myContext = theContext;
        myLocations = theLocations;
        inflater = LayoutInflater.from(theContext);
    }

    @Override
    public int getCount() {
        return myLocations.size();
    }

    @Override
    public Object getItem(int position) {
        return myLocations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.location_list_row, null);
            holder.title = (TextView) convertView.findViewById(R.id.location_list_row_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText("Location Row: " + position);
        return convertView;
    }
}
