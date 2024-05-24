package edu.xda.hongtt.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.LSXoaChiAdapter;
import edu.xda.hongtt.adapter.LSXoaThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.Thu;

public class LichSuXoaThuFragment extends Fragment {

    static RecyclerView recyclerView ;
    static ArrayList<LoaiThu> loaiThuArrayList;
    static ArrayList<Thu> thuArrayList;
    static MyDatabaseHelper db;
    static LSXoaThuAdapter lsXoaThuAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lich_su_xoa_thu, container, false);
        addControls(v);
        getThu();
        return v;
    }
    public void addControls(View v){
        recyclerView = v.findViewById(R.id.recyclerViewLsxkhoan);
        loaiThuArrayList = new ArrayList<>();
        thuArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(getContext());
    }
    public static void XoaVinhVienThu(int id){
        db.QueryData("DELETE FROM thu WHERE id = '" + id + "'");
        getThu();
    }
    public static void PhucHoiThu(int id){
        db.QueryData("UPDATE thu SET deleteFlag = '0' WHERE id =" +id);
        getThu();
    }
    public static int getDuLie(int id){
        Cursor cursor = db.GetDate("SELECT * FROM thu WHERE id = " +id);
        while (cursor.moveToNext()){
            int idLoaiThu = cursor.getInt(7);
            Cursor cursor1 = db.GetDate("SELECT * FROM loaithu WHERE id =" + idLoaiThu);
            while (cursor1.moveToNext()){
                int delete = cursor1.getInt(2);
                if(delete==0){
                    return 1;
                }
            }
        }
        return 0;
    }
    public static void getThu(){
        Cursor cursor = db.GetDate("SELECT * FROM thu WHERE deleteFlag = '1'");
        thuArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenMucThu = cursor.getString(1);
            String dinhMucThu = cursor.getString(2);
            String donViThu = cursor.getString(3);
            String thoiGian = cursor.getString(4);
            int danhGia = cursor.getInt(5);
            int deleteFlag = cursor.getInt(6);
            int idLoaiThu = cursor.getInt(7);
            Cursor cursor1 = db.GetDate("SELECT * FROM loaithu WHERE id = " + idLoaiThu);
            while (cursor1.moveToNext()){
                String tenLoaiThu = cursor1.getString(1);
                loaiThuArrayList.add(new LoaiThu(idLoaiThu,tenLoaiThu));
            }
            thuArrayList.add(new Thu(id,tenMucThu,dinhMucThu,donViThu,thoiGian,danhGia,deleteFlag,idLoaiThu));
        }
        lsXoaThuAdapter = new LSXoaThuAdapter(recyclerView.getContext(), thuArrayList,loaiThuArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(lsXoaThuAdapter);
        lsXoaThuAdapter.notifyDataSetChanged();
    }
}