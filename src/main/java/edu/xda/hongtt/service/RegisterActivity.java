package edu.xda.hongtt.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    Button btnSave;
    MyDatabaseHelper db;

     RadioGroup radioGroup;
     RadioButton radioButton1, radioButton2, radioButton3;
     String vaiVe = "";
    private void init() {
        username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        btnSave = findViewById(R.id.btnRegister);
        radioGroup = findViewById(R.id.rdoGroup);
        radioButton1 = findViewById(R.id.rdoAdmin);
        radioButton2= findViewById(R.id.rdoMe);
        radioButton3 = findViewById(R.id.radioMoiNguoi);
        db = new MyDatabaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Xử lý khi một RadioButton được chọn
                if (checkedId == R.id.rdoAdmin) {
                    vaiVe = "ADMIN";
                } else if (checkedId == R.id.rdoMe) {
                    vaiVe = "ME";
                } else if (checkedId == R.id.radioMoiNguoi) {
                    vaiVe = "MOINGUOI";
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = username.getText().toString();
                String pass = password.getText().toString();


                if(username.equals("") || password.equals("") || us.isEmpty() || pass.isEmpty()||( !radioButton1.isChecked() &&!radioButton2.isChecked()&&!radioButton3.isChecked())){
                    Toast.makeText(getApplicationContext(), "Phai dien day du thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkus = db.checkUsername(us);
                    if(checkus == true){
                        Toast.makeText(RegisterActivity.this, vaiVe, Toast.LENGTH_SHORT).show();
                        Boolean insert = db.insert(us, pass,vaiVe);
                        if(insert == true) {
                            Toast.makeText(getApplicationContext(), "Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainAdminActivity.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Email da duoc su dung", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }




}
