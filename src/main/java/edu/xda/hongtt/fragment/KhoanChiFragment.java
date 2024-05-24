package edu.xda.hongtt.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ChiAdapter;
import edu.xda.hongtt.adapter.SpDonViTienAdapter;
import edu.xda.hongtt.adapter.SpLoaiChiAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.view.ShowSnackBar;

public class KhoanChiFragment extends Fragment {
    FloatingActionButton fabThemKhoanChi;
    static ArrayList<LoaiChi> loaiChiArrayList;
    ArrayList<String> donViTienArrayList;
    static ArrayList<Chi> chiArrayList;
    RecyclerView recyclerViewChi;
    static ChiAdapter chiAdapter;
    int idLoaiChi;
    String donViTien;
    static MyDatabaseHelper database;
    ShowSnackBar showSnackBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_khoan_chi, container, false);
        addControls(v);

        addEvents();

        return v;
    }
    private void addEvents() {
        fabThemKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKhoanChiDialog();
            }
        });
    }
    private void addControls(View v) {
        fabThemKhoanChi = v.findViewById(R.id.fabThemKhoanChi);
        recyclerViewChi = (RecyclerView) v.findViewById(R.id.recyclerViewChi);
        database = new MyDatabaseHelper(getContext());
        chiArrayList = new ArrayList<>();
        showSnackBar = new ShowSnackBar();
        chiAdapter = new ChiAdapter(getContext(),chiArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewChi.setLayoutManager(layoutManager);
        recyclerViewChi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChi.setAdapter(chiAdapter);
        loadData();
    }
    private void addKhoanChiDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_chi);

        ImageView imgClose = dialog.findViewById(R.id.imgCloseThemKhoanChi);
        final EditText edtThemKhoanChi = dialog.findViewById(R.id.edtThemKhoanChi);
        final EditText edtThemSoTienChi = dialog.findViewById(R.id.edtThemSoTienChi);
        final EditText edtThemNgayChi = dialog.findViewById(R.id.edtThemNgayChi);
        final Spinner spShowLoaiChi = dialog.findViewById(R.id.spShowLoaiChi);
        Spinner spShowDonViTienChi = dialog.findViewById(R.id.spShowDonViTienChi);
        Button btnThemKhoanChi = dialog.findViewById(R.id.btnThemKhoanChi);

        addItemsToSpinnerLoaiChi(spShowLoaiChi);
        addItemsToSpinnerDonViTien(spShowDonViTienChi);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edtThemNgayChi.setText(mDay + "/" + (mMonth+1) + "/" + mYear);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtThemNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtThemNgayChi);
            }
        });

        btnThemKhoanChi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String edtKhoanChi = edtThemKhoanChi.getText().toString();
                String edtSoTien = edtThemSoTienChi.getText().toString();
                String edtNgayChi = edtThemNgayChi.getText().toString();
                if (edtKhoanChi.isEmpty()){
                    edtThemKhoanChi.setError("Không được bỏ trống");
                }else if (edtSoTien.isEmpty()){
                    edtThemSoTienChi.setError("Không được bỏ trống");
                }else if (edtNgayChi.isEmpty()){
                    edtThemNgayChi.setError("Không được bỏ trống");
                }else {
                    edtThemKhoanChi.setError(null);
                    edtThemSoTienChi.setError(null);
                    edtThemNgayChi.setError(null);
                    if (idLoaiChi == 0 || donViTien.equals("Chọn")){
                        showSnackBar.showSnackbar(getView(),"Không được bỏ trống");
                    }else {

                        showSnackBar.showSnackbar(getView(), "Thêm thành công");
                        database.addChi(new Chi(edtKhoanChi,edtSoTien,donViTien,edtNgayChi,0,0,idLoaiChi));
                        loadData();
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
    }
    public void addItemsToSpinnerLoaiChi(Spinner spChonLoaiThu) {

        loaiChiArrayList = new ArrayList<LoaiChi>();
        loaiChiArrayList.add(new LoaiChi(0,"Chọn"));

        dataSpinnerLoaiChi(loaiChiArrayList);

        SpLoaiChiAdapter spinAdapter = new SpLoaiChiAdapter(
                getContext(), loaiChiArrayList);
        spChonLoaiThu.setAdapter(spinAdapter);
        spChonLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                getContext(), donViTienArrayList);
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
    public static void dataSpinnerLoaiChi(ArrayList<LoaiChi> loaiChiArrayList){
        Cursor cursor = database.GetDate("SELECT  * FROM loaiChi WHERE deleteFlag = 0");
        while (cursor.moveToNext()){
            int idLoaiThu = cursor.getInt(0);
            String TenLoaiThu = cursor.getString(1);
            loaiChiArrayList.add(new LoaiChi(idLoaiThu,TenLoaiThu));
        }
    }
    private void chonNgay(final EditText chonNgay){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    public static void loadData(){
        Cursor cursor = database.GetDate("SELECT * FROM chi WHERE deleteFlag = '0'");
        chiArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenMucChi = cursor.getString(1);
            String dinhMucChi = cursor.getString(2);
            String donViChi = cursor.getString(3);
            String thoiGian = cursor.getString(4);
            int danhGia = cursor.getInt(5);
            int deleteFlag = cursor.getInt(6);
            int idLoaiChi = cursor.getInt(7);

            chiArrayList.add(new Chi(id,tenMucChi,dinhMucChi,donViChi,thoiGian,danhGia,deleteFlag,idLoaiChi));

        }
        chiAdapter.notifyDataSetChanged();
    }
    public static void showDialogFullChi(Context context, Chi chi){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_xem_full_chi);

        ImageView imgCloseViewFullChi = dialog.findViewById(R.id.imgCloseXemChi);
        TextView txtDlShowLoaiChiView = dialog.findViewById(R.id.txtDlShowLoaiChiView);
        TextView txtDlShowNameKhoanChiView = dialog.findViewById(R.id.txtDlShowNameKhoanChiView);
        TextView txtDlShowSoTienView = dialog.findViewById(R.id.txtDlShowSoTienViewChi);
        TextView txtDlShowNgayChiView = dialog.findViewById(R.id.txtDlShowNgayChiView);


        Cursor cursor = database.GetDate("SELECT * FROM loaiChi WHERE id = '"+ chi.getIdLoaiChi() +"'");
        while (cursor.moveToNext()) {
            String tenMucThu = cursor.getString(1);
            txtDlShowLoaiChiView.setText(tenMucThu);
        }

        txtDlShowNameKhoanChiView.setText(chi.getTenMucChi());
        txtDlShowSoTienView.setText(FormatCost(Long.parseLong(chi.getDinhMucChi())) + " " + chi.getDonViChi());
        txtDlShowNgayChiView.setText(chi.getThoiDiemApDungChi());


        imgCloseViewFullChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public static void deleteKhoanChi(int id){
        database.QueryData("UPDATE chi SET deleteFlag = 1 WHERE id = '" + id + "'");
        loadData();
    }
    public static void editKhoanChi(int id, String edtKhoanchi, String edtSoTien, String edtNgayChi, int idLoaiChi, String donViTien){
        database.QueryData("UPDATE chi SET tenMucChi = '" + edtKhoanchi + "' , dinhMucChi = '" + edtSoTien + "' , thoiDiemApDungChi = '" + edtNgayChi + "' , idLoaiChi = " + idLoaiChi + " , donViChi = '" + donViTien + "' WHERE id = '" + id + "'");
        loadData();
    }
    public static String loaichi(int idLoaiChi){
        Cursor cursor = database.GetDate("SELECT * FROM loaiChi WHERE id = '"+ idLoaiChi +"'");
        while (cursor.moveToNext()) {
            String tenMucChi = cursor.getString(1);
            return tenMucChi;
        }
        return null;
    }

    public static String FormatCost(long cost){
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            return decimalFormat.format(Integer.parseInt(cost+""));
        }catch (Exception e) {
            return cost + "";
        }
    }


}
