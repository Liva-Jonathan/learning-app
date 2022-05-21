package com.example.learning.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.learning.R;
import com.example.learning.fragment.ChooseImageFragment;
import com.example.learning.fragment.ChooseWordFragment;
import com.example.learning.fragment.DraggingFragment;
import com.example.learning.fragment.LearnFragment;
import com.example.learning.fragment.SortingFragment;
import com.example.learning.fragment.WritingFragment;
import com.example.learning.utils.DatabaseManager;

public class ExerciceActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setDbManager(new DatabaseManager(this));

        Bundle extras = getIntent().getExtras();
        int typeexo = 0;
        Fragment myFragment = null;
        if(extras != null){
            typeexo = extras.getInt("TYPEEXO");
            Log.println(Log.VERBOSE, "ACT PASS DATA", "=========TYPE EXO "+typeexo);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(typeexo == R.id.nav_exerciceWriting){
            myFragment = new WritingFragment();
        }
        if(typeexo == R.id.nav_exerciceSorting){
            myFragment = new SortingFragment();
        }
        if(typeexo == R.id.nav_exerciceDragging){
            myFragment = new DraggingFragment();
        }
        if(typeexo == R.id.nav_exerciceChooseImage) {
            myFragment = new ChooseImageFragment();
        }
        if(typeexo == R.id.nav_exerciceChooseWord) {
            myFragment = new ChooseWordFragment();
        }

        Bundle bundle = new Bundle();
        String myMessage = "Alphabet";
        bundle.putString("categorie", myMessage );
        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.exercicefragment, myFragment).commit();
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
}