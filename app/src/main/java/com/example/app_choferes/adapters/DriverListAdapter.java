package com.example.app_choferes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app_choferes.R;
import com.example.app_choferes.models.Driver;

import java.util.List;

public class DriverListAdapter extends ArrayAdapter<Driver> {

    public DriverListAdapter(Activity context, int resouceId, List<Driver> list) {
        super(context, resouceId, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        Driver driver = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            LayoutInflater flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.driver_item_list, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.tvDriverName);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(driver.getName());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}
