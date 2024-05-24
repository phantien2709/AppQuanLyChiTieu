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
import edu.xda.hongtt.adapter.LSXoaLoaiChiAdapter;
import edu.xda.hongtt.adapter.LSXoaLoaiThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;

public class LSXoaLoaiThuFragment extends Fragment {
    static ArrayList<LoaiThu> loaiThuArrayList;
    static RecyclerView recyclerView;
    static LSXoaLoaiThuAdapter lsXoaLoaiThuAdapter;
    static MyDatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_l_s_xoa_loai_thu, container, false);
       addControls(v);
       getLoaiThu();
       return v;
    }
    public void addControls(View v){
        recyclerView = v.findViewById(R.id.recyclerViewlsxl);
        db = new MyDatabaseHelper(getContext());
        loaiThuArrayList = new ArrayList<>();

    }
    public static void XoaVinhVienChi(int id){
        db.QueryData("DELETE FROM loaithu WHERE id = '" + id + "'");
        getLoaiThu();
    }
    public static  void PhucHoiChi(int id){
        db.QueryData("UPDATE loaithu SET deleteGlag = '0' WHERE id =" +id);
        getLoaiThu();
    }
    public static void getLoaiThu(){
        Cursor cursor = db.GetDate("SELECT * FROM loaithu WHERE deleteGlag = '1'");
        loaiThuArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenLoai = cursor.getString(1);
            int delete = cursor.getInt(2);
            loaiThuArrayList.add(new LoaiThu(id,tenLoai,delete));
        }
        lsXoaLoaiThuAdapter = new LSXoaLoaiThuAdapter(recyclerView.getContext(),loaiThuArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(lsXoaLoaiThuAdapter);
        lsXoaLoaiThuAdapter.notifyDataSetChanged();
    }

}