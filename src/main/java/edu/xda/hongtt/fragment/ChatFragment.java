package edu.xda.hongtt.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ChatAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chat;
import edu.xda.hongtt.model.User;
import edu.xda.hongtt.view.ShowSnackBar;


public class ChatFragment extends Fragment {

    static RecyclerView recyclerView;
    static ArrayList<Chat> chatArrayList;
    EditText editChat ;
    ImageView imgSend;
    static MyDatabaseHelper db;
    static ChatAdapter chatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        addControls(view);
        loadData();
        return view;

    }
    public void addControls(View view){
        recyclerView = view.findViewById(R.id.recyclerViewChat);
        chatArrayList = new ArrayList<>();
        editChat = view.findViewById(R.id.edChat);
        imgSend = view.findViewById(R.id.imageSend);
        db = new MyDatabaseHelper(getContext());
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = editChat.getText().toString();

                if (!chat.isEmpty()){
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    String tenDangNhap = sharedPreferences.getString("username", "null");
                    Toast.makeText(getContext(), tenDangNhap, Toast.LENGTH_SHORT).show();
                    db.chatMoi(new Chat(chat,tenDangNhap));
                    editChat.setText("");
                    loadData();
                }
            }
        });
    }

    public static  void thuHoiTinNhan(int id){
        db.QueryData("DELETE FROM CHAT WHERE idChat = " + id);
        chatAdapter.notifyItemRemoved(id);
    }
    public static void loadData(){
        Cursor cursor = db.GetDate("SELECT * FROM CHAT");
        chatArrayList.clear();
        while (cursor.moveToNext()){
            int tinNhanId = cursor.getInt(0);
            String noiDung = cursor.getString(1);
            String tenTaiKhoan = cursor.getString(2);
            int id = cursor.getInt(0);
            String tenDangNhap = getDataFromSharedPreferences(recyclerView.getContext(), "username","null");
            boolean isHiddenByCurrentUser = db.isTinNhanAn(tinNhanId, tenDangNhap);
            if (!isHiddenByCurrentUser) {
                chatArrayList.add(new Chat(id,noiDung,tenTaiKhoan));
            }

        }
        chatAdapter = new ChatAdapter(recyclerView.getContext(), chatArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Collections.reverse(chatArrayList);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    public static String getDataFromSharedPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
}