package com.example.learning.fragment;

import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.learning.R;
import com.example.learning.adapter.ListThemeAdapter;
import com.example.learning.controller.MainActivity;
import com.example.learning.model.Theme;
import com.example.learning.utils.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListThemeFragment extends Fragment implements ListThemeAdapter.OnThemeListener {

    private OnFragmentInteractionListener mListener;

    private DatabaseManager dbManager;
    private GridLayout grid;
    private RecyclerView gridTheme;

    private ListThemeAdapter adapter;
    private List<Theme> themes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListThemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListThemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListThemeFragment newInstance(String param1, String param2) {
        ListThemeFragment fragment = new ListThemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_theme, container, false);

        if(mListener != null) {
            mListener.onFragmentInteractionChangeTitle("Accueil");
        }

        setDbManager(((MainActivity)getContext()).getDb());
        gridTheme = (RecyclerView) rootView.findViewById(R.id.gridTheme);

        this.setThemes(Theme.getAllThemes(getDbManager()));

        adapter = new ListThemeAdapter(getContext(), themes, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        gridTheme.setLayoutManager(gridLayoutManager);
        gridTheme.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onThemeClick(int position) {
        Theme themeClicked = getThemes().get(position);
        Log.d(TAG, "onThemeClick: " + themeClicked.getName());

        ((MainActivity)getContext()).openFragment(new DetailsThemeFragment(themeClicked), true);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteractionChangeTitle(String title);
    }


    public DatabaseManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    //        FragmentTransaction ft = ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.grid, CardThemeFragment.newInstance());
//        ft.add(R.id.grid, cardThemeFragment);
//    ft.commit();
}