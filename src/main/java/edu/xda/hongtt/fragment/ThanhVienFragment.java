package edu.xda.hongtt.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ThanhVienAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.ThanhVien;
import edu.xda.hongtt.view.ShowSnackBar;

public class ThanhVienFragment extends Fragment {
    FloatingActionButton fabThemThanhVien;
    static MyDatabaseHelper databaseHelper;
    static ThanhVienAdapter thanhVienAdapter;
    RecyclerView recyclerViewThanhVien;
    static ArrayList<ThanhVien> thanhVienArrayList;
    static ShowSnackBar showSnackBar;
    static View vi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        vi = v;
        addControls(v);

        addEvents();
        return v;
    }

    private void addEvents() {
        fabThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddThanhVien();
            }
        });
    }
    private void dialogAddThanhVien()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_thanhvien);

        Button btnThemThanhVien = (Button) dialog.findViewById(R.id.btnThemThanhVien);
        ImageView imgCloseThemThanhVien = (ImageView) dialog.findViewById(R.id.imgCloseThemThanhVien);
        final EditText edtThemThanhVien = (EditText) dialog.findViewById(R.id.edtThemThanhVien);
        final EditText edtThemNgaySinh = (EditText) dialog.findViewById(R.id.edtThemNgaySinh);
        final EditText edtThemGioiTinh = (EditText) dialog.findViewById(R.id.edtThemGioiTinh);
        final EditText edtThemVaiVe = (EditText) dialog.findViewById(R.id.edtThemVaiVe);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edtThemNgaySinh.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
        imgCloseThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edtThemNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay(edtThemNgaySinh);
            }
        });
        btnThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResouceType")
            @Override
            public void onClick(View v) {
                String edtThanhVien = edtThemThanhVien.getText().toString();
                String edtNgaySinh = edtThemNgaySinh.getText().toString();
                String edtGioiTinh = edtThemGioiTinh.getText().toString();
                String edtVaiVe = edtThemVaiVe.getText().toString();
                if (edtThanhVien.isEmpty())
                {
                    edtThemThanhVien.setError("Không được bỏ trống");
                }
                else if (edtNgaySinh.isEmpty())
                {
                    edtThemNgaySinh.setError("Không được bỏ trống");
                }
                else if (edtGioiTinh.isEmpty())
                {
                    edtThemGioiTinh.setError("Không được bỏ trống");
                }
                else if (edtVaiVe.isEmpty())
                {
                    edtThemVaiVe.setError("Không được bỏ trống");
                }
                else
                {
                    edtThemThanhVien.setError(null);
                    edtThemNgaySinh.setError(null);
                    edtThemGioiTinh.setError(null);
                    edtThemVaiVe.setError(null);
                    showSnackBar.showSnackbar(getView(),"Thêm thành công");
                    databaseHelper.addThanhVien(new ThanhVien(edtThanhVien,edtNgaySinh,edtGioiTinh,edtVaiVe,0));
                    loadData();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void chonNgay(final EditText chonNgay) {
        final  Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void addControls(View v) {fabThemThanhVien = v.findViewById(R.id.fabThemThanhVien);
        recyclerViewThanhVien =v.findViewById(R.id.recyclerViewThanhVien);
        databaseHelper = new MyDatabaseHelper(getContext());
        thanhVienArrayList = new ArrayList<>();
        showSnackBar = new ShowSnackBar();
        thanhVienAdapter = new ThanhVienAdapter(getContext(),thanhVienArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewThanhVien.setLayoutManager(layoutManager);
        recyclerViewThanhVien.setItemAnimator(new DefaultItemAnimator());
        recyclerViewThanhVien.setAdapter(thanhVienAdapter);
        loadData();
    }

    public static void  loadData() {
        Cursor cursor = databaseHelper.GetDate("SELECT * FROM thanhvien WHERE deleteflag = '0'");
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
        thanhVienAdapter.notifyDataSetChanged();
    }

    public static void showDialogChiTietThanhVien(Context context, ThanhVien thanhVien)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_xem_full_thanhvien);
        ImageView imgCloseViewChiTietThanhVien = dialog.findViewById(R.id.imgCloseXemThanhVien);
        TextView txtDlShowHoTen = dialog.findViewById(R.id.txtDlShowTenThanhVien);
        TextView txtDlShowNgaySinh = dialog.findViewById(R.id.txtDlShowNgaySinh);
        TextView txtDlShowGioiTinh = dialog.findViewById(R.id.txtDlShowGioiTinh);
        TextView txtDlShowVaiVe = dialog.findViewById(R.id.txtDlShowVaiVe);

        txtDlShowHoTen.setText(thanhVien.getHoTen());
        txtDlShowNgaySinh.setText(thanhVien.getNgaySinh());
        txtDlShowGioiTinh.setText(thanhVien.getGioiTinh());
        txtDlShowVaiVe.setText(thanhVien.getVaiVe());

        imgCloseViewChiTietThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void thanhVienDelete(int idThanhVien) {
        databaseHelper.QueryData("UPDATE thanhVien SET deleteFlag = 1 WHERE id = '" + idThanhVien + "'");
        showSnackBar.showSnackbar(vi, "Xóa thành công");
        loadData();
    }

    public static void editThanhVien(Context context, final int id, String hoTen, String vaiVe) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_thanhvien);
        ImageView imgClose = dialog.findViewById(R.id.imgCloseEditThanhVien);
        final EditText edtEditTenThanhVien = dialog.findViewById(R.id.edtEditThanhVien);
        final EditText edtEditVaiVe = dialog.findViewById(R.id.edtEditVaiVe);
        Button btnEditUpThanhVien = dialog.findViewById(R.id.btnEditUpThanhVien);

        edtEditTenThanhVien.setText(hoTen);
        edtEditVaiVe.setText(vaiVe);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnEditUpThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtTenThanhVien = edtEditTenThanhVien.getText().toString();
                String edtVaiVe = edtEditVaiVe.getText().toString();
                if(edtTenThanhVien.isEmpty())
                {
                    edtEditTenThanhVien.setError("Không được bỏ trống");
                }
                else if(edtVaiVe.isEmpty()) {
                    edtEditVaiVe.setError("Không được bỏ trống");
                }
                else{
                    edtEditTenThanhVien.setError(null);
                    edtEditVaiVe.setError(null);
                    databaseHelper.QueryData("UPDATE chi SET tenThanhVien = '" + edtTenThanhVien + "' , vaiVe = '" + edtVaiVe + "' WHERE id = '" + id + "'");
                    loadData();
                    showSnackBar.showSnackbar(vi, "Cập nhật thành công");
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}