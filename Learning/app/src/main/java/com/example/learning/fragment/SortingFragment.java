package com.example.learning.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learning.R;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;
import com.example.learning.utils.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button submitSorting;

    List<ThemeResource> themeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sorting, container, false);

        themeList = new ArrayList<ThemeResource>();
        themeList.add(new ThemeResource("Un", "chiffre_un", "chiffre_un"));
        themeList.add(new ThemeResource("Quatre", "chiffre_quatre", "chiffre_quatre"));
        themeList.add(new ThemeResource("Deux", "chiffre_deux", "chiffre_deux"));
        themeList.add(new ThemeResource("Trois", "chiffre_trois", "chiffre_trois"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        submitSorting = (Button) rootView.findViewById(R.id.submitSorting);
        submit();
        recyclerAdapter = new RecyclerAdapter(themeList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(themeList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    private void submit(){
        this.submitSorting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                for(int i = 0; i<themeList.size(); i++){
                    Log.println(Log.VERBOSE, "RESULTSORTING", "==== C "+themeList.get(i).getName());
                }
            }
        });
    }
}