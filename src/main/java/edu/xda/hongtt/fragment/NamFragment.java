package edu.xda.hongtt.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.spAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.ThangNam;
import edu.xda.hongtt.service.BaoCaoActivity;

public class NamFragment extends Fragment {
//    PieChart pieChart;
    TextView txtTongThu, txtTongChi,txtchitietthunam,txtchitietchinam;
    Spinner spinner;
    int tongThu;
    int tongChi;
    MyDatabaseHelper database;
    Calendar calendar = Calendar.getInstance();
    String namHienTai;
    ArrayList<ThangNam> arrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nam, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ThangNam thangNam = arrayList.get(i);
                namHienTai = "/" + thangNam.getThangNam();
                if (namHienTai.equals("/Chọn năm")){
                    ngayHienTai();
                    getChi();
                    getThu();
                    txtTongChi.setText("Tổng Chi: " + FormatCost(tongChi) + " VND");
                    txtTongThu.setText("Tổng Thu: " + FormatCost(tongThu) + " VND");
                }else {
                    getChi();
                    getThu();
                    txtTongChi.setText("Tổng Chi: " + FormatCost(tongChi) + " VND");
                    txtTongThu.setText("Tổng Thu: " + FormatCost(tongThu) + " VND");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txtchitietchinam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BaoCaoActivity.class);
                intent.putExtra("thu_chi",1);
                intent.putExtra("ngay",namHienTai);
                startActivity(intent);
            }
        });
        txtchitietthunam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BaoCaoActivity.class);
                intent.putExtra("thu_chi",2);
                intent.putExtra("ngay",namHienTai);
                startActivity(intent);
            }
        });
    }
    private void initControls(View v) {
        txtTongChi = v.findViewById(R.id.txtTongchiNam);
        txtTongThu = v.findViewById(R.id.txtTongthuNam);
        txtchitietthunam = v.findViewById(R.id.txtchitietthunam);
        txtchitietchinam = v.findViewById(R.id.txtchitetchinam);
        spinner = (Spinner) v.findViewById(R.id.spChonNam);
        database = new MyDatabaseHelper(getContext());

        arrayList = new ArrayList<>();
        arrayList.add(new ThangNam(1, "Chọn năm"));
        arrayList.add(new ThangNam(1, "2021"));
        arrayList.add(new ThangNam(1, "2022"));
        arrayList.add(new ThangNam(1, "2023"));
        spAdapter adapter = new spAdapter(getContext(), arrayList);
        spinner.setAdapter(adapter);
    }
    public String FormatCost(long cost){
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            return decimalFormat.format(Integer.parseInt(cost+""));
        }catch (Exception e) {
            return cost + "";
        }
    }
    public void getChi(){
        Cursor cursor = database.GetDate("SELECT * FROM chi WHERE deleteFlag = '0'");
        int usd = 0;
        int toVnd = 23255;
        int vnd = 0;
        int vietNamDong = 0;
        while (cursor.moveToNext()) {
            int dinhMucChi = cursor.getInt(2);
            String donViChi = cursor.getString(3);
            String ngayThang = cursor.getString(4);
            if (ngayThang.contains(namHienTai)){
                if (donViChi.equalsIgnoreCase("USD")){
                    usd = usd + dinhMucChi;
                    vnd = (usd * toVnd);
                }
                if (donViChi.equalsIgnoreCase("VND")){
                    vietNamDong = vietNamDong + dinhMucChi;
                }
            }
        }
        tongChi = vnd + vietNamDong;
    }
    public void getThu(){
        Cursor cursor = database.GetDate("SELECT * FROM thu WHERE deleteFlag = '0'");
        int usd = 0;
        int toVnd = 23255;
        int vnd = 0;
        int vietNamDong = 0;
        while (cursor.moveToNext()) {
            int dinhMucThu = cursor.getInt(2);
            String donViChi = cursor.getString(3);
            String ngayThang = cursor.getString(4);
            if (ngayThang.contains(namHienTai)) {
                if (donViChi.equalsIgnoreCase("USD")) {
                    usd = usd + dinhMucThu;
                    vnd = (usd * toVnd);
                }
                if (donViChi.equalsIgnoreCase("VND")) {
                    vietNamDong = vietNamDong + dinhMucThu;
                }
            }
        }
        tongThu = vnd + vietNamDong;
    }

    public void ngayHienTai(){
        int mYear = calendar.get(Calendar.YEAR);
        namHienTai = "/" + mYear;
    }

}
