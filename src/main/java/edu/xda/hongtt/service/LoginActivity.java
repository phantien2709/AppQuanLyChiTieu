package edu.xda.hongtt.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUserName, txtPassWord;
    private Button btnLogin,btnForgotPass;
    private MyDatabaseHelper db;
    public void init() {
        txtUserName = findViewById(R.id.edUsername);
        txtPassWord = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPass = findViewById(R.id.btnForgotPass);
        db = new MyDatabaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String passWord = txtPassWord.getText().toString();
                if (userName.equals("")) {
                    txtUserName.setError("Moi nhap email");
                    txtUserName.requestFocus();
                } else if (passWord.equals("")) {
                    txtPassWord.setError("Moi nhap mat khau");
                    txtPassWord.requestFocus();
                }
                if (db.checkLogin(userName, passWord) == true) {
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong ", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userName); // lưu tên đăng nhập
                    editor.putString("password", passWord); // lưu mật khẩu
                    editor.putBoolean("isLoggedIn", true); // lưu trạng thái đã đăng nhập
                    editor.apply();
                    db.GetDate("UPDATE NguoiDung SET TrangThai = 1 WHERE TenDangNhap='"+userName +"'");
                    Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("tendangnhap" , userName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Email hoac mat khau khong chinh xac", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
            }
        });
    }

}