package edu.xda.hongtt.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.fragment.ThanhVienFragment;
import edu.xda.hongtt.model.ThanhVien;

public class DSTVienAdapter extends RecyclerView.Adapter<DSTVienAdapter.DSTVViewHolder> {

    Context context;
    ArrayList<ThanhVien> thanhVienArrayList;

    public DSTVienAdapter(Context context , ArrayList<ThanhVien> thanhVienArrayList){
        this.context = context;
        this.thanhVienArrayList = thanhVienArrayList;
    }

    @NonNull
    @Override
    public DSTVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_ds_thanhvien,parent,false);
        return new DSTVienAdapter.DSTVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTVViewHolder holder, int position) {
        ThanhVien thanhVien = thanhVienArrayList.get(position);
        holder.txtCustomThanhVien.setText(thanhVien.getHoTen());
        holder.imgCustomChiTietThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhVienFragment.showDialogChiTietThanhVien(context,thanhVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhVienArrayList.size();
    }

    class DSTVViewHolder extends RecyclerView.ViewHolder{

        TextView txtCustomThanhVien;
        ImageView imgCustomChiTietThanhVien;
        public DSTVViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomThanhVien = itemView.findViewById(R.id.txtCustomThanhVien);
            imgCustomChiTietThanhVien = itemView.findViewById(R.id.imgCustomChiTietThanhVien);
        }
    }
}
