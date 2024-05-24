package edu.xda.hongtt.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;


public class ShowSnackBar {
    @SuppressLint("ResourceType")
    public void showSnackbar(View v, String noti){
        Snackbar snackbar = Snackbar
                .make(v, noti, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#0573ff"));
        snackbar.show();
    }

}
