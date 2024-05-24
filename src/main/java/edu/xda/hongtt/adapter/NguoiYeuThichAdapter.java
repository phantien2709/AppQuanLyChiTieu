package edu.xda.hongtt.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.model.YeuThich;

public class NguoiYeuThichAdapter extends RecyclerView.Adapter<NguoiYeuThichAdapter.NYTViewHolder>{
    Context context;
    ArrayList<YeuThich> yeuThichArrayList;

    public NguoiYeuThichAdapter(Context context , ArrayList<YeuThich> yeuThichArrayList){
        this.context = context;
        this.yeuThichArrayList = yeuThichArrayList;
    }
    @NonNull
    @Override
    public NYTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_nguoi_da_yeu_thich,parent,false);
        return new NguoiYeuThichAdapter.NYTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NYTViewHolder holder, int position) {
        YeuThich yeuThich = yeuThichArrayList.get(position);
        holder.tenTaiKhoan.setText(yeuThich.getTenTaiKhoan());
    }

    @Override
    public int getItemCount() {
        return yeuThichArrayList.size();
    }

    class NYTViewHolder extends RecyclerView.ViewHolder {

        TextView tenTaiKhoan;
        public NYTViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTaiKhoan = itemView.findViewById(R.id.txtTenTaiKhoan);
        }
    }
}
