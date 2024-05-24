package edu.xda.hongtt.service;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText edUserName;
    private Button btxacnhan;
    private MyDatabaseHelper db;
    public  void init(){
        edUserName = findViewById(R.id.edUsername);
        btxacnhan = findViewById(R.id.btxacnhan);
        db = new MyDatabaseHelper(this);
    }
    public String getPass(String userName){
        Cursor cursor = db.GetDate("SELECT * FROM NguoiDung");
        String matKhau = null;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(userName)) {
                matKhau = cursor.getString(1);
                return matKhau;
            }
        }
        cursor.close();
        return matKhau;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        init();
        btxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString();

                if (userName.equals("")) {
                    edUserName.setError("Mời nhập tài khoản");
                    edUserName.requestFocus();
                }
                if (db.checkForgot(userName) == true) {
                    AlertDialog.Builder b = new AlertDialog.Builder(ForgotPassActivity.this);
                    b.setTitle("Mật khẩu của bạn");
                    b.setMessage(getPass(userName));
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Tài khoản không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}