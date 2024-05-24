package edu.xda.hongtt.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.service.BaoCaoActivity;


public class HomNayFragment extends Fragment {

    TextView txtTongThu, txtTongChi, txtchitietchi, txtchitietthu;
    int tongThuNgay = 0;
    int tongChiNgay = 0;
    MyDatabaseHelper database;
    Calendar calendar = Calendar.getInstance();
    String ngayHienTai;
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hom_nay, container, false);
        initControls(v);
        initEvents();

        return v;
    }
    private void initEvents() {
        ngayHienTai();
        getChi();
        getThu();
        txtTongChi.setText("Tổng Chi: " + FormatCost(tongChiNgay) + " VND");
        txtTongThu.setText("Tổng Thu: " + FormatCost(tongThuNgay) + " VND");
        txtchitietchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaoCaoActivity.class);
                intent.putExtra("ngay" , ngayHienTai);
                intent.putExtra("thu_chi",1);
                startActivity(intent);

            }
        });
        txtchitietthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaoCaoActivity.class);
                intent.putExtra("ngay" , ngayHienTai);
                intent.putExtra("thu_chi",2);
                startActivity(intent);
            }
        });
    }
    private void initControls(View v) {
        txtTongChi = v.findViewById(R.id.txtTongchiNgay);
        txtTongThu = v.findViewById(R.id.txtTongthuNgay);
        txtchitietchi = v.findViewById(R.id.txtchitetchingay);
        txtchitietthu = v.findViewById(R.id.txtchitietthungay);
        database = new MyDatabaseHelper(getContext());
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
            try {
                Date date = simpleDate.parse(ngayThang);
                if (simpleDate.format(date).contains(ngayHienTai)){
                    if (donViChi.equalsIgnoreCase("USD")){
                        usd = usd + dinhMucChi;
                        vnd = (usd * toVnd);
                    }
                    if (donViChi.equalsIgnoreCase("VND")){
                        vietNamDong = vietNamDong + dinhMucChi;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        tongChiNgay = vnd + vietNamDong;
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
            try {
                Date date = simpleDate.parse(ngayThang);
                if (simpleDate.format(date).contains(ngayHienTai)) {
                    if (donViChi.equalsIgnoreCase("USD")) {
                        usd = usd + dinhMucThu;
                        vnd = (usd * toVnd);
                    }
                    if (donViChi.equalsIgnoreCase("VND")) {
                        vietNamDong = vietNamDong + dinhMucThu;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        tongThuNgay = vnd + vietNamDong;

    }

    public void ngayHienTai(){
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        ngayHienTai = mDay + "/" + (mMonth+1) + "/" + mYear;
        Date date = null;
        try {
            date = simpleDate.parse(ngayHienTai);
            ngayHienTai = simpleDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
