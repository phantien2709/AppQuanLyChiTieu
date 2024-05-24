package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.BaoCaoChiAdapter;
import edu.xda.hongtt.adapter.BaoCaoThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;

public class BaoCaoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Chi> chiArrayList;
    ArrayList<LoaiChi> loaiChiArrayList;
    ArrayList<LoaiThu> loaiThuArrayList;
    ArrayList<Thu> thuArrayList;
    public MyDatabaseHelper db;
    BaoCaoChiAdapter baoCaoChiAdapter;
    BaoCaoThuAdapter baoCaoThuAdapter;
    SimpleDateFormat simpleDate;
    private String thoigian = null;
    {
        simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);
        addContros();
        getDuLieu();
    }
    public void addContros(){
        recyclerView = findViewById(R.id.recyclerViewBaoCao);
        loaiChiArrayList = new ArrayList<>();
        chiArrayList = new ArrayList<>();
        loaiThuArrayList = new ArrayList<>();
        thuArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(this );

    }
    public void getDuLieu(){
        int thu_chi = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            thoigian = extras.getString("ngay");
            thu_chi = extras.getInt("thu_chi");
        }
        if(thu_chi==1){
            getChi(thoigian);
        }
        else {
            getThu(thoigian);
        }

    }
    public void hienThi(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    public void getChi(String thoigian) {
        Cursor cursor = db.GetDate("SELECT * FROM chi WHERE deleteFlag = '0'");
        while (cursor.moveToNext()) {

            String thoiGian = cursor.getString(4);
            if (thoiGian.contains(thoigian)) {
                int id = cursor.getInt(0);
                String tenMucChi = cursor.getString(1);
                String dinhMucChi = cursor.getString(2);
                String donViChi = cursor.getString(3);
                int danhGia = cursor.getInt(5);
                int deleteFlag = cursor.getInt(6);
                int idLoaiChi = cursor.getInt(7);
                Cursor loaichi = db.GetDate("SELECT * FROM loaichi WHERE id = " + idLoaiChi);
                while (loaichi.moveToNext()) {
                    String TenLoaiChi = loaichi.getString(1);
                    Toast.makeText(BaoCaoActivity.this, TenLoaiChi, Toast.LENGTH_SHORT).show();
                    loaiChiArrayList.add(new LoaiChi(idLoaiChi, TenLoaiChi));
                }
                chiArrayList.add(new Chi(id, tenMucChi, dinhMucChi, donViChi, thoiGian, danhGia, deleteFlag, idLoaiChi));
            }
        }
        baoCaoChiAdapter = new BaoCaoChiAdapter(this, chiArrayList,loaiChiArrayList);
        hienThi();
        recyclerView.setAdapter(baoCaoChiAdapter);
    }
    public void getThu(String thoigian){
        Cursor cursor = db.GetDate("SELECT * FROM thu WHERE deleteFlag = 0");
        while (cursor.moveToNext()){
            String thoiGian = cursor.getString(4);
            if(thoiGian.contains(thoigian)){
                int id = cursor.getInt(0);
                String tenMucThu = cursor.getString(1);
                String dinhMucThu = cursor.getString(2);
                String donViThu = cursor.getString(3);
                int danhThu = cursor.getInt(5);
                int deleteFlag = cursor.getInt(6);
                int idLoaiThu = cursor.getInt(7);
                Cursor loaiThu = db.GetDate("SELECT * FROM loaithu WHERE id = " +idLoaiThu);
                while(loaiThu.moveToNext()){
                    String tenLoaiThu = loaiThu.getString(1);
                    loaiThuArrayList.add(new LoaiThu(idLoaiThu,tenLoaiThu));
                }
                thuArrayList.add(new Thu(id,tenMucThu,dinhMucThu,donViThu,thoiGian,danhThu,deleteFlag,idLoaiThu));
            }
            baoCaoThuAdapter = new BaoCaoThuAdapter(this,thuArrayList,loaiThuArrayList);
            hienThi();
            recyclerView.setAdapter(baoCaoThuAdapter);
        }
    }
}