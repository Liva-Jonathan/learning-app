package com.example.learning.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.model.Exercice;
import com.example.learning.model.ThemeResource;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseWordFragment extends Fragment {

    private ThemeResource[] themeResources; // length: 4
    private int indexGoodAnswer;

    private ImageView imageColor;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ImageButton choiceAudio1;
    private ImageButton choiceAudio2;
    private ImageButton choiceAudio3;
    private ImageButton choiceAudio4;
    private ImageButton btnNext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseWordFragment newInstance(String param1, String param2) {
        ChooseWordFragment fragment = new ChooseWordFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_choose_word, container, false);

        imageColor = (ImageView) rootView.findViewById(R.id.imageChooseWord);
        choice1 = (Button) rootView.findViewById(R.id.chooseWordChoice1);
        choice2 = (Button) rootView.findViewById(R.id.chooseWordChoice2);
        choice3 = (Button) rootView.findViewById(R.id.chooseWordChoice3);
        choice4 = (Button) rootView.findViewById(R.id.chooseWordChoice4);
        choiceAudio1 = (ImageButton) rootView.findViewById(R.id.chooseWordAudio1);
        choiceAudio2 = (ImageButton) rootView.findViewById(R.id.chooseWordAudio2);
        choiceAudio3 = (ImageButton) rootView.findViewById(R.id.chooseWordAudio3);
        choiceAudio4 = (ImageButton) rootView.findViewById(R.id.chooseWordAudio4);
        btnNext = (ImageButton) rootView.findViewById(R.id.chooseWordNextBtn);

        ExerciceActivity exerciceActivity = (ExerciceActivity) getActivity();
        exerciceActivity.setExercice(new Exercice());

        this.createOneExercise();

        return rootView;
    }

    public void createOneExercise() {
        resetViewForNewExercise();

        ExerciceActivity activity = (ExerciceActivity) ChooseWordFragment.this.getActivity();

        this.themeResources = activity.getRandTheme(4);
        this.indexGoodAnswer = new Random().nextInt(4);
        this.displayExercise();
    }

    public void resetViewForNewExercise() {
        ExerciceActivity exerciceActivity = (ExerciceActivity) ChooseWordFragment.this.getActivity();

        int color = Color.parseColor("#F7673AB7");
        choice1.setBackgroundColor(color);
        choice2.setBackgroundColor(color);
        choice3.setBackgroundColor(color);
        choice4.setBackgroundColor(color);
        btnNext.setVisibility(View.INVISIBLE);
    }

    public void displayExercise() {
        ThemeResource themeGoodAnswer = ChooseWordFragment.this.themeResources[ChooseWordFragment.this.indexGoodAnswer];
        imageColor.setImageResource(getContext().getResources().getIdentifier(themeGoodAnswer.getImage().split("\\.")[0], "drawable", getContext().getPackageName()));

        choice1.setText(themeResources[0].getName());
        choice2.setText(themeResources[1].getName());
        choice3.setText(themeResources[2].getName());
        choice4.setText(themeResources[3].getName());

        setOnClickListnerChoice(choice1);
        setOnClickListnerChoice(choice2);
        setOnClickListnerChoice(choice3);
        setOnClickListnerChoice(choice4);

        setAudioListener(choiceAudio1, themeResources[0]);
        setAudioListener(choiceAudio2, themeResources[1]);
        setAudioListener(choiceAudio3, themeResources[2]);
        setAudioListener(choiceAudio4, themeResources[3]);
    }

    public void setOnClickListnerChoice(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciceActivity exerciceActivity = (ExerciceActivity) ChooseWordFragment.this.getActivity();
                int idGoodAnswer = getResources().getIdentifier("chooseWordChoice" + (ChooseWordFragment.this.indexGoodAnswer + 1), "id", exerciceActivity.getPackageName());
                int idChoiceClicked = view.getId();
                Button choiceClicked = exerciceActivity.findViewById(idChoiceClicked);
                boolean canContinue;
                if(idChoiceClicked == idGoodAnswer) {
                    choiceClicked.setBackgroundColor(Color.parseColor("#FF03DAC5"));
                    canContinue = exerciceActivity.checkExerciceIfContinue(true);
                } else {
                    choiceClicked.setBackgroundColor(Color.parseColor("#FF0000"));
                    canContinue = exerciceActivity.checkExerciceIfContinue(false);
                }

                if(!canContinue) return ;

                btnNext.setVisibility(View.VISIBLE);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChooseWordFragment.this.createOneExercise();
                    }
                });

                choice1.setOnClickListener(null);
                choice2.setOnClickListener(null);
                choice3.setOnClickListener(null);
                choice4.setOnClickListener(null);
            }
        });
    }

    public void setAudioListener(ImageButton imgBtn, ThemeResource themeResource) {
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciceActivity exerciceActivity = (ExerciceActivity) ChooseWordFragment.this.getContext();
                int resourceId = exerciceActivity.getResources().getIdentifier(themeResource.getVoice().split("\\.")[0], "raw", exerciceActivity.getPackageName());
                exerciceActivity.playAudio(resourceId);
            }
        });
    }

}