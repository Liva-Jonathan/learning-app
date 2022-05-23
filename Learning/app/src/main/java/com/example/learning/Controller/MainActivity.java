package com.example.learning.controller;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.learning.R;
import com.example.learning.controller.ui.login.LoginActivity;
import com.example.learning.fragment.DetailsThemeFragment;
import com.example.learning.fragment.ListThemeFragment;
import com.example.learning.fragment.SettingsFragment;
import com.example.learning.model.User;
import com.example.learning.utils.Constante;
import com.example.learning.utils.DatabaseManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning.utils.ReminderBroadcast;
import com.example.learning.utils.Serializer;
import com.example.learning.utils.Util;
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
import androidx.preference.PreferenceManager;

import com.example.learning.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
                          implements NavigationView.OnNavigationItemSelectedListener,
                                     ListThemeFragment.OnFragmentInteractionListener,
                                     DetailsThemeFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    NavigationView navigationView;
    private DatabaseManager db;
    private final String filename = Constante.filelog;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        this.createNotificationChannel();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean reminder = sharedPreferences.getBoolean("reminder", true);
        int hourReminder = sharedPreferences.getInt("hourReminder", 8);
        int minuteReminder = sharedPreferences.getInt("minuteReminder", 0);
        if(reminder){
            SettingsFragment.setupNotification(this, hourReminder, minuteReminder);
        }

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
//        Log.println(Log.VERBOSE, "CLICK MENU", "=========YES");

        Fragment fragment = null;

        if(item.getItemId() == R.id.nav_menuTheme){
            fragment = new ListThemeFragment();
        }
        else if(item.getItemId() == R.id.nav_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.nav_log){
            if(Util.isNetworkAvailable(this)){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this.getApplicationContext(), "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
            }

        }else if(item.getItemId() == R.id.nav_logout){
            if(Util.isNetworkAvailable(this)){
                Serializer.logout(filename, this);
                checkLogMenu();
                finish();
                startActivity(getIntent());
            }else{
                Toast.makeText(MainActivity.this.getApplicationContext(), "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
            }
        }

        openFragment(fragment, true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        //Log.println(Log.VERBOSE, "MENU", "CHECK LOG");
        if(auth){
            navigationView.getMenu().findItem(R.id.nav_log).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            if(navigationView.getHeaderCount() > 0){
                ((TextView)navigationView.getHeaderView(0).findViewById(R.id.userlog)).setText(getUser().getEmail());
                navigationView.getHeaderView(0).setVisibility(View.VISIBLE);
            }
        }else{
            navigationView.getMenu().findItem(R.id.nav_log).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            if(navigationView.getHeaderCount() > 0){
                ((TextView)navigationView.getHeaderView(0).findViewById(R.id.userlog)).setText("");
                navigationView.getHeaderView(0).setVisibility(View.INVISIBLE);
            }
            setUser(null);
        }
    }

    public boolean checkAuth(){
        User u =(User) Serializer.deSerialize(filename, this);
        if(u != null){
            //Log.println(Log.VERBOSE, "MENU", "USER NOT NULL");
            if(u.getEmail() != null){
                //Log.println(Log.VERBOSE, "MENU", "USER Exist");
                setUser(u);
                return true;
            }
            //Log.println(Log.VERBOSE, "MENU", "USER NOT EXIST");
        }
        //Log.println(Log.VERBOSE, "MENU", "USER NULL");
        return false;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LearningReminderChannel";
            String description = "Channel for Learning Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifReminder", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
