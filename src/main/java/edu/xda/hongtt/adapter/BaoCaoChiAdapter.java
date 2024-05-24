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
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;

public class BaoCaoChiAdapter extends RecyclerView.Adapter<BaoCaoChiAdapter.BCViewHolder> {
    Context context;
    ArrayList<Chi>chiArrayList;
    ArrayList<LoaiChi> loaiChiArrayList;
    public BaoCaoChiAdapter(Context context , ArrayList<Chi> chiArrayList){
        this.context = context;
        this.chiArrayList = chiArrayList;
    }
    public BaoCaoChiAdapter(Context context, ArrayList<Chi> chiArrayList, ArrayList<LoaiChi> loaiChiArrayList){
        this.context = context;
        this.chiArrayList = chiArrayList;
        this.loaiChiArrayList = loaiChiArrayList;
    }
    @NonNull
    @Override
    public BCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_bao_cao,parent,false);
        return new BCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BCViewHolder holder, int position) {
        Chi chi = chiArrayList.get(position);
        LoaiChi loaiChi = loaiChiArrayList.get(position);
        holder.txtTenLoai.setText(loaiChi.getTenLoaiChi());
        holder.txtTien.setText(chi.getDinhMucChi());
        holder.txtTenKhoan.setText(chi.getTenMucChi());
        holder.txtNgay.setText(chi.getThoiDiemApDungChi());
        holder.txtDonVi.setText(chi.getDonViChi());

    }

    @Override
    public int getItemCount() {
        return chiArrayList.size();
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
