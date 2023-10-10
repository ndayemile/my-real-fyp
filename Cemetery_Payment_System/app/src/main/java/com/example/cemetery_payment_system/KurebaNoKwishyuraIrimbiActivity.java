package com.example.cemetery_payment_system;

import static com.example.cemetery_payment_system.LoginActivity.SHARED_PREFERENCES_NAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class KurebaNoKwishyuraIrimbiActivity extends AppCompatActivity {

    TextView izinaTxt,urwegoTxt,amafarangaTxt,akarere,umurenge,akagari,umudugudu;
    Button PayBtn;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kureba_no_kwishyura_irimbi);

        izinaTxt = (TextView) findViewById(R.id.TxtIZINA);
        urwegoTxt = (TextView) findViewById(R.id.CATEGORY);
        amafarangaTxt = (TextView) findViewById(R.id.price);
        akarere = (TextView) findViewById(R.id.DISTRICT);
        umurenge = (TextView) findViewById(R.id.SECTOR);
        akagari = (TextView) findViewById(R.id.CELL);
        umudugudu = (TextView) findViewById(R.id.VILLAGE);

        PayBtn = (Button) findViewById(R.id.PayBTN);
//                value from Intent
        Intent intent = getIntent();
        String valueId = intent.getStringExtra("IrimbiID");
        viewCimetery(valueId);
//        Toast.makeText(KurebaNoKwishyuraIrimbiActivity.this, "Idi ni" + valueId, Toast.LENGTH_SHORT).show();

//                   get sharedPreference data
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String fname = sharedPreferences.getString("fname", "");
        String lname = sharedPreferences.getString("lname", "");
        String phoneNumber = sharedPreferences.getString("phone", "");


        PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        getPrice from Sharedpref
                sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_PRICE", Context.MODE_PRIVATE);
                String price = sharedPreferences.getString("sharedPrice", "");

                //                    calling sendPop method here
                sendPop(fname,lname,phoneNumber, price);
                Toast.makeText(KurebaNoKwishyuraIrimbiActivity.this, "Price is "+price, Toast.LENGTH_SHORT).show();
                Toast.makeText(KurebaNoKwishyuraIrimbiActivity.this, "Phone  is "+phoneNumber, Toast.LENGTH_SHORT).show();
                }
        });

    }

    private void viewCimetery(String valueID) {
        Constant constant = new Constant(this,"Message");
        ProgressDialog progressDialog = new ProgressDialog(KurebaNoKwishyuraIrimbiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Viewing...");
        progressDialog.show();

        String URL1 = Constant.host +"selectedCemetery.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    constant.openDialog(response);
//                    Toast.makeText(KurebaNoKwishyuraIrimbiActivity.this, "Data" + response, Toast.LENGTH_SHORT).show();
                    JSONObject resp = new JSONObject(response);
                    String cemetery_id = resp.optString("cemetery_id");
                    String cemetery_name = resp.optString("cemetery_name");
                    String category = resp.optString("category");
                    String price = resp.optString("price");
                    String district = resp.optString("district");
                    String sector = resp.optString("sector");
                    String cell = resp.optString("cell");
                    String village = resp.optString("village");

//                    set sharedPrefe of price
                    SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_PRICE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("sharedPrice",price);
                    editor.commit();

//                    set value to textViews
                    izinaTxt.setText("IZINA: "+cemetery_name);
                    urwegoTxt.setText("URWEGO: "+category);
                    amafarangaTxt.setText("IKIGUZI: "+price);
                    akarere.setText("AKARERE: "+district);
                    umurenge.setText("UMURENGE: "+sector);
                    umudugudu.setText("AKAGARI: "+cell);
                    akagari.setText("UMUDUGUDU: "+village);
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                    constant.openDialog(e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                constant.openDialog(error.toString());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("id",valueID);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void sendPop(String fname, String lname, String phone, String amount){

        String transactionId = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = new Date();
        String date = inputFormat.format(d);
        System.out.println(date);
        final int random = new Random().nextInt(99999) + 1;
        System.out.println("==================amafaranga==" + amount + "=======================" + random + " number is " + phone);

//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String appTransactionId = "ffe037792vcdmx51e8l5h4603qf0064a09" + random;
//        RequestBody body = RequestBody.create(mediaType, "{\n  \"telephoneNumber\" : \"25" + number + "\",\n  \"amount\" : " + Integer.parseInt(amount) + ",\n  \"organizationId\" : \"6af87ea4-ced1-44f8-aea1-75098962e0e4\",\n  \"description\" : \"Funeral Management System\",\n  \"callbackUrl\" : \"https://menyeshaapp.000webhostapp.com/android/kwishyura_callback.php\",\n  \"transactionId\" : \"" + appTransactionId + "\"\n}\n");
//        Log.d("PhoneVal","25"+number);
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url("https://opay-api.oltranz.com/opay/paymentrequest")
//                .post(body)
//                .addHeader("Content-Type", "application/json")
//                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("ref","pay")  //ni iyihe
                .addFormDataPart("tel",phone)
                .addFormDataPart("tx_ref",appTransactionId)
                .addFormDataPart("amount",amount)
                .addFormDataPart("link","https://cemeterypaymentsystem.000webhostapp.com/android/kwishyura_callback.php")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://payment.hdev.rw/api_pay/api/HDEV-48d87cf2-c648-49c1-9c7c-a1a12dbc30eb-ID/HDEV-79d8e552-5bed-4f5a-9551-cd051e32e406-KEY")
                .method("POST", body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String res = response.body().string();
                Log.d("TAG", res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");


                    Log.d("tId", "transactionId");
                    if(status == "error"){
                        status = "failed";
                    }else{
                        status = "pending";
                    }
                    saveData(fname,lname,phone, amount, appTransactionId, status);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void saveData(String fname, String lname, String phone, String amount, String Transactionref, String status) {

        String URL1 = Constant.host + "kwishyura.php";
        Constant constant = new Constant(this, "Message");
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, URL1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HTTPErr", response);
                constant.openDialog("Transaction request sent successful");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("fname", fname);
                param.put("lname", lname);
                param.put("phone", phone);
                param.put("amount", amount);
                param.put("Transactionref", Transactionref);
                param.put("status", status);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }


}