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
import edu.xda.hongtt.adapter.LSXoaLoaiChiAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;

public class LSXoaLoaiChiFragment extends Fragment { ;
    static ArrayList<LoaiChi> loaiChiArrayList;
    static RecyclerView recyclerViewLoaiChi;
    static LSXoaLoaiChiAdapter lsXoaLoaiChiAdapter;
    static MyDatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_l_s_xoa_loai_chi, container, false);
       addControls(v);
       getLoaiChi();
       return v;
    }
    public void addControls(View v){
        recyclerViewLoaiChi = v.findViewById(R.id.recyclerViewlsxl);
        loaiChiArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(getContext());
    }
    public static void XoaVinhVienChi(int id){
        db.QueryData("DELETE FROM loaichi WHERE id = '" + id + "'");
        getLoaiChi();
    }
    public static  void PhucHoiChi(int id){
        db.QueryData("UPDATE loaichi SET deleteFlag = '0' WHERE id =" +id);
        getLoaiChi();
    }

    public static void getLoaiChi(){
        Cursor cursor = db.GetDate("SELECT * FROM loaichi WHERE deleteFlag = '1'");
        loaiChiArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenLoai = cursor.getString(1);
            int deleteFlag = cursor.getInt(2);
            loaiChiArrayList.add(new LoaiChi(id,tenLoai,deleteFlag));
        }
        lsXoaLoaiChiAdapter = new LSXoaLoaiChiAdapter(recyclerViewLoaiChi.getContext(),loaiChiArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerViewLoaiChi.getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewLoaiChi.setLayoutManager(layoutManager);
        recyclerViewLoaiChi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoaiChi.setAdapter(lsXoaLoaiChiAdapter);
        lsXoaLoaiChiAdapter.notifyDataSetChanged();
    }
}