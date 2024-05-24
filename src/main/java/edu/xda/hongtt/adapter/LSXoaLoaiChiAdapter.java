package edu.xda.hongtt.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.fragment.LSXoaLoaiChiFragment;
import edu.xda.hongtt.fragment.LichSuXoaChiFragment;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.view.ShowSnackBar;

public class LSXoaLoaiChiAdapter extends RecyclerView.Adapter<LSXoaLoaiChiAdapter.LSViewHolder> {
    Context context;
    ArrayList<LoaiChi> loaiChiArrayList;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    public LSXoaLoaiChiAdapter(Context context , ArrayList<LoaiChi> loaiChiArrayList){
        this.context = context;
        this.loaiChiArrayList = loaiChiArrayList;
    }
    @NonNull
    @Override
    public LSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_ls_xoa_loai,parent,false);
        return new LSXoaLoaiChiAdapter.LSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LSViewHolder holder, int position) {
        LoaiChi loaiChi = loaiChiArrayList.get(position);
        holder.txtTenLoai.setText(loaiChi.getTenLoaiChi());
        holder.imgXoaVinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa Loai Chi "+ loaiChi.getTenLoaiChi() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(view, "Xóa thành công");
                        LSXoaLoaiChiFragment.XoaVinhVienChi(loaiChi.getId());
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
        holder.imgKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn khôi phục lại Loai Chi "+ loaiChi.getTenLoaiChi() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(view, "Khôi phục thành công");
                        LSXoaLoaiChiFragment.PhucHoiChi(loaiChi.getId());
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
    }

    @Override
    public int getItemCount() {
        return loaiChiArrayList.size();
    }

    class LSViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenLoai;
        ImageView imgXoaVinhVien , imgKhoiPhuc;
        public LSViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            imgXoaVinhVien = itemView.findViewById(R.id.imgDeleteForever);
            imgKhoiPhuc = itemView.findViewById(R.id.imgKhoiPhuc);
        }
    }
}
