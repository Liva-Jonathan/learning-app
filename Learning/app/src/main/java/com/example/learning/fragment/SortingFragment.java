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
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.model.Exercice;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;
import com.example.learning.utils.Constante;
import com.example.learning.utils.RecyclerAdapter;
import com.example.learning.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button submitSorting;
    Button nextSorting;
    TextView textSorting;
    ItemTouchHelper.SimpleCallback simpleCallback;
    View rootView;
    int order;
    List<ThemeResource> themeList;

    public List<ThemeResource> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<ThemeResource> themeList) {
        this.themeList = themeList;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sorting, container, false);

        textSorting = rootView.findViewById(R.id.textSorting);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        submitSorting = (Button) rootView.findViewById(R.id.submitSorting);
        nextSorting = (Button) rootView.findViewById(R.id.nextSorting);

        init();
        return rootView;
    }

    public void initCallback(){
        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getBindingAdapterPosition();
                int toPosition = target.getBindingAdapterPosition();
                //Collections.swap(themeList, fromPosition, toPosition);
                if(fromPosition < toPosition){
                    Collections.rotate(themeList.subList(fromPosition, toPosition+1), -1);
                }
                if(fromPosition > toPosition){
                    Collections.rotate(themeList.subList(toPosition, fromPosition+1), 1);
                }
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
    }

    private void submit(){
        this.submitSorting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    ExerciceActivity activity = (ExerciceActivity) SortingFragment.this.getActivity();
                    Exercice exo = activity.getExercice();
                    boolean res = activity.checkOrderTab(getThemeList(), getOrder());
                    if(res){
                        //Log.println(Log.VERBOSE, "RESULT", "===== VRAI");
                        exo.setBonne(exo.getBonne() + 1);
                        Toast.makeText(SortingFragment.this.getActivity().getApplicationContext(), "Réponse correcte", Toast.LENGTH_SHORT).show();
                    }else{
                        //Log.println(Log.VERBOSE, "RESULT", "===== FAUX");
                        exo.setMauvaise(exo.getMauvaise() + 1);
                        Toast.makeText(SortingFragment.this.getActivity().getApplicationContext(), "Réponse fausse", Toast.LENGTH_SHORT).show();
                    }
                    exo.setTotale(exo.getTotale() + 1);
                    if(exo.getTotale()>=exo.getFin()){
                        activity.showScore();
                    }else{
                        //init();
                        submitSorting.setVisibility(View.INVISIBLE);
                        nextSorting.setVisibility(View.VISIBLE);
                    }
            }
        });
    }

    public String getLibelleOrder(){
        if(this.getOrder() == 0){
            return "croissant";
        }else if(this.getOrder() == 1){
            return "décroissant";
        }
        return null;
    }

    public void init(){
        themeList = new ArrayList<ThemeResource>();
        setOrder(Util.getRandNumber(0, 1));
        ThemeResource[] tab = ((ExerciceActivity)this.getActivity()).getURandTheme(Constante.config_nbSorting, this.getOrder());
        for(int i = 0; i< tab.length; i++){
            themeList.add(tab[i]);
        }
        submit();
        submitSorting.setVisibility(View.VISIBLE);
        if(recyclerAdapter == null){
            recyclerAdapter = new RecyclerAdapter(themeList);

            recyclerView.setAdapter(recyclerAdapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

            initCallback();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }else{
            //recyclerView.swapAdapter(new RecyclerAdapter(themeList), false);
            recyclerAdapter.getThemeList().clear();
            recyclerAdapter.setThemeList(themeList);
            //recyclerAdapter = new RecyclerAdapter(themeList);
            recyclerAdapter.notifyDataSetChanged();
        }
        textSorting.setText("Remettre dans l'ordre "+this.getLibelleOrder());
        nextSorting.setVisibility(View.INVISIBLE);
        nextSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortingFragment.this.init();
            }
        });
    }

}