package edu.xda.hongtt.fragment;


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
import android.widget.Toast;

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
import edu.xda.hongtt.adapter.SpDonViTienAdapter;
import edu.xda.hongtt.adapter.SpLoaiThuAdapter;
import edu.xda.hongtt.adapter.ThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;
import edu.xda.hongtt.view.ShowSnackBar;

public class KhoanThuFragment extends Fragment {

    FloatingActionButton fabThemKhoanThu;
    static ArrayList<LoaiThu> loaiThuArrayList;
    ArrayList<String> donViTienArrayList;
    static ArrayList<Thu> thuArrayList;
    RecyclerView recyclerViewThu;
    static ThuAdapter thuAdapter;
    int idLoaiThu;
    String donViTien;
    static MyDatabaseHelper database;
    ShowSnackBar showSnackBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_khoan_thu, container, false);

        addControls(v);

        addEvents();

        return v;
    }

    private void addEvents() {
        fabThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKhoanThuDialog();
            }
        });
    }

    private void addControls(View v) {
        fabThemKhoanThu = v.findViewById(R.id.fabThemKhoanThu);
        recyclerViewThu = (RecyclerView) v.findViewById(R.id.recyclerViewThu);
        database = new MyDatabaseHelper(getContext());
        thuArrayList = new ArrayList<>();

        thuAdapter = new ThuAdapter(getContext(),thuArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewThu.setLayoutManager(layoutManager);
        recyclerViewThu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewThu.setAdapter(thuAdapter);

        showSnackBar = new ShowSnackBar();
        loadData();
    }

    private void addKhoanThuDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_thu);

        ImageView imgClose = dialog.findViewById(R.id.imgCloseThemKhoanThu);
        final EditText edtThemKhoanThu = dialog.findViewById(R.id.edtThemKhoanThu);
        final EditText edtThemSoTien = dialog.findViewById(R.id.edtThemSoTien);
        final EditText edtThemNgayThu = dialog.findViewById(R.id.edtThemNgayThu);
        final Spinner spShowLoaiThu = dialog.findViewById(R.id.spShowLoaiThu);
        Spinner spShowDonViTien = dialog.findViewById(R.id.spShowDonViTien);
        Button btnThemKhoanThu = dialog.findViewById(R.id.btnThemKhoanThu);

        addItemsToSpinnerLoaiThu(spShowLoaiThu);
        addItemsToSpinnerDonViTien(spShowDonViTien);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edtThemNgayThu.setText(mDay + "/" + (mMonth+1) + "/" + mYear);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtThemNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtThemNgayThu);
            }
        });

        btnThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtKhoanthu = edtThemKhoanThu.getText().toString();
                String edtSoTien = edtThemSoTien.getText().toString();
                String edtNgayThu = edtThemNgayThu.getText().toString();
                if (edtKhoanthu.isEmpty()){
                    edtThemKhoanThu.setError("Không được bỏ trống");
                }else if (edtSoTien.isEmpty()){
                    edtThemSoTien.setError("Không được bỏ trống");
                }else if (edtNgayThu.isEmpty()){
                    edtThemNgayThu.setError("Không được bỏ trống");
                }else {
                    edtThemKhoanThu.setError(null);
                    edtThemSoTien.setError(null);
                    edtThemNgayThu.setError(null);
                    if (idLoaiThu == 0 || donViTien.equals("Chọn")){
                        showSnackBar.showSnackbar(getView(), "Không được bỏ trống");
                    }else {
                        showSnackBar.showSnackbar(getView(), "Thêm thành công");
                        database.addThu(new Thu(edtKhoanthu,edtSoTien,donViTien,edtNgayThu,0,0,idLoaiThu));
                        loadData();
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
    }
    public void addItemsToSpinnerLoaiThu(Spinner spChonLoaiThu) {

        loaiThuArrayList = new ArrayList<LoaiThu>();
        loaiThuArrayList.add(new LoaiThu(0,"Chọn"));

        dataSpinnerLoaiThu(loaiThuArrayList);

        SpLoaiThuAdapter spinAdapter = new SpLoaiThuAdapter(
                getContext(), loaiThuArrayList);
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
    public static void dataSpinnerLoaiThu(ArrayList<LoaiThu> loaiThuArrayList){
        Cursor cursor = database.GetDate("SELECT  * FROM loaithu WHERE deleteGlag = 0");
        while (cursor.moveToNext()){
            int idLoaiThu = cursor.getInt(0);
            String TenLoaiThu = cursor.getString(1);
            loaiThuArrayList.add(new LoaiThu(idLoaiThu,TenLoaiThu));
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
        Cursor cursor = database.GetDate("SELECT * FROM thu WHERE deleteFlag = '0'");
        thuArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenMucThu = cursor.getString(1);
            String dinhMucThu = cursor.getString(2);
            String donViThu = cursor.getString(3);
            String thoiGian = cursor.getString(4);
            int danhGia = cursor.getInt(5);
            int deleteFlag = cursor.getInt(6);
            int idLoaiThu = cursor.getInt(7);

            thuArrayList.add(new Thu(id,tenMucThu,dinhMucThu,donViThu,thoiGian,danhGia,deleteFlag,idLoaiThu));

        }
        thuAdapter.notifyDataSetChanged();
    }
    public static void showDialogFullThu(Context context, Thu thu){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_xem_full_thu);

        ImageView imgCloseViewFullThu = dialog.findViewById(R.id.imgCloseXemThu);
        TextView txtDlShowLoaiThuView = dialog.findViewById(R.id.txtDlShowLoaiThuView);
        TextView txtDlShowNameKhoanThuView = dialog.findViewById(R.id.txtDlShowNameKhoanThuView);
        TextView txtDlShowSoTienView = dialog.findViewById(R.id.txtDlShowSoTienView);
        TextView txtDlShowNgayThuView = dialog.findViewById(R.id.txtDlShowNgayThuView);

        Cursor cursor = database.GetDate("SELECT * FROM loaithu WHERE id = '"+ thu.getIdLoaiThu() +"'");
        while (cursor.moveToNext()) {
            String tenMucThu = cursor.getString(1);
            txtDlShowLoaiThuView.setText(tenMucThu);
        }

        txtDlShowNameKhoanThuView.setText(thu.getTenMucThu());
        txtDlShowSoTienView.setText(FormatCost(Long.parseLong(thu.getDinhMucThu())) + " " + thu.getDonViThu());
        txtDlShowNgayThuView.setText(thu.getThoiDiemApDungThu());


        imgCloseViewFullThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public static void deleteKhoanThu(int id){
        database.QueryData("UPDATE thu SET deleteFlag = 1 WHERE id = '" + id + "'");
        loadData();
    }
    public static void editKhoanThu(int id, String edtKhoanthu, String edtSoTien, String edtNgayThu, int idLoaiThu, String donViTien){
        database.QueryData("UPDATE thu SET tenMucThu = '" + edtKhoanthu + "' , dinhMucThu = '" + edtSoTien + "' , thoiDiemApDungThu = '" + edtNgayThu + "' , idLoaiThu = " + idLoaiThu + " , donViThu = '" + donViTien + "' WHERE id = '" + id + "'");
        loadData();
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
    public static String loaiThu(int idLoaiThu){
        Cursor cursor = database.GetDate("SELECT * FROM loaithu WHERE id = '"+ idLoaiThu +"'");
        while (cursor.moveToNext()) {
            String tenMucThu = cursor.getString(1);
            return tenMucThu;
        }
        return null;
    }
}
