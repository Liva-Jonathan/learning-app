package com.example.learning.controller;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learning.R;
import com.example.learning.fragment.LearnFragment;

public class LearnActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final LearnFragment myFragment = new LearnFragment();
        Bundle bundle = new Bundle();
        String myMessage = "Alphabet";
        bundle.putString("categorie", myMessage );
        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.learnfragment, myFragment).commit();
    }
}