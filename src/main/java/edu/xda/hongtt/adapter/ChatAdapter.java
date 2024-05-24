package edu.xda.hongtt.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.hongtt.R;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.fragment.ChatFragment;
import edu.xda.hongtt.fragment.LichSuXoaChiFragment;
import edu.xda.hongtt.model.Chat;
import edu.xda.hongtt.view.ShowSnackBar;

public class ChatAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_LEFT = 0;
    private static final int VIEW_TYPE_RIGHT = 1;
    private ArrayList<Chat> chats ;
    Context context;
    ShowSnackBar showSnackBar = new ShowSnackBar();
    public ChatAdapter(Context context, ArrayList<Chat> chats){
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_LEFT) {
            view = inflater.inflate(R.layout.custom_chat_left, parent, false);
            return new LeftMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_RIGHT) {
            view = inflater.inflate(R.layout.custom_chat_right, parent, false);
            return new RightMessageViewHolder(view);
        }

        throw new IllegalArgumentException("Invalid view type");
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        if (holder instanceof LeftMessageViewHolder) {
            LeftMessageViewHolder leftViewHolder = (LeftMessageViewHolder) holder;
            MyDatabaseHelper db = new MyDatabaseHelper(context);
            String tenTaiKhoan = chat.getIdTaiKhoan();
            Cursor cursor = db.GetDate("SELECT * FROM NguoiDung WHERE TenDangNhap= '" +tenTaiKhoan+ "'");
            while (cursor.moveToNext()){
                if (cursor.getInt(3) == 1){
                     leftViewHolder.imageOnline.setImageResource(R.drawable.baseline_onl_24);
                }
            }

            leftViewHolder.textChat.setText(chat.getNoiDung());
            leftViewHolder.textTen.setText(chat.getIdTaiKhoan());
            leftViewHolder.textXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn xóa tin nhắn này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            showSnackBar.showSnackbar(v, "Xóa thành công");

                            db.anTinNhan(tenDangNhap,chat.idChat);
                            ChatFragment.loadData();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
        } else if (holder instanceof RightMessageViewHolder) {
            RightMessageViewHolder rightViewHolder = (RightMessageViewHolder) holder;
            rightViewHolder.textChat.setText(chat.getNoiDung());
            rightViewHolder.textTen.setText(chat.getIdTaiKhoan());
            rightViewHolder.imageOnline.setImageResource(R.drawable.baseline_onl_24);
            rightViewHolder.textThuHoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn thu hồi tin nhắn này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            showSnackBar.showSnackbar(v, "Thu hồi thành công");

                            rightViewHolder.textChat.setText("Đã bị thu hồi");
                            rightViewHolder.textChat.setBackground(ContextCompat.getDrawable(context,R.drawable.background_thu_hoi));
                            ChatFragment.thuHoiTinNhan(chat.getId());

                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            rightViewHolder.textXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn xóa tin nhắn này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            showSnackBar.showSnackbar(v, "Xóa thành công");
                            MyDatabaseHelper db = new MyDatabaseHelper(context);
                            db.anTinNhan(tenDangNhap,chat.getId());
                            ChatFragment.loadData();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {

        Chat chat = chats.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("username", "null");
        // Return VIEW_TYPE_LEFT or VIEW_TYPE_RIGHT depending on the sender
        return chat.idTaiKhoan.equals(tenDangNhap) ? VIEW_TYPE_RIGHT : VIEW_TYPE_LEFT;
    }

    class LeftMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textChat,textTen,textXoa;
        ImageView imageOnline;
        LeftMessageViewHolder(View itemView) {
            super(itemView);
            textChat = itemView.findViewById(R.id.txtChat);
            textTen = itemView.findViewById(R.id.txtTen);
            textXoa = itemView.findViewById(R.id.txtXoaTinNhan);
            imageOnline = itemView.findViewById(R.id.imgOnline);
        }
    }

    class RightMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textChat,textTen,textThuHoi, textXoa;
        ImageView imageOnline;
        RightMessageViewHolder(View itemView) {
            super(itemView);
            textChat = itemView.findViewById(R.id.txtChat);
            textTen = itemView.findViewById(R.id.txtTen);
            textXoa = itemView.findViewById(R.id.txtXoaTinNhan);
            textThuHoi = itemView.findViewById(R.id.txtThuHoiTinNhan);
            imageOnline = itemView.findViewById(R.id.imgOnline);
        }
    }
}
