package com.example.app_choferes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app_choferes.R;
import com.example.app_choferes.models.ExpenseType;

import java.util.List;

public class ExpenseTypeListAdapter extends ArrayAdapter<ExpenseType> {

    public ExpenseTypeListAdapter(Activity context, int resouceId, List<ExpenseType> list) {
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
        ExpenseType expenseType = getItem(position);
        viewHolder holder;
        View rowview = convertView;
        StringBuilder descriptionItem = new StringBuilder();

        if (rowview == null) {

            holder = new viewHolder();
            LayoutInflater flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_item_list, null, false);

            holder.txtTitle = rowview.findViewById(R.id.tvDescriptionItem);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        descriptionItem.append(expenseType.getId());
        descriptionItem.append(" - ");
        descriptionItem.append(expenseType.getDescription());
        holder.txtTitle.setText(descriptionItem.toString());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}
