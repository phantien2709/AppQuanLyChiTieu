package edu.xda.hongtt.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.xda.hongtt.R;
import edu.xda.hongtt.fragment.KhoanThuFragment;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;
import edu.xda.hongtt.view.ShowSnackBar;

public class ThuAdapter extends RecyclerView.Adapter<ThuAdapter.MyViewHolder> {
    ArrayList<LoaiThu> loaiThuArrayList;
    ArrayList<String> donViTienArrayList;
    private List<Thu> thus;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    int idLoaiThu;
    String donViTien;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    View v;
    public ThuAdapter(Context context,List<Thu> thus) {
        this.thus = thus;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameThu, txtSoTien;
        ImageView imgEdtThu, imgDeleteThu;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameThu = itemView.findViewById(R.id.txtCustomTenThu);
            txtSoTien = itemView.findViewById(R.id.txtCustomSoTienThu);
            imgEdtThu = itemView.findViewById(R.id.imgCustomEditThu);
            imgDeleteThu = itemView.findViewById(R.id.imgCustomDeleteThu);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_thu,parent,false);
        v = item;
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ThuAdapter.MyViewHolder holder, int position) {
        final Thu thu = thus.get(position);
        holder.txtNameThu.setText(thu.getTenMucThu());
        holder.txtSoTien.setText(thu.getDinhMucThu() + " " + thu.getDonViThu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhoanThuFragment.showDialogFullThu(mContext,thu);
            }
        });
        holder.imgDeleteThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Bạn có muốn xóa Khoản Thu "+ thu.getTenMucThu() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(v, "Xóa thành công");
                        KhoanThuFragment.deleteKhoanThu(thu.getIdThu());
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

        holder.imgEdtThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editThu(thu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thus.size();
    }

    public void editThu(final Thu thu){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_edit_thu);

        ImageView imgClose = dialog.findViewById(R.id.imgCloseEditKhoanThu);
        final EditText edtEditKhoanThu = dialog.findViewById(R.id.edtEditKhoanThu);
        final EditText edtEditSoTien = dialog.findViewById(R.id.edtEditSoTien);
        final EditText edtEditNgayThu = dialog.findViewById(R.id.edtEditNgayThu);
        final Spinner spShowLoaiThuEdit = dialog.findViewById(R.id.spShowLoaiThuEdit);
        Spinner spShowDonViTienEdit = dialog.findViewById(R.id.spShowDonViTienEdit);
        Button btnEditUpKhoanThu = dialog.findViewById(R.id.btnEditUpKhoanThu);


        addItemsToSpinnerLoaiThu(spShowLoaiThuEdit);
        addItemsToSpinnerDonViTien(spShowDonViTienEdit);

        edtEditKhoanThu.setText(thu.getTenMucThu());
        edtEditSoTien.setText(thu.getDinhMucThu());
        edtEditNgayThu.setText(thu.getThoiDiemApDungThu());
        for (int i = 1; i < loaiThuArrayList.size(); i++){
            if (KhoanThuFragment.loaiThu(thu.getIdLoaiThu()).equals(loaiThuArrayList.get(i).getTenLoaiThu())){
                spShowLoaiThuEdit.setSelection(i);
            }
        }
        for (int i = 1; i < donViTienArrayList.size(); i++){
            if (thu.getDonViThu().equals(donViTienArrayList.get(i).toString())){
                spShowDonViTienEdit.setSelection(i);
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtEditNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtEditNgayThu);
            }
        });

        btnEditUpKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtKhoanthu = edtEditKhoanThu.getText().toString();
                String edtSoTien = edtEditSoTien.getText().toString();
                String edtNgayThu = edtEditNgayThu.getText().toString();
                if (edtKhoanthu.isEmpty()){
                    edtEditKhoanThu.setError("Không được bỏ trống");
                }else if (edtSoTien.isEmpty()){
                    edtEditSoTien.setError("Không được bỏ trống");
                }else if (edtNgayThu.isEmpty()){
                    edtEditNgayThu.setError("Không được bỏ trống");
                }else {
                    edtEditKhoanThu.setError(null);
                    edtEditSoTien.setError(null);
                    edtEditNgayThu.setError(null);
                    if (idLoaiThu == 0 || donViTien.equals("Chọn")){
                        showSnackBar.showSnackbar(v, "Không được bỏ trống");
                    }else {
                        showSnackBar.showSnackbar(v, "Cập nhật thành công");
                        KhoanThuFragment.editKhoanThu(thu.getIdThu(), edtKhoanthu, edtSoTien, edtNgayThu, idLoaiThu, donViTien);
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
    }
    public void addItemsToSpinnerLoaiThu(Spinner spChonLoaiThu ) {

        loaiThuArrayList = new ArrayList<LoaiThu>();
        loaiThuArrayList.add(new LoaiThu(0,"Chọn"));

        KhoanThuFragment.dataSpinnerLoaiThu(loaiThuArrayList);
        SpLoaiThuAdapter spinAdapter = new SpLoaiThuAdapter(
                mContext, loaiThuArrayList);
        spChonLoaiThu.setAdapter(spinAdapter);

        spChonLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                idLoaiThu = loaiThuArrayList.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    public void addItemsToSpinnerDonViTien(Spinner spChonDonVi) {
        donViTienArrayList = new ArrayList<String>();
        donViTienArrayList.add("Chọn");
        donViTienArrayList.add("VND");
        donViTienArrayList.add("USD");

        SpDonViTienAdapter spinAdapter = new SpDonViTienAdapter(
                mContext, donViTienArrayList);
        spChonDonVi.setAdapter(spinAdapter);
        spChonDonVi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                donViTien = donViTienArrayList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    private void chonNgay(final EditText chonNgay){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}