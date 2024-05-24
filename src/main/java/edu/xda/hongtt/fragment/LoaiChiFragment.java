package edu.xda.hongtt.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ChiAdapter;
import edu.xda.hongtt.adapter.LoaiChiAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.view.ShowSnackBar;

public class LoaiChiFragment extends Fragment {
    FloatingActionButton fabThemLoaiChi;
    static MyDatabaseHelper database;
    static LoaiChiAdapter loaiChiAdapter;
    static ChiAdapter chiAdapter;
    RecyclerView recyclerViewLoaiChi;
    static ArrayList<LoaiChi> loaiChiArrayList;
    static  ArrayList<Chi> chiArrayList;
    static ShowSnackBar showSnackBar;
    static View vi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_chi, container, false);
        vi = v;
        addControls(v);

        addEvents();
        return v;
    }

    private void addEvents() {
        fabThemLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddLoaiThu();
            }
        });
    }

    private void dialogAddLoaiThu(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_loai_chi);

        Button btnThemLoaiChi = (Button) dialog.findViewById(R.id.btnThemLoaiChi);
        ImageView imgCloseThemLoaiChi = (ImageView) dialog.findViewById(R.id.imgCloseThemLoaiChi);
        final EditText edtThemLoaiChi = (EditText) dialog.findViewById(R.id.edtThemLoaiChi);

        imgCloseThemLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThemLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtLoaiChi =  edtThemLoaiChi.getText().toString();
                if (edtLoaiChi.isEmpty()){
                    edtThemLoaiChi.setError("Không được bỏ trống");
                }else {
                    edtThemLoaiChi.setError(null);
                    database.addLoaiChi(new LoaiChi(edtLoaiChi,0));
                    showSnackBar.showSnackbar(getView(), "Thêm dữ liệu thành công");
                    loadData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private static void loadData(){
        Cursor cursor = database.GetDate("SELECT * FROM loaiChi WHERE deleteFlag = 0");
        loaiChiArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenLoaiChi = cursor.getString(1);
            int deleteGlag = cursor.getInt(2);
            loaiChiArrayList.add(new LoaiChi(id, tenLoaiChi, deleteGlag));
        }
        loaiChiAdapter.notifyDataSetChanged();
    }
    private void addControls(View v) {
        fabThemLoaiChi = v.findViewById(R.id.fabThemLoaiChi);
        database = new MyDatabaseHelper(getContext());
        recyclerViewLoaiChi = v.findViewById(R.id.recyclerViewLoaiChi);
        loaiChiArrayList = new ArrayList<>();

        loaiChiAdapter = new LoaiChiAdapter(getContext(),loaiChiArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewLoaiChi.setLayoutManager(layoutManager);
        recyclerViewLoaiChi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoaiChi.setLayoutManager(layoutManager);
        recyclerViewLoaiChi.setAdapter(loaiChiAdapter);
        showSnackBar = new ShowSnackBar();
        loadData();
    }

    public static void loaiChiDelete(int id){
        database.QueryData("UPDATE loaiChi SET deleteFlag = 1 WHERE id = '" + id + "'");
        database.QueryData("UPDATE chi SET deleteFlag = 1 WHERE idLoaiChi = '" + id + "'");
        showSnackBar.showSnackbar(vi, "Xóa thành công");
        loadData();
        KhoanChiFragment.loadData();
    }

    public static void loaiChiEdit(final Context c, final int id, String nameLoaiChi){
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.dialog_edit_loai_chi);
        ImageView imgCloseEdit = dialog.findViewById(R.id.imgCloseEditLoaiChi);
        final EditText edtEditData = dialog.findViewById(R.id.edtEditLoaiChi);
        Button btnUpdate = dialog.findViewById(R.id.btnEditLoaiChi);

        edtEditData.setText(nameLoaiChi);

        imgCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtEdit = edtEditData.getText().toString();
                if (edtEdit.isEmpty()){
                    edtEditData.setError("Không được bỏ trống");
                }else {
                    edtEditData.setError(null);

                    database.QueryData("UPDATE loaiChi SET tenLoaiChi = '" + edtEdit + "' WHERE id = '" + id + "'");
                    loadData();
                    showSnackBar.showSnackbar(vi, "Cập nhật thành công");
//                    Toast.makeText(c, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
