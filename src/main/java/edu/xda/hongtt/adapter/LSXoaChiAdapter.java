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
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.KhoanThuFragment;
import edu.xda.hongtt.fragment.LichSuXoaChiFragment;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.view.ShowSnackBar;

public class LSXoaChiAdapter extends RecyclerView.Adapter<LSXoaChiAdapter.LSViewHolder> {
    private Context context;
    ArrayList<Chi> chiArrayList;
    ArrayList<LoaiChi> loaiChis;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    View v;
    public LSXoaChiAdapter(Context context , ArrayList<Chi> chiArrayList , ArrayList<LoaiChi> loaiChis){
        this.context = context;
        this.chiArrayList = chiArrayList;
        this.loaiChis = loaiChis;
    }
    @NonNull
    @Override
    public LSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_ls_xoa,parent,false);
        v = view;
        return new LSXoaChiAdapter.LSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LSViewHolder holder, int position) {
        Chi chi = chiArrayList.get(position);
        LoaiChi loaiChi = loaiChis.get(position);
        holder.txtTenLoai.setText(loaiChi.getTenLoaiChi());
        holder.txtTien.setText(chi.getDinhMucChi());
        holder.txtTenKhoan.setText(chi.getTenMucChi());
        holder.txtNgay.setText(chi.getThoiDiemApDungChi());
        holder.txtDonVi.setText(chi.getDonViChi());
        holder.imgXoaVinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa Khoản CHi "+ chi.getTenMucChi() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(v, "Xóa thành công");
                        LichSuXoaChiFragment.XoaVinhVienChi(chi.getIdChi());
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
                builder.setMessage("Bạn có muốn khôi phục lại khoan chi "+ chi.getTenMucChi() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int i = LichSuXoaChiFragment.getDuLie(chi.getIdChi());
                        if(i == 1){
                            showSnackBar.showSnackbar(view, "Khôi phục thành công");
                            LichSuXoaChiFragment.PhucHoiChi(chi.getIdChi());
                        }
                         else{
                            showSnackBar.showSnackbar(view, "Tên loại chi đã bị xóa vui lòng kiểm tra lại để khôi phục");
                        }

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
        return chiArrayList.size();
    }

    class LSViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenLoai, txtTenKhoan , txtTien , txtDonVi, txtNgay ;
        ImageView imgKhoiPhuc, imgXoaVinhVien;
        public LSViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhoan = itemView.findViewById(R.id.txtCustomTen);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTien = itemView.findViewById(R.id.txtCustomSoTien);
            txtDonVi = itemView.findViewById(R.id.txtCustomDonVi);
            txtNgay = itemView.findViewById(R.id.txtCustomNgay);
            imgKhoiPhuc = itemView.findViewById(R.id.imgKhoiPhuc);
            imgXoaVinhVien = itemView.findViewById(R.id.imgDeleteForever);
        }
    }
}
