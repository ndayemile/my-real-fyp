package com.example.cemetery_payment_system;


import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;

public class Constant {
public static String host = "http://192.168.43.82/cpis/android/";
    //public static String host = "https://cemeterypayment.000webhostapp.com/cpis/android/";
    Context context;
    String mtitle;

    public Constant(Context context, String mtitle) {
        this.context = context;
        this.mtitle = mtitle;
    }

    public void openDialog(String message) {
        AlertDialog dlg = new AlertDialog.Builder(context)
                .setTitle(mtitle)
                .setMessage(message)
                .setPositiveButton("Yego", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }
                })
                .create();
        dlg.show();

    }


}