package com.example.learning.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.learning.R;
import com.example.learning.fragment.DraggingFragment;
import com.example.learning.fragment.LearnFragment;
import com.example.learning.fragment.SortingFragment;
import com.example.learning.fragment.WritingFragment;

public class ExerciceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        Bundle bundle = new Bundle();
        String myMessage = "Alphabet";
        bundle.putString("categorie", myMessage );
        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.exercicefragment, myFragment).commit();
    }
}