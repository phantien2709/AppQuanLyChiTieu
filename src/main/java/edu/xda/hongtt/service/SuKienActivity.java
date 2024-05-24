package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.SuKienAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.ChatFragment;
import edu.xda.hongtt.model.Chat;
import edu.xda.hongtt.model.SuKien;

public class SuKienActivity extends AppCompatActivity {
    private static final int IMAGE_GALLERY_REQUEST = 1;
    TextView dangSuKien;
    static RecyclerView recyclerViewSuKien;
    ImageView imageHinh = null;
    private String linkHinh = null;
    EditText noiDung;
    Uri imageUri = null,data;
    ImageView imageThemHinh;
    static MyDatabaseHelper db;
    static SuKienAdapter suKienAdapter;
    static ArrayList<SuKien> suKienArrayList;
    static String linkHinhAnh = null;
    static ImageView imageHinhAnh = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_kien);
        addControls();
        init();
        loadData();
    }
    public void addControls(){
        dangSuKien = findViewById(R.id.txtSuKien);
        recyclerViewSuKien = findViewById(R.id.recyclerViewSuKien);
        db = new MyDatabaseHelper(this);
        suKienAdapter = new SuKienAdapter(this);
        suKienArrayList = new ArrayList<>();
    }
    public void init(){
        dangSuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SuKienActivity.this);
                dialog.setContentView(R.layout.dialog_su_kien);
                ImageView closs = dialog.findViewById(R.id.imgClose);
                noiDung =dialog.findViewById(R.id.edNoiDungSuKien);
                imageHinh = dialog.findViewById(R.id.imgHinhSuKien);
                imageThemHinh = dialog.findViewById(R.id.imageThemHinh);
                TextView tenTaiKhoan = dialog.findViewById(R.id.txtTenTaiKhoan);
                Button dang = dialog.findViewById(R.id.btDang);

                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                String tenDangNhap = sharedPreferences.getString("username", "null");
                tenTaiKhoan.setText(tenDangNhap);

                closs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                imageThemHinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        File file = Environment.getExternalStorageDirectory();
                        String linkAnh = file.getPath();
                        data = Uri.parse(linkAnh);
                        intent.setDataAndType(data, "image/*");
                        startActivityForResult(intent, IMAGE_GALLERY_REQUEST);


                    }
                });
                dang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noiDungSK = null;
                        noiDungSK =noiDung.getText().toString();

                        if(!(linkHinh == null) || !(noiDungSK==null)){
                            db.SuKienMoi(noiDungSK,linkHinh,tenDangNhap);
                            suKienAdapter.notifyDataSetChanged();
                            loadData();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    public static void ChinhSuaSuKien(final SuKienActivity c, final int idSuKien, final  String linkHinh , final  String noiDungSuKien){
        Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.dialog_chinh_su_su_kien);
        ImageView closs = dialog.findViewById(R.id.imgClose);
        EditText noiDung =dialog.findViewById(R.id.edNoiDungSuKien);
        imageHinhAnh = dialog.findViewById(R.id.imgHinhSuKien);
        ImageView imageThemHinh = dialog.findViewById(R.id.imageThemHinh);
        TextView tenTaiKhoan = dialog.findViewById(R.id.txtTenTaiKhoan);
        Button CapNhat = dialog.findViewById(R.id.btCapNhat);
        String tenDangNhap = ChatFragment.getDataFromSharedPreferences(c,"username", "null");
        tenTaiKhoan.setText(tenDangNhap);
        if (!(linkHinh == null)){
            Uri imageUri = Uri.parse(linkHinh);
            // Hiển thị hình ảnh bằng Glide (thư viện hỗ trợ tải và hiển thị hình ảnh một cách mượt mà)
            Glide.with(c).load(imageUri).into(imageHinhAnh);
        }

        noiDung.setText(noiDungSuKien);
        closs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        imageThemHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityForResult(c);
            }
        });
        CapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDungSK = null;
                noiDungSK =noiDung.getText().toString();

                if(!(linkHinhAnh == null) || !(noiDungSK==null)){
                    db.QueryData("UPDATE SUKIEN SET NoiDungSuKien = '" +noiDungSK + "' WHERE IdSuKien = " +idSuKien);
                    db.QueryData("UPDATE SUKIEN SET HinhAnh = '" +linkHinhAnh + "' WHERE IdSuKien = " +idSuKien);
                    loadData();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public static void openActivityForResult(SuKienActivity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        File file = Environment.getExternalStorageDirectory();
        String linkAnh = file.getPath();
        Uri data = Uri.parse(linkAnh);
        intent.setDataAndType(data, "image/*");
        activity.startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
    }
    public static void loadData(){
        Cursor cursor = db.GetDate("SELECT * FROM SUKIEN");
        suKienArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String noiDung = cursor.getString(1);
            String linkHinhAnh = cursor.getString(2);
            String tenTaiKhoan = cursor.getString(3);
            String tenDangNhap = ChatFragment.getDataFromSharedPreferences(recyclerViewSuKien.getContext(), "username","null");
            boolean isHiddenByCurrentUser = db.isSuKien(id, tenDangNhap);
            if (!isHiddenByCurrentUser) {
                suKienArrayList.add(new SuKien(id,noiDung,tenTaiKhoan,linkHinhAnh));
            }

        }
        suKienAdapter = new SuKienAdapter(recyclerViewSuKien.getContext(),suKienArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerViewSuKien.getContext().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        Collections.reverse(suKienArrayList);
        recyclerViewSuKien.setLayoutManager(linearLayoutManager);
        recyclerViewSuKien.setAdapter(suKienAdapter);
        recyclerViewSuKien.setItemAnimator(new DefaultItemAnimator());
        suKienAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            linkHinh = String.valueOf(imageUri);
            linkHinhAnh =String.valueOf(imageUri);
            if(!(imageHinh == null)){
                imageHinh.setImageURI(imageUri);
            }
            if(!(imageHinhAnh == null)){
                imageHinhAnh.setImageURI(imageUri);
            }
            imageUri = null;
        }
    }

}