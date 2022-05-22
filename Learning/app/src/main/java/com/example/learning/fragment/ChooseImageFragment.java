package com.example.learning.fragment;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.model.Exercice;
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

    private ThemeResource[] themeResources; // length: 4
    private int indexGoodAnswer;

    private ImageButton audio;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageButton btnNext;

    private ImageView image1Check;
    private ImageView image2Check;
    private ImageView image3Check;
    private ImageView image4Check;


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

        this.audio = (ImageButton) rootView.findViewById(R.id.chooseImageAudio);
        this.image1 = (ImageView) rootView.findViewById(R.id.chooseImage1);
        this.image2 = (ImageView) rootView.findViewById(R.id.chooseImage2);
        this.image3 = (ImageView) rootView.findViewById(R.id.chooseImage3);
        this.image4 = (ImageView) rootView.findViewById(R.id.chooseImage4);
        this.btnNext = (ImageButton) rootView.findViewById(R.id.chooseImageNextBtn);
        this.image1Check = (ImageView) rootView.findViewById(R.id.chooseImage1Check);
        this.image2Check = (ImageView) rootView.findViewById(R.id.chooseImage2Check);
        this.image3Check = (ImageView) rootView.findViewById(R.id.chooseImage3Check);
        this.image4Check = (ImageView) rootView.findViewById(R.id.chooseImage4Check);

        ExerciceActivity exerciceActivity = (ExerciceActivity) getActivity();
        exerciceActivity.setExercice(new Exercice());

        this.createOneExercise();

        return rootView;
    }

    public void createOneExercise() {
        resetViewForNewExercise();

        ExerciceActivity activity = (ExerciceActivity) ChooseImageFragment.this.getActivity();

        this.themeResources = activity.getRandTheme(4);
        this.indexGoodAnswer = new Random().nextInt(4);
        this.displayExercise();
    }

    public void resetViewForNewExercise() {
        image1Check.setImageResource(0);
        image2Check.setImageResource(0);
        image3Check.setImageResource(0);
        image4Check.setImageResource(0);
        btnNext.setVisibility(View.INVISIBLE);
    }

    public void displayExercise() {
        this.audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciceActivity exerciceActivity = (ExerciceActivity) ChooseImageFragment.this.getContext();
                ThemeResource themeGoodAnswer = ChooseImageFragment.this.themeResources[ChooseImageFragment.this.indexGoodAnswer];
                int resourceId = exerciceActivity.getResources().getIdentifier(themeGoodAnswer.getVoice().split("\\.")[0], "raw", exerciceActivity.getPackageName());
                exerciceActivity.playAudio(resourceId);
            }
        });
        this.audio.performClick();

        this.image1.setImageResource(getContext().getResources().getIdentifier(themeResources[0].getImage().split("\\.")[0], "drawable", getContext().getPackageName()));
        this.image2.setImageResource(getContext().getResources().getIdentifier(themeResources[1].getImage().split("\\.")[0], "drawable", getContext().getPackageName()));
        this.image3.setImageResource(getContext().getResources().getIdentifier(themeResources[2].getImage().split("\\.")[0], "drawable", getContext().getPackageName()));
        this.image4.setImageResource(getContext().getResources().getIdentifier(themeResources[3].getImage().split("\\.")[0], "drawable", getContext().getPackageName()));

        View.OnClickListener imageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciceActivity exerciceActivity = (ExerciceActivity) ChooseImageFragment.this.getActivity();
                int idImageClicked = view.getId();
                int idImageGoodAnswer = getResources().getIdentifier("chooseImage" + (ChooseImageFragment.this.indexGoodAnswer + 1), "id", exerciceActivity.getPackageName());
                int idImageCheckClicked = getResources().getIdentifier(getResources().getResourceEntryName(idImageClicked) + "Check", "id", exerciceActivity.getPackageName());
                ImageView imageCheckClicked = exerciceActivity.findViewById(idImageCheckClicked);
                boolean canContinue;
                if(idImageClicked == idImageGoodAnswer) {
                    imageCheckClicked.setImageResource(R.drawable.rect_green_transparent);
                    canContinue = exerciceActivity.checkExerciceIfContinue(true);
                } else {
                    imageCheckClicked.setImageResource(R.drawable.rect_red_transparent);
                    canContinue = exerciceActivity.checkExerciceIfContinue(false);
                }

                if(!canContinue) return ;

                btnNext.setVisibility(View.VISIBLE);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChooseImageFragment.this.createOneExercise();
                    }
                });

                ChooseImageFragment.this.image1.setOnClickListener(null);
                ChooseImageFragment.this.image2.setOnClickListener(null);
                ChooseImageFragment.this.image3.setOnClickListener(null);
                ChooseImageFragment.this.image4.setOnClickListener(null);
            }
        };
        this.image1.setOnClickListener(imageClickListener);
        this.image2.setOnClickListener(imageClickListener);
        this.image3.setOnClickListener(imageClickListener);
        this.image4.setOnClickListener(imageClickListener);
    }

}