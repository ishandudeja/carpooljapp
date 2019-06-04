package com.example.ishan.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Trips> {
    public CustomAdapter(Context context, ArrayList<Trips> trips) {
        super(context, R.layout.custom_row ,trips);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);
        // get references.
        Trips singleTripItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.buckysText);
        TextView datetimeText = (TextView) customView.findViewById(R.id.dateTimeText);
        TextView availableSetText=(TextView) customView.findViewById(R.id.availableSetText);
       // ImageView buckysImage = (ImageView) customView.findViewById(R.id.imageView);

        // dynamically update the text from the array
        itemText.setText(singleTripItem.get_name());
        datetimeText.setText("Timing: " +singleTripItem.get_startDate() +" " +singleTripItem.get_startTime());
        availableSetText.setText("Set Available: "+singleTripItem.get_setAvailable());
        // using the same image every time
        //buckysImage.setImageResource(R.drawable.farrri);
        // Now we can finally return our custom View or custom item
        return customView;
    }
}
