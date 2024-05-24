package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.DSTVienAdapter;
import edu.xda.hongtt.adapter.ThanhVienAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.ThanhVienFragment;
import edu.xda.hongtt.model.ThanhVien;

public class DanhSachCacThanhVienActivity extends AppCompatActivity {

    RecyclerView recyclerViewThanhVien;
    MyDatabaseHelper db;
    ArrayList<ThanhVien> thanhVienArrayList;
    DSTVienAdapter dstVienAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_thanh_vien);
        recyclerViewThanhVien = findViewById(R.id.recyclerViewdstv);
        thanhVienArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(this);
        loadData();

    }
    public void  loadData() {
        Cursor cursor = db.GetDate("SELECT * FROM thanhvien WHERE deleteflag = '0'");
        thanhVienArrayList.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String hoTen = cursor.getString(1);
            String ngaySinh = cursor.getString(2);
            String gioiTinh = cursor.getString(3);
            String vaiVe = cursor.getString(4);
            int deleteFlag = cursor.getInt(5);
            thanhVienArrayList.add(new ThanhVien(id, hoTen,ngaySinh,gioiTinh,vaiVe,deleteFlag));
        }
        dstVienAdapter = new DSTVienAdapter(this,thanhVienArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewThanhVien.setLayoutManager(layoutManager);
        recyclerViewThanhVien.setItemAnimator(new DefaultItemAnimator());
        recyclerViewThanhVien.setAdapter(dstVienAdapter);
        dstVienAdapter.notifyDataSetChanged();
    }
}