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
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.KhoSuKienAdapter;
import edu.xda.hongtt.adapter.SuKienAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.SuKien;

public class KhoSuKienFragment extends Fragment {

    static RecyclerView recyclerViewKhoSuKien;
    static ArrayList<SuKien> suKienArrayList;
    static KhoSuKienAdapter khoSuKienAdapter;
    static MyDatabaseHelper db ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_kho_su_kien, container, false);
        addControls(view);
        loadData();
        return view;
    }
    public void addControls(View view){
        recyclerViewKhoSuKien = view.findViewById(R.id.recyclerViewKhoSuKien);
        suKienArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(getContext());
    }
    public static void loadData(){
        Cursor cursor = db.GetDate("SELECT * FROM SUKIEN");
        suKienArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String noiDung = cursor.getString(1);
            String linkHinhAnh = cursor.getString(2);
            String tenTaiKhoan = cursor.getString(3);
            String tenDangNhap = ChatFragment.getDataFromSharedPreferences(recyclerViewKhoSuKien.getContext(), "username","null");
            boolean isHiddenByCurrentUser = db.isSuKien(id, tenDangNhap);
            if (isHiddenByCurrentUser) {
                suKienArrayList.add(new SuKien(id,noiDung,tenTaiKhoan,linkHinhAnh));
            }
        }
        khoSuKienAdapter = new KhoSuKienAdapter(recyclerViewKhoSuKien.getContext(),suKienArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerViewKhoSuKien.getContext().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        Collections.reverse(suKienArrayList);
        recyclerViewKhoSuKien.setLayoutManager(linearLayoutManager);
        recyclerViewKhoSuKien.setAdapter(khoSuKienAdapter);
        recyclerViewKhoSuKien.setItemAnimator(new DefaultItemAnimator());
        khoSuKienAdapter.notifyDataSetChanged();
    }
}