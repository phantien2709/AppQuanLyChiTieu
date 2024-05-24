package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.xda.hongtt.R;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(LoadActivity.this, MainActivity.class);
            startActivity(intent);
            // Hiển thị trang chính của ứng dụng
        } else {
            Intent intent = new Intent(LoadActivity.this,LoginActivity.class);
            startActivity(intent );
            // Hiển thị màn hình đăng nhập
        }
    }
}