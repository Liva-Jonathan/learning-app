package com.example.learning.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.learning.R;
import com.example.learning.utils.DatabaseManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.learning.controller.ExerciceActivity;
import com.example.learning.controller.LearnActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        init();
    }

    public void init() {
        Log.i("MainActivity", "init in onCreate invoked");
        setDb(new DatabaseManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public DatabaseManager getDb() {
        return db;
    }

    public void setDb(DatabaseManager db) {
        this.db = db;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.println(Log.VERBOSE, "CLICK MENU", "=========YES");
        if(item.getItemId() == R.id.nav_learn){
            Intent intent = new Intent(this, LearnActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_exerciceWriting){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_exerciceSorting){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }

        if(item.getItemId() == R.id.nav_exerciceDragging){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        return true;
    }
}