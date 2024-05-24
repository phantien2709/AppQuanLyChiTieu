package edu.xda.hongtt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;

public class BaoCaoThuAdapter extends RecyclerView.Adapter<BaoCaoThuAdapter.BCViewHolder> {
    Context context;
    ArrayList<Thu> thuArrayList;
    ArrayList<LoaiThu> loaiThuArrayList;
    public BaoCaoThuAdapter(Context context, ArrayList<Thu> thuArrayList , ArrayList<LoaiThu> loaiThuArrayList){
        this.context = context;
        this.loaiThuArrayList = loaiThuArrayList;
        this.thuArrayList = thuArrayList;
    }
    @NonNull
    @Override
    public BaoCaoThuAdapter.BCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_bao_cao,parent,false);
        return new BaoCaoThuAdapter.BCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaoCaoThuAdapter.BCViewHolder holder, int position) {
        Thu thu = thuArrayList.get(position);
        LoaiThu loaiThu = loaiThuArrayList.get(position);
        holder.txtDonVi.setText(thu.getDonViThu());
        holder.txtNgay.setText(thu.getThoiDiemApDungThu());
        holder.txtTenKhoan.setText(thu.getTenMucThu());
        holder.txtTien.setText(thu.getDinhMucThu());
        holder.txtTenLoai.setText(loaiThu.getTenLoaiThu());
    }

    @Override
    public int getItemCount() {
        return thuArrayList.size();
    }

    class BCViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenLoai, txtTenKhoan , txtTien , txtDonVi, txtNgay;
        public BCViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhoan = itemView.findViewById(R.id.txtCustomTen);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTien = itemView.findViewById(R.id.txtCustomSoTien);
            txtDonVi = itemView.findViewById(R.id.txtCustomDonVi);
            txtNgay = itemView.findViewById(R.id.txtCustomNgay);

        }
    }
}
