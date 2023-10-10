package com.example.cemetery_payment_system;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    private EditText fName,lName,phoneNumber,Username,Password,rePassword;
    private Button saveButton;
    Constant constant;
    String URL = Constant.host;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
//        set back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//edt_fname,edt_lname_new,edt_PhoneNumber_new,edt_username_new
//edt_passward_new,edt_re_passward_new,new_btn

        fName = (EditText) findViewById(R.id.edt_fname);
        lName = (EditText) findViewById(R.id.edt_lname_new);
        phoneNumber = (EditText) findViewById(R.id.edt_PhoneNumber_new);
        Username = (EditText) findViewById(R.id.edt_username_new);
        Password = (EditText) findViewById(R.id.edt_passward_new);
        rePassword = (EditText) findViewById(R.id.edt_re_passward_new);
        saveButton = (Button) findViewById(R.id.new_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = fName.getText().toString();
                String ln = lName.getText().toString();
                String phone = phoneNumber.getText().toString();
                String userN = Username.getText().toString();
                String pass = Password.getText().toString();
                String rePass = rePassword.getText().toString();
                constant = new Constant(CreateActivity.this,"Ubutumwa");
                if (TextUtils.isEmpty(fn) || TextUtils.isEmpty(ln) || TextUtils.isEmpty(userN) ||
                        TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone)|| TextUtils.isEmpty(rePass)){
                    constant.openDialog("Banza wuzuze ibisabwa Byose!");
                }else{
                    createNewAccountMethod(fn,ln,phone,userN,pass,rePass);
                }


            }
        });
    }

    private void createNewAccountMethod(String fn, String ln,String phone, String userN, String pass,String repass) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Kwiyandikisha Bushyashya");
        progressDialog.show();
        String url1 = URL+"signup.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully_Registered")){
                    Log.d("enock",response+"working");
                    progressDialog.dismiss();
                    constant.openDialog(response);
                    clearEditText();
                }else{
                    progressDialog.dismiss();
                    constant.openDialog(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                constant.openDialog(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("firname",fn);
                param.put("lastname",ln);
                param.put("phonenumber",phone);
                param.put("username",userN);
                param.put("password",pass);
                param.put("retype",repass);
                return param;
            }
        };
        requestQueue.add(request);
    }

    public  void clearEditText(){
        fName.setText("");
        lName.setText("");
        phoneNumber.setText("");
        Username.setText("");
        Password.setText("");
        rePassword.setText("");
    }
}