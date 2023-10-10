package com.example.cemetery_payment_system.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cemetery_payment_system.Constant;
import com.example.cemetery_payment_system.DataIrimbi;
import com.example.cemetery_payment_system.IrimbiListAdapter;
import com.example.cemetery_payment_system.KurebaNoKwishyuraIrimbiActivity;
import com.example.cemetery_payment_system.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KurebaIrimbiFragment extends Fragment implements IrimbiListAdapter.SelectIrimbi{

    RecyclerView IrimbiRecyclerview;
    IrimbiListAdapter irimbiListAdapter;
    List<DataIrimbi> listIrimbi;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KurebaIrimbiFragment() {
        // Required empty public constructor
    }

    public static KurebaIrimbiFragment newInstance(String param1, String param2) {
        KurebaIrimbiFragment fragment = new KurebaIrimbiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kureba_irimbi, container, false);
        IrimbiRecyclerview = view.findViewById(R.id.irimbiListID);

        listIrimbi = new ArrayList<>();
        irimbiListAdapter = new IrimbiListAdapter(getContext(),listIrimbi,this);
        selectingIrimbiMethod();
        return view;
    }

    private void selectingIrimbiMethod() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String URL1 = Constant.host +"choosecemetery.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject roomObj = array.getJSONObject(i);
//                        add data to the ArrayList
                        listIrimbi.add(new DataIrimbi(
                                roomObj.getString("cemetery_id"),
                                roomObj.getString("cemetery_name"),
                                roomObj.getString("category")
                        ));
                    }
//                    add adapter
                    IrimbiRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    IrimbiRecyclerview.setAdapter(irimbiListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(request);
    }

    @Override
    public void SelectIrimbi(DataIrimbi dataIrimbi) {
        Intent intent = new Intent(getContext(), KurebaNoKwishyuraIrimbiActivity.class);
        Bundle args = new Bundle();
        args.putString("IrimbiID",dataIrimbi.getIrimbiID());
        intent.putExtras(args);
        startActivity(intent);
    }
}