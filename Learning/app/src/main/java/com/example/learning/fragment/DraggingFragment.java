package com.example.learning.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learning.R;

public class DraggingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.println(Log.VERBOSE, "RESULTSORTING", "==== C ");
        View rootView = inflater.inflate(R.layout.fragment_dragging, container, false);
        return rootView;
    }
}