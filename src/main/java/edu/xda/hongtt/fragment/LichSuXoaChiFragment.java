package edu.xda.hongtt.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.BaoCaoChiAdapter;
import edu.xda.hongtt.adapter.LSXoaChiAdapter;
import edu.xda.hongtt.adapter.LSXoaLoaiChiAdapter;
import edu.xda.hongtt.adapter.PageLSXoaAdapter;
import edu.xda.hongtt.adapter.ThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.service.BaoCaoActivity;


public class LichSuXoaChiFragment extends Fragment {

    static ArrayList<Chi> chiArrayList;
    static ArrayList<LoaiChi> loaiChis;
    static RecyclerView recyclerViewChi ;

    static LSXoaChiAdapter lsXoaChiAdapter;

    static MyDatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lich_su_xoa_chi, container, false);
        addControls(view);
        getChi();
        return view;
    }

    public void addControls(View v){
        recyclerViewChi = v.findViewById(R.id.recyclerViewLsxkhoan);
        chiArrayList = new ArrayList<>();
        loaiChis = new ArrayList<>();
        db = new MyDatabaseHelper(getContext());
    }
    public static void XoaVinhVienChi(int id){
        db.QueryData("DELETE FROM chi WHERE id = '" + id + "'");
        getChi();
    }
    public static  void PhucHoiChi(int id){
        db.QueryData("UPDATE chi SET deleteFlag = '0' WHERE id =" +id);
        getChi();
    }
    public static int getDuLie(int id){
        Cursor cursor = db.GetDate("SELECT * FROM chi WHERE id = " +id);
        while (cursor.moveToNext()){
            int idLoaiChi = cursor.getInt(7);
            Cursor cursor1 = db.GetDate("SELECT * FROM loaichi WHERE id =" + idLoaiChi);
            while (cursor1.moveToNext()){
                int delete = cursor1.getInt(2);
                if(delete==0){
                    return 1;
                }
            }
        }
        return 0;
    }
    public static void getChi() {
        Cursor cursor = db.GetDate("SELECT * FROM chi WHERE deleteFlag = '1'");
        chiArrayList.clear();
        while (cursor.moveToNext()) {
            String thoiGian = cursor.getString(4);
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
                loaiChis.add(new LoaiChi(idLoaiChi, TenLoaiChi));
            }
            chiArrayList.add(new Chi(id, tenMucChi, dinhMucChi, donViChi, thoiGian, danhGia, deleteFlag, idLoaiChi));
        }

        lsXoaChiAdapter = new LSXoaChiAdapter(recyclerViewChi.getContext(), chiArrayList,loaiChis);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerViewChi.getContext().getApplicationContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewChi.setLayoutManager(layoutManager);
        recyclerViewChi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChi.setAdapter(lsXoaChiAdapter);
        lsXoaChiAdapter.notifyDataSetChanged();
    }
}