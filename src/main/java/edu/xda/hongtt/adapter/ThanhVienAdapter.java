package edu.xda.hongtt.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.xda.hongtt.R;
import edu.xda.hongtt.fragment.LoaiChiFragment;
import edu.xda.hongtt.fragment.ThanhVienFragment;
import edu.xda.hongtt.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.MyViewHolder> {
    private ArrayList<ThanhVien> thanhViens;
    private LayoutInflater layoutInflater;
    private Context context;
    public  ThanhVienAdapter(Context context, ArrayList<ThanhVien> thanhViens)
    {
        this.context = context;
        this.thanhViens = thanhViens;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View item = layoutInflater.inflate(R.layout.custom_thanh_vien, parent,false);
        return new ThanhVienAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        final ThanhVien thanhVien = thanhViens.get(position);
        holder.txtThanhVien.setText(thanhVien.getHoTen());
        holder.imgDeleteThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa thành viên " + thanhVien.getHoTen() + " này không" );
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThanhVienFragment.thanhVienDelete(thanhVien.getIdThanhVien());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        holder.imgEditThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVienFragment.editThanhVien(context,thanhVien.getIdThanhVien(),thanhVien.getHoTen(),thanhVien.getVaiVe());
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhViens.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtThanhVien;
        ImageView imgEditThanhVien, imgDeleteThanhVien;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtThanhVien = itemView.findViewById(R.id.txtCustomThanhVien);
            imgEditThanhVien = itemView.findViewById(R.id.imgCustomEditThanhVien);
            imgDeleteThanhVien = itemView.findViewById(R.id.imgCustomDeleteThanhVien);
        }
    }
}
