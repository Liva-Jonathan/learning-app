package com.example.learning.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.learning.R;
import com.example.learning.controller.ui.login.LoginActivity;
import com.example.learning.fragment.DetailsThemeFragment;
import com.example.learning.fragment.ListThemeFragment;
import com.example.learning.model.User;
import com.example.learning.utils.DatabaseManager;
import android.view.MenuItem;
import android.view.View;

import com.example.learning.utils.Serializer;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
                          implements NavigationView.OnNavigationItemSelectedListener,
                                     ListThemeFragment.OnFragmentInteractionListener,
                                     DetailsThemeFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    NavigationView navigationView;
    private DatabaseManager db;
    private final String filename = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        checkLogMenu();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_menuTheme,
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        init();
    }

    public void init() {
        Log.i("MainActivity", "init in onCreate invoked");
        setDb(new DatabaseManager(this));

        openFragment(new ListThemeFragment(), false);
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

        Fragment fragment = null;

        if(item.getItemId() == R.id.nav_menuTheme){
            fragment = new ListThemeFragment();
        }
        else if(item.getItemId() == R.id.nav_learn){
            Intent intent = new Intent(this, LearnActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_exerciceWriting){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_exerciceSorting){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_exerciceDragging){
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_exerciceChooseImage) {
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_exerciceChooseWord) {
            Intent intent = new Intent(this, ExerciceActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_log){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("TYPEEXO", item.getItemId());
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_logout){
            Serializer.logout(filename, this);
            checkLogMenu();
        }


        openFragment(fragment, true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

//    @Override
//    public void onBackPressed(){
//        FragmentManager fm = getFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            Log.i("MainActivity", "popping backstack");
//            fm.popBackStack();
//        } else {
//            Log.i("MainActivity", "nothing on backstack, calling super");
//            super.onBackPressed();
//        }
//    }

    public void openFragment(Fragment fragment, boolean addToBackStack) {
        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainActivityFrame, fragment);
            if(addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteractionChangeTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void checkLogMenu(){
        boolean auth = checkAuth();
        if(auth){
            navigationView.getMenu().findItem(R.id.nav_log).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        }else{
            navigationView.getMenu().findItem(R.id.nav_log).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }
    }

    public boolean checkAuth(){
        User u =(User) Serializer.deSerialize(filename, this);
        if(u != null){
            if(u.getEmail() != null){
                return true;
            }
        }
        return false;
    }
}
