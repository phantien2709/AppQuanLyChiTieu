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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.fragment.LichSuXoaChiFragment;
import edu.xda.hongtt.fragment.LichSuXoaThuFragment;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;
import edu.xda.hongtt.view.ShowSnackBar;

public class LSXoaThuAdapter extends RecyclerView.Adapter<LSXoaThuAdapter.LSViewHolder> {
    Context context;
    ArrayList<Thu> thuArrayList;
    ArrayList<LoaiThu> loaiThuArrayList;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    public LSXoaThuAdapter(Context context , ArrayList<Thu> thuArrayList, ArrayList<LoaiThu> loaiThuArrayList){
        this.context = context;
        this.thuArrayList = thuArrayList;
        this.loaiThuArrayList = loaiThuArrayList;
    }
    @NonNull
    @Override
    public LSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_ls_xoa,parent,false);
        return new LSXoaThuAdapter.LSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LSViewHolder holder, int position) {
        Thu thu = thuArrayList.get(position);
        LoaiThu loaiThu = loaiThuArrayList.get(position);
        holder.txtTenLoai.setText(loaiThu.getTenLoaiThu());
        holder.txtDonVi.setText(thu.getDonViThu());
        holder.txtNgay.setText(thu.getThoiDiemApDungThu());
        holder.txtTenKhoan.setText(thu.getTenMucThu());
        holder.txtTien.setText(thu.getDinhMucThu());
        holder.imgXoaVinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa Khoản Thu "+ thu.getTenMucThu()+" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(view, "Xóa thành công");
                        LichSuXoaThuFragment.XoaVinhVienThu(thu.getIdThu());
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
                builder.setMessage("Bạn có muốn khôi phục lại khoan Thu "+ thu.getTenMucThu() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int i = LichSuXoaThuFragment.getDuLie(thu.getIdThu());
                        if(i == 1){
                            showSnackBar.showSnackbar(view, "Khôi phục thành công");
                            LichSuXoaThuFragment.PhucHoiThu(thu.getIdThu());
                        }
                        else{
                            showSnackBar.showSnackbar(view, "Tên loại thu đã bị xóa vui lòng kiểm tra lại để khôi phục");
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
        return thuArrayList.size();
    }

    class LSViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenLoai, txtTenKhoan , txtTien , txtDonVi, txtNgay ;
        ImageView imgXoaVinhVien , imgKhoiPhuc;
        public LSViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhoan = itemView.findViewById(R.id.txtCustomTen);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTien = itemView.findViewById(R.id.txtCustomSoTien);
            txtDonVi = itemView.findViewById(R.id.txtCustomDonVi);
            txtNgay = itemView.findViewById(R.id.txtCustomNgay);
            imgKhoiPhuc = itemView.findViewById((R.id.imgKhoiPhuc));
            imgXoaVinhVien = itemView.findViewById(R.id.imgDeleteForever);
        }
    }
}
