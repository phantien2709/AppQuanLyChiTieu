package edu.xda.hongtt.fragment;

import android.database.Cursor;
import android.mtp.MtpConstants;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ChatAdapter;
import edu.xda.hongtt.adapter.KhoChatAdapter;
import edu.xda.hongtt.data.MyDatabaseHelper;
import edu.xda.hongtt.model.Chat;

public class KhoChatFragment extends Fragment {

    static RecyclerView recyclerViewKhoChat;
    static ArrayList<Chat> chatArrayList;
    static MyDatabaseHelper db;
    static KhoChatAdapter khoChatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho_chat, container, false);
        addControls(view);
        loadData();
        return view;
    }
    public  void  addControls(View view){
        recyclerViewKhoChat = view.findViewById(R.id.recyclerViewKhoTinNhan);
        chatArrayList = new ArrayList<>();
        db = new MyDatabaseHelper(getContext());
    }
    public static void loadData(){
        Cursor cursor = db.GetDate("SELECT * FROM CHAT");
        chatArrayList.clear();
        while (cursor.moveToNext()){
            int tinNhanId = cursor.getInt(0);
            String noiDung = cursor.getString(1);
            String tenTaiKhoan = cursor.getString(2);
            int id = cursor.getInt(0);
            String tenDangNhap = ChatFragment.getDataFromSharedPreferences(recyclerViewKhoChat.getContext(), "username","null");
            boolean isHiddenByCurrentUser = db.isTinNhanAn(tinNhanId, tenDangNhap);
            if (isHiddenByCurrentUser) {
                chatArrayList.add(new Chat(id,noiDung,tenTaiKhoan));
            }

        }
        khoChatAdapter = new KhoChatAdapter(recyclerViewKhoChat.getContext(), chatArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerViewKhoChat.getContext().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Collections.reverse(chatArrayList);
        recyclerViewKhoChat.setLayoutManager(linearLayoutManager);
        recyclerViewKhoChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewKhoChat.setAdapter(khoChatAdapter);
        khoChatAdapter.notifyDataSetChanged();
    }
}