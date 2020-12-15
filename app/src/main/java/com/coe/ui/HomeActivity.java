package com.coe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coe.R;
import com.coe.bo.UsuarioBo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.NumberFormat;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView mBottomNavigation;
    private TextView mTitleToobar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        initView();
        mBottomNavigation.setSelectedItemId(R.id.nav_home_bottom);

        setSupportActionBar(toolbar);
        mTitleToobar.setText("Olá, " + new UsuarioBo(this).autenticado().getNome());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_trofeu, R.id.nav_home, R.id.nav_user_fragment)
                .setDrawerLayout(drawer)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();






        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_drawer_logout) {
                new UsuarioBo(this).clean();
                finishAffinity();
                startActivity(new Intent(this, TelaInicialActivity.class));
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });




        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);
            switch (item.getItemId()) {
                case R.id.nav_trofeu_bottom:
                    navController.navigate(R.id.nav_trofeu);
                    Log.e("B", "o011111111111 ");
                    break;
                case R.id.nav_home_bottom:
                    navController.navigate(R.id.nav_home);
                    Log.e("B", "o0122222222222222 ");
                    break;
                case R.id.nav_user_bottom:
                    navController.navigate(R.id.nav_user_fragment);
                    Log.e("B", "o03333333333333333333 ");
                    break;
            }
            return true;
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        toolbar.setTitle(null);




        return super.onPrepareOptionsMenu(menu);
    }



    private void initView() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        mTitleToobar = findViewById(R.id.toolbar_title);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
    }
}