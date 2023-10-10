package com.example.cemetery_payment_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cemetery_payment_system.Fragments.HomeFragment;
import com.example.cemetery_payment_system.Fragments.KurebaIrimbiFragment;
import com.example.cemetery_payment_system.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    TextView name_nav,phonenumber_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header =navigationView.getHeaderView(0);

        name_nav = (TextView) header.findViewById(R.id.namestxt_nav);
        phonenumber_nav = (TextView) header.findViewById(R.id.phoneNumberTxt_nav);
//        make three horizontal line as icon for navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        getting data from LoginActivity
        Intent in = getIntent();

        String username_bund1 = in.getStringExtra("username");
        String phonenumber_bund =in.getStringExtra("phone");
        name_nav.setText(username_bund1);
        phonenumber_nav.setText(phonenumber_bund);

//        Log.d("my2 name",username_bund);
//        end of getting data from LoginActivity
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home_nav);
        }

//  end make three horizontal line as icon for navigation
    }

//    open navigation By clicking on Items

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.pay_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new KurebaIrimbiFragment()).commit();
                Toast.makeText(this, "Viewing", Toast.LENGTH_SHORT).show();
                break;

            case R.id.profile_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.logout_nav:
                final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();
                startActivity((new Intent(HomeActivity.this, LoginActivity.class)));
                finish();
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}