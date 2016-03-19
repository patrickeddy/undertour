package com.patrickeddy.undertour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patrickeddy.undertour.R;
import com.patrickeddy.undertour.model.Tour;

import java.util.List;

/**
 * Created by patrickeddy on 3/18/16.
 */
public class TourAdapter extends BaseAdapter {

    private Context myContext;
    private List<Tour> myTours;
    private LayoutInflater myInflater;

    public TourAdapter(final Context theC, final List<Tour> theTours) {
        myContext = theC;
        myTours = theTours;
        myInflater = LayoutInflater.from(theC);
    }

    @Override
    public int getCount() {
        return myTours.size();
    }

    @Override
    public Object getItem(int position) {
        return myTours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView title;
        TextView likes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.tour_list_row, null);
            // Get the actual tour for this position.
            Tour tour = ((Tour) getItem(position));
            //TODO: Set the text for this row based on the tours' values.

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
