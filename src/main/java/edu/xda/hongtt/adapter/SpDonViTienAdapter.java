package edu.xda.hongtt.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.xda.hongtt.R;

public class SpDonViTienAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> data;
    public Resources res;
    private LayoutInflater inflater;

    public SpDonViTienAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.custom_sp_don_vi_tien, objects);

        this.context = context;
        data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.custom_sp_don_vi_tien, parent, false);

        TextView txtCustomSpDonViTien = (TextView) row.findViewById(R.id.txtCustomSpDonViTien);
        txtCustomSpDonViTien.setText(data.get(position).toString());

        return row;
    }

}
