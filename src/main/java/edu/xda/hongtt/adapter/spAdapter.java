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
import edu.xda.hongtt.model.ThangNam;

public class spAdapter extends ArrayAdapter<ThangNam> {
    private Context context;
    private ArrayList<ThangNam> data;
    public Resources res;
    private LayoutInflater inflater;

    public spAdapter(Context context, ArrayList<ThangNam> objects) {
        super(context, R.layout.custom_sp, objects);

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

        View row = inflater.inflate(R.layout.custom_sp, parent, false);
        ThangNam thangNam = data.get(position);
        TextView txtCustomSp = (TextView) row.findViewById(R.id.txtCustomSp);
        txtCustomSp.setText(thangNam.getThangNam());

        return row;
    }

}
