package com.example.learning.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.model.ThemeResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseImageFragment extends Fragment {

    public static final int ALPHABET_ID = 1;

    private List<ThemeResource> themeResources;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseImageFragment newInstance(String param1, String param2) {
        ChooseImageFragment fragment = new ChooseImageFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_choose_image, container, false);

        setThemeResources(ThemeResource.getThemeResources(((ExerciceActivity)getContext()).getDbManager(), ALPHABET_ID));

        return rootView;
    }

    public List<ThemeResource> getOneExercise() {
        List<ThemeResource> themeResources = new ArrayList<>();
        int[] indexes = new int[4];
        for(int i = 0; i < 4; i++) {
            int nbRandom = new Random().nextInt(getThemeResources().size());

        }
    }

    public List<ThemeResource> getThemeResources() {
        return themeResources;
    }

    public void setThemeResources(List<ThemeResource> themeResources) {
        this.themeResources = themeResources;
    }
}