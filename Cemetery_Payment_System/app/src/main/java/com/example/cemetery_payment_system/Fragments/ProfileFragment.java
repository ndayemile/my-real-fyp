package com.example.cemetery_payment_system.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cemetery_payment_system.R;


public class ProfileFragment extends Fragment {


    private static  String param_data_name;
    private static  String param_username;
    private static  String param_lname;
    private static  String param_phone;

    TextView names,usernametxt,phonetxt;

    private String _username;
    private String _FullName;
    private String _Lname;
    private String _Phone;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1username, String param2names, String param3Lname, String param4phone) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("username", param1username);
        args.putString("fname", param2names);
        args.putString("lname", param3Lname);
        args.putString("phone", param4phone);
        fragment.setArguments(args);
        param_data_name = param2names;
        param_username = param1username;
        param_lname = param3Lname;
        param_phone = param4phone;
        Log.d("getus",param_data_name);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _username = getArguments().getString("username");
            _FullName = getArguments().getString("fname");
            _Lname = getArguments().getString("lname");
            _Phone =getArguments().getString("phone");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        names =(TextView) view.findViewById(R.id.FullName);
        usernametxt =(TextView) view.findViewById(R.id.UserNameTxt);
        phonetxt = (TextView) view.findViewById(R.id.phoneNumbertxt);


        names.setText(param_data_name +" "+ param_lname);
        usernametxt.setText(param_username);
        phonetxt.setText(param_phone);
        return view;
    }

}