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
import edu.xda.hongtt.model.BinhLuan;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.BLViewHolder> {

    Context context;
    ArrayList<BinhLuan> binhLuanArrayList;

    public BinhLuanAdapter(Context context ,ArrayList<BinhLuan> binhLuanArrayList){
        this.context = context;
        this.binhLuanArrayList = binhLuanArrayList;
    }
    public BinhLuanAdapter(ArrayList<BinhLuan> binhLuanArrayList){
        this.binhLuanArrayList = binhLuanArrayList;
    }
    @NonNull
    @Override
    public BLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_binh_luan, parent, false);
       // LayoutInflater inflater = LayoutInflater.from(context);
       // View view = inflater.inflate(R.layout.custom_binh_luan,parent,false);
        return new BinhLuanAdapter.BLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BLViewHolder holder, int position) {
        BinhLuan binhLuan = binhLuanArrayList.get(position);
        holder.tenTaiKhoan.setText(binhLuan.getTenTaiKhoan());
        holder.noiDungBinhLuan.setText(binhLuan.getNoiDungBinhLuan());
    }

    @Override
    public int getItemCount() {
        return binhLuanArrayList.size();
    }

    class BLViewHolder extends RecyclerView.ViewHolder{

        TextView tenTaiKhoan , noiDungBinhLuan;
        public BLViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTaiKhoan =itemView.findViewById(R.id.txtTenTaiKhoan);
            noiDungBinhLuan = itemView.findViewById(R.id.txtNdBinhLuan);
        }
    }
}
