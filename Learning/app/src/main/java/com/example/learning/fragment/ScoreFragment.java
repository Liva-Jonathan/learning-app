package com.example.learning.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.controller.ui.login.LoginActivity;
import com.example.learning.model.Exercice;
import com.example.learning.utils.Constante;

import org.w3c.dom.Text;


public class ScoreFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView progressText;
    private Handler mHandler = new Handler();
    private int status = 0;
    private Button save;
    private Button retry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        progressBar = rootView.findViewById(R.id.progressBarScore);
        progressText = rootView.findViewById(R.id.progressText);
        progressText.setVisibility(View.INVISIBLE);
        setSave(rootView.findViewById(R.id.buttonSaveScore));
        getSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        setRetry(rootView.findViewById(R.id.buttonRetry));
        getRetry().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = null;
                ExerciceActivity exerciceActivity = ((ExerciceActivity) ScoreFragment.this.getActivity());
                exerciceActivity.resetExercice();
                if(exerciceActivity.getThemeID() == Constante.themeID_jour){
                    myFragment = new WritingFragment();
                }
                if(exerciceActivity.getThemeID() == Constante.themeID_nombre){
                    myFragment = new SortingFragment();
                }
                FragmentManager fragmentManager = exerciceActivity.getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.exercicefragment, myFragment).commit();
            }
        });

        Exercice exo = ((ExerciceActivity)ScoreFragment.this.getActivity()).getExercice();
        int progress = (100 * exo.getBonne()) / exo.getTotale();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(status < progress){
                    status++;
                    android.os.SystemClock.sleep(progress);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressText.setText("Score: "+exo.getBonne() + "/"+exo.getTotale() + progress+"%");
                        progressText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();

        return rootView;
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public Button getRetry() {
        return retry;
    }

    public void setRetry(Button retry) {
        this.retry = retry;
    }
}