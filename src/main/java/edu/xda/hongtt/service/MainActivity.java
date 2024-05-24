package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabaseHelper(this);
        phanQuyen();

    }
    public void phanQuyen(){
        db = new MyDatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        Cursor cursor = db.GetDate("SELECT * FROM NguoiDung WHERE TenDangNhap = '" +tenDangNhap +"'");
        String vaiVe = "";
        Toast.makeText(this, vaiVe, Toast.LENGTH_SHORT).show();
        while (cursor.moveToNext()){
            vaiVe = cursor.getString(2);

        }
        if(Objects.equals(vaiVe, "ADMIN")){
            Intent intent = new Intent(MainActivity.this,MainAdminActivity.class);
            startActivity(intent);
        }
        else if(Objects.equals(vaiVe, "ME")){
            Intent intent = new Intent(MainActivity.this,MainMeActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this,MainMoiNguoiActivity.class);
            startActivity(intent);
        }
    }

}