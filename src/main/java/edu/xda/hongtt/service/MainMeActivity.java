package edu.xda.hongtt.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.ChatFragment;
import edu.xda.hongtt.fragment.ChiFragment;
import edu.xda.hongtt.fragment.KhoLuuTruFragment;
import edu.xda.hongtt.fragment.LichSuXoaFragment;
import edu.xda.hongtt.fragment.ThongKeFragment;
import edu.xda.hongtt.fragment.ThuFragment;


public class MainMeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    private NavigationView navigationView;
    MyDatabaseHelper db;
    private TextView txtTenDangNhap;
    String user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_me);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initEvents(toolbar);
        hienThiTen();
    }

    private void initEvents(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        txtTenDangNhap = navigationView.getHeaderView(0).findViewById(R.id.txtTenDangNhap);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_ThongKe);
        loadFragment(new ThongKeFragment());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("tendangnhap");
        }
    }
    private void hienThiTen(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        txtTenDangNhap.setVisibility(View.VISIBLE);
        txtTenDangNhap.setText(tenDangNhap);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_KhoanThu) {
            loadFragment(new ThuFragment());
        } else if (id == R.id.nav_KhoanChi) {
            loadFragment(new ChiFragment());
        } else if (id == R.id.nav_ThongKe) {
            loadFragment(new ThongKeFragment());
//        }
//        else if (id == R.id.nav_GioiThieu) {
//            loadFragment(new GioiThieuFragment());
        } else if (id == R.id.nav_Thoat) {
            thongBaoCloseApp();
        }
        else if(id == R.id.nav_LichSuXoa){
            loadFragment(new LichSuXoaFragment());
        }
        else if(id == R.id.nav_DangXuat){
            thongBaoDangXuat();
        }
        else if(id == R.id.nav_chat){
            loadFragment(new ChatFragment());
        }
        else if(id == R.id.nav_su_kien){
            Intent intent = new Intent(MainMeActivity.this, SuKienActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_luu_tru){
            loadFragment(new KhoLuuTruFragment());
        }
        else if(id==R.id.nav_DSThanhVien){
            Intent intent = new Intent(this,DanhSachCacThanhVienActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void thongBaoCloseApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMeActivity.this);
        builder.setMessage("Bạn có muốn thoát ứng dụng?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Vâng",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void thongBaoDangXuat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMeActivity.this);
        builder.setMessage("Bạn có muốn đăng xuất ứng dụng?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Vâng",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(MainMeActivity.this , LoginActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            thongBaoCloseApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
