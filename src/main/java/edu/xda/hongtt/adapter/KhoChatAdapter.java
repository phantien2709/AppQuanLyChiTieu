package edu.xda.hongtt.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.DataInput;
import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.KhoChatFragment;
import edu.xda.hongtt.model.Chat;

public class KhoChatAdapter extends RecyclerView.Adapter<KhoChatAdapter.KChatViewHolder> {
   Context context;
   ArrayList<Chat> chatArrayList;
   public  KhoChatAdapter(Context context, ArrayList<Chat> chatArrayList){
       this.context = context;
       this.chatArrayList = chatArrayList;
   }
    @NonNull
    @Override
    public KChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.custom_kho_chat,parent,false);
        return  new KhoChatAdapter.KChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KChatViewHolder holder, int position) {
        MyDatabaseHelper db = new MyDatabaseHelper(context);
        Chat chat = chatArrayList.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        Cursor cursor = db.GetDate("SELECT * FROM NguoiDung WHERE TenDangNhap= '" +chat.getIdTaiKhoan()+ "'");
        while (cursor.moveToNext()){
            if (cursor.getInt(3) == 1){
                holder.imageOnline.setImageResource(R.drawable.baseline_onl_24);
            }
        }
        holder.textChat.setText(chat.getNoiDung());
        holder.textTen.setText(chat.getIdTaiKhoan());
        holder.imageKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat.getIdTaiKhoan().equals(tenDangNhap)){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_xoa_kho_luu_tru);
                    TextView boAn = dialog.findViewById(R.id.txtBoAn);
                    TextView xoa = dialog.findViewById(R.id.txtXoa);
                    boAn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.QueryData("DELETE FROM CHAT_HIDDEN_STATUS WHERE idTinNhan = " + chat.getId() + " AND tenTaiKhoan = '" + tenDangNhap + "'");
                            KhoChatFragment.loadData();
                            dialog.dismiss();
                        }
                    });
                    xoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.QueryData("DELETE FROM CHAT WHERE idChat = " + chat.getId());
                            db.QueryData("DELETE FROM CHAT_HIDDEN_STATUS WHERE idTinNhan = " + chat.getId());
                            KhoChatFragment.loadData();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_khoi_phu_kho_luu_tru);
                    TextView boAn = dialog.findViewById(R.id.txtBoAn);
                    boAn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.QueryData("DELETE FROM CHAT_HIDDEN_STATUS WHERE idTinNhan = " + chat.getId() + " AND tenTaiKhoan = '" + tenDangNhap + "'");
                            KhoChatFragment.loadData();
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
        return chatArrayList.size();
    }

    class KChatViewHolder extends RecyclerView.ViewHolder{
        TextView textChat,textTen;
        ImageView imageOnline , imageKhoiPhuc;
        public KChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textChat = itemView.findViewById(R.id.txtChat);
            textTen = itemView.findViewById(R.id.txtTen);
            imageKhoiPhuc = itemView.findViewById(R.id.imgKhoiPhuc);
            imageOnline = itemView.findViewById(R.id.imgOnline);
        }
    }
}
