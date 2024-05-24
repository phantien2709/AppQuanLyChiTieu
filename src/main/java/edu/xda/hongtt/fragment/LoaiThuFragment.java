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
import edu.xda.hongtt.adapter.LoaiThuAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.view.ShowSnackBar;


public class LoaiThuFragment extends Fragment {
    FloatingActionButton fabThemLoaiThu;
    static MyDatabaseHelper database;
    static LoaiThuAdapter loaiThuAdapter;
    RecyclerView recyclerViewLoaiThu;
    static ArrayList<LoaiThu> loaiThuArrayList;
    static ShowSnackBar showSnackBar;
    static View vi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_thu, container, false);
        vi = v;
        addControls(v);

        addEvents();

        return v;
    }

    private void addEvents() {
        fabThemLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddLoaiThu();
            }
        });
    }

    private void dialogAddLoaiThu(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_loai_thu);

        Button btnThem = (Button) dialog.findViewById(R.id.btnThemLoaiThu);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgCloseThemLoaiThu);
        final EditText edtThemLoaiThu = (EditText) dialog.findViewById(R.id.edtThemLoaiThu);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtLoaiThu =  edtThemLoaiThu.getText().toString();
                if (edtLoaiThu.isEmpty()){
                    edtThemLoaiThu.setError("Không được bỏ trống");
                }else {
                    edtThemLoaiThu.setError(null);
                    database.addLoaiThu(new LoaiThu(edtLoaiThu,0));
                    showSnackBar.showSnackbar(getView(), "Thêm dữ liệu thành công");
                    loadData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private static void loadData(){
        Cursor cursor = database.GetDate("SELECT  * FROM loaithu WHERE deleteGlag = 0");
        loaiThuArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenLoaiThu = cursor.getString(1);
            int deleteGlag = cursor.getInt(2);
            loaiThuArrayList.add(new LoaiThu(id, tenLoaiThu, deleteGlag));
        }
        loaiThuAdapter.notifyDataSetChanged();
    }

    private void addControls(View v) {
        fabThemLoaiThu = v.findViewById(R.id.fabThemLoaiThu);
        database = new MyDatabaseHelper(getContext());
        recyclerViewLoaiThu = v.findViewById(R.id.recyclerViewLoaiThu);
        loaiThuArrayList = new ArrayList<>();

        loaiThuAdapter = new LoaiThuAdapter(getContext(),loaiThuArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewLoaiThu.setLayoutManager(layoutManager);
        recyclerViewLoaiThu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoaiThu.setLayoutManager(layoutManager);
        recyclerViewLoaiThu.setAdapter(loaiThuAdapter);
        showSnackBar = new ShowSnackBar();
        loadData();
    }

    public static void loaiThuDelete(int id){
        database.QueryData("UPDATE loaithu SET deleteGlag = 1 WHERE id = '" + id + "'");
        database.QueryData("UPDATE thu SET deleteFlag = 1 WHERE idLoaiThu = '" + id + "'");
        showSnackBar.showSnackbar(vi, "Xóa thành công");
        loadData();
        KhoanThuFragment.loadData();
    }

    public static void loaiThuEdit(final Context c, final int id, String nameLoaiThu){
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.dialog_edit_loai_thu);
        ImageView imgCloseEdit = dialog.findViewById(R.id.imgCloseEditLoaiThu);
        final EditText edtEditData = dialog.findViewById(R.id.edtEditLoaiThu);
        Button btnUpdate = dialog.findViewById(R.id.btnEditLoaiThu);

        edtEditData.setText(nameLoaiThu);

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

                    database.QueryData("UPDATE loaithu SET tenLoaiThu = '" + edtEdit + "' WHERE id = '" + id + "'");
                    loadData();
                    showSnackBar.showSnackbar(vi,"Cập nhật thành công");
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
