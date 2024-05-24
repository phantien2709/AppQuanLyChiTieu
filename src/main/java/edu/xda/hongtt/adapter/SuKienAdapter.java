package edu.xda.hongtt.adapter;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.ChatFragment;
import edu.xda.hongtt.model.BinhLuan;
import edu.xda.hongtt.model.SuKien;
import edu.xda.hongtt.model.YeuThich;
import edu.xda.hongtt.service.SuKienActivity;
import edu.xda.hongtt.view.ShowSnackBar;

public class SuKienAdapter extends RecyclerView.Adapter<SuKienAdapter.SKViewHolder> {
    private static final int IMAGE_GALLERY_REQUEST = 1;
    Context context;
    ArrayList<SuKien> suKienArrayList;
    MyDatabaseHelper db;
    ArrayList<BinhLuan> binhLuanArrayList;
    BinhLuanAdapter binhLuanAdapter;
    RecyclerView recyclerViewBinhLuan;
    EditText binhLuan;

    public SuKienAdapter(Context context){
        this.context = context;
    }
    public SuKienAdapter(Context context, ArrayList<SuKien> suKienArrayList){
        this.context = context;
        this.suKienArrayList = suKienArrayList;
    }
    @NonNull
    @Override
    public SKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_su_kien,parent,false);
        return new SuKienAdapter.SKViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull SKViewHolder holder, int position) {
        db = new MyDatabaseHelper(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        SuKien suKien = suKienArrayList.get(position);
        holder.tenTaiKhoan.setText(suKien.getTenTaiKhoan());
        holder.noiDungSuKien.setText(suKien.getNoiDung());
        String linkhinh = null;
        linkhinh = suKien.getHinhAnh();
        if (!(linkhinh == null)){
            Uri imageUri = Uri.parse(linkhinh);
            // Hiển thị hình ảnh bằng Glide (thư viện hỗ trợ tải và hiển thị hình ảnh một cách mượt mà)
            Glide.with(context).load(imageUri).into(holder.HinhSuKien);
        }
        Cursor cursor = db.GetDate("SELECT * FROM YEUTHICH WHERE TenTaiKhoan LIKE '" + tenDangNhap +"' AND IdSuKien = " + suKien.getIdSuKien() );
        if(cursor.getCount() == 0){
            holder.imageFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
            holder.linearYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.themYeuThich(tenDangNhap,suKien.getIdSuKien());
                    holder.imageFavorite.setImageResource(R.drawable.baseline_favorite_24);
                    notifyDataSetChanged();
                }
            });
        }
        else {
            holder.imageFavorite.setImageResource(R.drawable.baseline_favorite_24);
            holder.linearYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.QueryData("DELETE FROM YEUTHICH WHERE TenTaiKhoan LIKE '" + tenDangNhap +"' AND IdSuKien = " + suKien.getIdSuKien());
                    holder.imageFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
                    notifyDataSetChanged();
                }
            });
        }
        Cursor cursor1 = db.GetDate("SELECT * FROM YEUTHICH WHERE IdSuKien = " + suKien.getIdSuKien());
        String luotTim = String.valueOf(cursor1.getCount());
        holder.luotTim.setText(luotTim);
        holder.linearNguoiYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_nguoi_yeu_thich);
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1);
                dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView close = dialog.findViewById(R.id.imgClose);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewNguoiYeuThich);
                NguoiYeuThichAdapter nguoiYeuThichAdapter;
                ArrayList<YeuThich> yeuThichArrayList = new ArrayList<>();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yeuThichArrayList.clear();
                Cursor cursor3 = db.GetDate("SELECT * FROM YEUTHICH WHERE IdSuKien = " + suKien.getIdSuKien());
                while (cursor3.moveToNext()){
                    String tenTaiKhoan = cursor3.getString(1);
                    int idSuKien = cursor3.getInt(2);
                    yeuThichArrayList.add(new YeuThich(tenTaiKhoan,idSuKien));
                }
                nguoiYeuThichAdapter = new NguoiYeuThichAdapter(context,yeuThichArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(nguoiYeuThichAdapter);
                nguoiYeuThichAdapter.notifyDataSetChanged();
                dialog.show();
            }
        });
        holder.linearBinhLuan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_binh_luan);

                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1);
                dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                binhLuanArrayList = new ArrayList<>();
                db = new MyDatabaseHelper(context);
                recyclerViewBinhLuan = dialog.findViewById(R.id.recyclerViewBinhLuan);

                ImageView closs = dialog.findViewById(R.id.imgClose);
                binhLuan = dialog.findViewById(R.id.edBinhLuan);
                ImageView send = dialog.findViewById(R.id.imageSend);
                closs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                getBinhLuan();
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noidungBL = binhLuan.getText().toString();
                        db.themBinhLuan(suKien.getIdSuKien(),tenDangNhap,noidungBL);
                        getBinhLuan();
                        binhLuanAdapter.notifyDataSetChanged();
                        binhLuan.setText("");

                    }
                });

                dialog.show();
            }
            public  void getBinhLuan(){
                Cursor cursor2 = db.GetDate("SELECT * FROM BINHLUAN WHERE Id_SK = " + suKien.getIdSuKien() );
                binhLuanArrayList.clear();
                while (cursor2.moveToNext()){
                    int idBinhLuan = cursor2.getInt(0);
                    String noiDung = cursor2.getString(1);
                    int idSuKien = cursor2.getInt(2);
                    String tenTaiKhoan = cursor2.getString(3);
                    binhLuanArrayList.add(new BinhLuan(idBinhLuan,idSuKien,noiDung,tenTaiKhoan));
                }
                String luotBinhLuan = String.valueOf(cursor2.getCount());
                holder.luotBinhLuan.setText(luotBinhLuan);

                binhLuanAdapter = new BinhLuanAdapter( binhLuanArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                Collections.reverse(binhLuanArrayList);
                linearLayoutManager.setReverseLayout(true);
                recyclerViewBinhLuan.setLayoutManager(linearLayoutManager);
                recyclerViewBinhLuan.setAdapter(binhLuanAdapter);
            }

        });
        Cursor cursor2 = db.GetDate("SELECT * FROM BINHLUAN WHERE Id_SK = " + suKien.getIdSuKien());
        String luotBinhLuan = String.valueOf(cursor2.getCount());
        holder.luotBinhLuan.setText(luotBinhLuan);
        holder.imageXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suKien.getTenTaiKhoan().equals(tenDangNhap)){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_thu_hoi);
                    TextView txtAn = dialog.findViewById(R.id.txtAn);
                    TextView txtXoa = dialog.findViewById(R.id.txtXoa);
                    TextView txtChinhSua = dialog.findViewById(R.id.txtChinhSua);

                    txtAn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Bạn có muốn ẩn sự kiện này không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ShowSnackBar showSnackBar = new ShowSnackBar();
                                    showSnackBar.showSnackbar(v, "Ẩn thành công");
                                    MyDatabaseHelper db = new MyDatabaseHelper(context);
                                    db.anSuKien(tenDangNhap,suKien.getIdSuKien());
                                    SuKienActivity.loadData();

                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                            dialog.dismiss();
                        }
                    });
                    txtXoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Bạn có muốn xóa Sự kiện này không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ShowSnackBar showSnackBar = new ShowSnackBar();
                                    showSnackBar.showSnackbar(v, "Xóa thành công");
                                    MyDatabaseHelper db = new MyDatabaseHelper(context);
                                    db.QueryData("DELETE FROM SUKIEN WHERE IdSuKien = " + suKien.getIdSuKien());
                                    SuKienActivity.loadData();

                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                            dialog.dismiss();
                        }
                    });
                    txtChinhSua.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SuKienActivity.ChinhSuaSuKien((SuKienActivity) context,suKien.getIdSuKien(),suKien.getHinhAnh(),suKien.getNoiDung());
                        }
                    });
                    dialog.show();
                }
                else {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_an);
                    TextView txtAn = dialog.findViewById(R.id.txtAn);
                    txtAn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Bạn có muốn ẩn sự kiện này không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ShowSnackBar showSnackBar = new ShowSnackBar();
                                    showSnackBar.showSnackbar(v, "Ẩn thành công");
                                    MyDatabaseHelper db = new MyDatabaseHelper(context);
                                    db.anSuKien(tenDangNhap,suKien.getIdSuKien());
                                    SuKienActivity.loadData();

                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return suKienArrayList.size();
    }

    class SKViewHolder extends RecyclerView.ViewHolder{
       TextView tenTaiKhoan,noiDungSuKien,luotTim, luotBinhLuan;
       ImageView HinhSuKien, imageFavorite , imageXoa;
       LinearLayout linearYeuThich, linearBinhLuan , linearNguoiYeuThich;
        public SKViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTaiKhoan = itemView.findViewById(R.id.txtTenTaiKhoan);
            noiDungSuKien = itemView.findViewById(R.id.txtNoiDungSuKien);
            luotBinhLuan = itemView.findViewById(R.id.txtBinhLuanSuKien);
            luotTim = itemView.findViewById(R.id.txtLuotTim);
            HinhSuKien = itemView.findViewById(R.id.imgSuKien);
            linearYeuThich = itemView.findViewById(R.id.linearYeuThich);
            linearBinhLuan = itemView.findViewById(R.id.linearBinhLuan);
            imageFavorite = itemView.findViewById(R.id.imageFavorite);
            linearNguoiYeuThich = itemView.findViewById(R.id.linerNhungNguoiYeuThich);
            imageXoa = itemView.findViewById(R.id.imageXoa);
        }
    }

}
