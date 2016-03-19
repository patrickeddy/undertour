package com.patrickeddy.undertour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrickeddy.undertour.R;
import com.patrickeddy.undertour.model.TourLocation;

import java.util.List;

/**
 * Created by patrickeddy on 3/19/16.
 */
public class EditTourAdapter extends BaseAdapter{

    private Context myContext;
    private List<TourLocation> myTourLocations;
    private LayoutInflater inflater;

    public EditTourAdapter(final Context theContext, final List<TourLocation> theTourLocations) {
        myContext = theContext;
        myTourLocations = theTourLocations;
        inflater = LayoutInflater.from(theContext);
    }

    @Override
    public int getCount() {
        return myTourLocations.size();
    }

    @Override
    public Object getItem(int position) {
        return myTourLocations.get(position);
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
            convertView = inflater.inflate(R.layout.edit_list_row, null);
            holder.title = (TextView) convertView.findViewById(R.id.edit_tour_list_row_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TourLocation currentLocation = myTourLocations.get(position);
        holder.title.setText(currentLocation.getName());
        return convertView;
    }
}
