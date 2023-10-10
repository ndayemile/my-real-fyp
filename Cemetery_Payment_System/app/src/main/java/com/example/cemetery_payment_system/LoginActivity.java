package com.example.cemetery_payment_system;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cemetery_payment_system.Fragments.ProfileFragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView newAccount;
    private EditText UserName,Password;
    private Button loginbtn;
    CheckBox loginState;
    String URL = Constant.host;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES_NAME = "login_portal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Constant constant = new Constant(LoginActivity.this,"Ubutumwa");

        newAccount = (TextView) findViewById(R.id.newtbn);
        UserName = (EditText) findViewById(R.id.edt_username);
        Password = (EditText) findViewById(R.id.edt_passward);
        loginState = (CheckBox) findViewById(R.id.checkBox);
        loginbtn = (Button) findViewById(R.id.loginbtn);

//        go to new account Activity
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,CreateActivity.class);
                startActivity(intent);
            }
        });

//        here we going to login
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userN = UserName.getText().toString();
                String passW = Password.getText().toString();

                if (TextUtils.isEmpty(userN) || TextUtils.isEmpty(passW)){
                    constant.openDialog("Banza wuzuze ibisabwa Byose!");
                }else{

                    loginMethod(userN,passW);
                }
            }
        });

    }

    private void loginMethod(String userN, String passW) {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Kwinjira...");
        progressDialog.show();

        Constant constant = new Constant(LoginActivity.this,"Ubutumwa");
        String url2 = URL+"login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject resp = new JSONObject(response);
                    String res = resp.getString("status");
                    String user_id = resp.optString("user_id");
                    String reference = resp.optString("reference");
                    String fname = resp.optString("fname");
                    String lname = resp.optString("lname");
                    String phoneNumber = resp.optString("phoneNumber");
                    String username = resp.optString("username");
                    if(res.contains("Login_Success")){
                        progressDialog.dismiss();
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("id",user_id);
                        editor.putString("username",username);
                        editor.putString("fname",fname);
                        editor.putString("lname",lname);
                        editor.putString("phone",phoneNumber);
                        editor.putString("reference",reference);
                        editor.commit();
//                        Log.d("getId", resp.getString("people_id"));

                        Bundle bundle = new Bundle();
                        bundle.putString("username",resp.optString("username"));
                        bundle.putString("phone",resp.optString("phoneNumber"));

//                        home fragment passing data
                        ProfileFragment homeFragment = new ProfileFragment();
                        homeFragment.setArguments(bundle);
                        homeFragment.newInstance(resp.getString("username"),resp.getString("lname"),
                                resp.getString("fname"),resp.getString("phoneNumber"));

                        if (res.equals("Login_Success")){
                            progressDialog.dismiss();
                            Log.d("LogResp", response);
                            Toast.makeText(LoginActivity.this, res, Toast.LENGTH_SHORT).show();
                            if (loginState.isChecked()) {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                            } else {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }else if(res.contains("Wrong_Username")){
                        progressDialog.dismiss();
                        constant.openDialog("Izina mwakoresheje Ntiribaho...");
                    }else if(res.contains("Wrong_Password")){
                        progressDialog.dismiss();
                        constant.openDialog("Ijambo banga siryo...");
                    }else{
                        progressDialog.dismiss();
                        constant.openDialog("Connection_Error");
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    constant.openDialog(e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Constant constant = new Constant(LoginActivity.this,"Error from Volley");
                constant.openDialog(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("username",userN);
                param.put("password",passW);
                return param;
            }
        };

//        add request to requestQueue
        requestQueue.add(request);
    }
}