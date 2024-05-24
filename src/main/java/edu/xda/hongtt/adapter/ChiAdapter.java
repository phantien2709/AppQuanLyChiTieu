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
import edu.xda.hongtt.fragment.KhoanChiFragment;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.view.ShowSnackBar;

public class ChiAdapter extends RecyclerView.Adapter<ChiAdapter.MyViewHolder> {
    ArrayList<LoaiChi> loaiChiArrayList;
    ArrayList<String> donViTienArrayList;
    private List<Chi> chis;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    int idLoaiChi;
    String donViTien;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    View v;

    public ChiAdapter(Context context,List<Chi> chis) {
        this.chis = chis;
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
    public ChiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_thu,parent,false);
        v = item;
        return new ChiAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ChiAdapter.MyViewHolder holder, int position) {
        final Chi chi = chis.get(position);
        holder.txtNameThu.setText(chi.getTenMucChi());
        holder.txtSoTien.setText(chi.getDinhMucChi() + " " + chi.getDonViChi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhoanChiFragment.showDialogFullChi(mContext, chi);
            }
        });
        holder.imgDeleteThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Bạn có muốn xóa Khoản Chi "+ chi.getTenMucChi() +" này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar.showSnackbar(v, "Xóa thành công");
                        KhoanChiFragment.deleteKhoanChi(chi.getIdChi());
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
                editChi(chi);

            }
        });
    }

    @Override
    public int getItemCount() {
        return chis.size();
    }

    public void editChi(final Chi chi){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_edit_chi);

        ImageView imgClose = dialog.findViewById(R.id.imgCloseEditKhoanChi);
        final EditText edtEditKhoanChi = dialog.findViewById(R.id.edtEditKhoanChi);
        final EditText edtEditSoTienChi = dialog.findViewById(R.id.edtEditSoTienChi);
        final EditText edtEditNgayChi = dialog.findViewById(R.id.edtEditNgayChi);
        final Spinner spShowLoaiChiEdit = dialog.findViewById(R.id.spShowLoaiChiEdit);
        Spinner spShowDonViTienEditChi = dialog.findViewById(R.id.spShowDonViTienEditChi);
        Button btnEditUpKhoanChi = dialog.findViewById(R.id.btnEditUpKhoanChi);


        addItemsToSpinnerLoaiChi(spShowLoaiChiEdit);
        addItemsToSpinnerDonViTien(spShowDonViTienEditChi);

        edtEditKhoanChi.setText(chi.getTenMucChi());
        edtEditSoTienChi.setText(chi.getDinhMucChi());
        edtEditNgayChi.setText(chi.getThoiDiemApDungChi());


        for (int i = 1; i < loaiChiArrayList.size(); i++){
            if (KhoanChiFragment.loaichi(chi.getIdLoaiChi()).equals(loaiChiArrayList.get(i).getTenLoaiChi())){
                spShowLoaiChiEdit.setSelection(i);
            }
        }
        for (int i = 1; i < donViTienArrayList.size(); i++){
            if (chi.getDonViChi().equals(donViTienArrayList.get(i).toString())){
                spShowDonViTienEditChi.setSelection(i);
            }
        }
//        Toast.makeText(mContext, "" +  KhoanChiFragment.loaichi(chi.getIdLoaiChi()), Toast.LENGTH_SHORT).show();

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtEditNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtEditNgayChi);
            }
        });

        btnEditUpKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtKhoanChi = edtEditKhoanChi.getText().toString();
                String edtSoTien = edtEditSoTienChi.getText().toString();
                String edtNgayChi = edtEditNgayChi.getText().toString();
                if (edtKhoanChi.isEmpty()){
                    edtEditKhoanChi.setError("Không được bỏ trống");
                }else if (edtSoTien.isEmpty()){
                    edtEditSoTienChi.setError("Không được bỏ trống");
                }else if (edtNgayChi.isEmpty()){
                    edtEditNgayChi.setError("Không được bỏ trống");
                }else {
                    edtEditKhoanChi.setError(null);
                    edtEditSoTienChi.setError(null);
                    edtEditNgayChi.setError(null);
                    if (idLoaiChi == 0 || donViTien.equals("Chọn")){
                        showSnackBar.showSnackbar(v, "Không được bỏ trống");
                    }else {
                        showSnackBar.showSnackbar(v, "Cập nhật thành công");
                        KhoanChiFragment.editKhoanChi(chi.getIdChi(), edtKhoanChi, edtSoTien, edtNgayChi, idLoaiChi, donViTien);
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
    }
    public void addItemsToSpinnerLoaiChi(Spinner spChonLoaiChi ) {

        loaiChiArrayList = new ArrayList<LoaiChi>();
        loaiChiArrayList.add(new LoaiChi(0,"Chọn"));
        SpLoaiChiAdapter spinAdapter = new SpLoaiChiAdapter(
                mContext, loaiChiArrayList);
        spChonLoaiChi.setAdapter(spinAdapter);
        KhoanChiFragment.dataSpinnerLoaiChi(loaiChiArrayList);
        spChonLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                idLoaiChi = loaiChiArrayList.get(position).getId();
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
