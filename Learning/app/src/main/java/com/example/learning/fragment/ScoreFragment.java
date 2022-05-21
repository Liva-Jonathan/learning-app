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
import android.widget.Toast;

import com.example.learning.R;
import com.example.learning.controller.ExerciceActivity;
import com.example.learning.controller.MainActivity;
import com.example.learning.controller.ui.login.LoginActivity;
import com.example.learning.model.Exercice;
import com.example.learning.model.User;
import com.example.learning.utils.Constante;
import com.example.learning.utils.RetrofitInterface;
import com.example.learning.utils.Serializer;

import org.w3c.dom.Text;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScoreFragment extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private ProgressBar progressBar;
    private TextView progressText;
    private Handler mHandler = new Handler();
    private int status = 0;
    private Button save;
    private Button retry;
    private User logged;
    private String BASE_URL = "http://192.168.88.16:1010";
    private final String filename = "log";

    public ScoreFragment(){
        Log.println(Log.VERBOSE, "LOG", "=====INI SAVED");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.println(Log.VERBOSE, "LOG", "=====INI CRATE");
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Exercice exo = ((ExerciceActivity)ScoreFragment.this.getActivity()).getExercice();

        int p = 0;
        if(exo.getTotale() != 0){
            p = (100 * exo.getBonne()) / exo.getTotale();
        }
        int progress = p;
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

        progressBar = rootView.findViewById(R.id.progressBarScore);
        progressText = rootView.findViewById(R.id.progressText);
        progressText.setVisibility(View.INVISIBLE);
        setSave(rootView.findViewById(R.id.buttonSaveScore));
        getSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAuth()){
                    Toast.makeText(ScoreFragment.this.getActivity().getApplicationContext(), "ENREGISTRER", Toast.LENGTH_LONG).show();
                    //ScoreFragment.saveScore(progress, ((ExerciceActivity)ScoreFragment.this.getActivity()).getThemeID(), new Integer(ScoreFragment.this.getLogged().get_id()), ScoreFragment.this.retrofitInterface);
                    Intent intent = new Intent(ScoreFragment.this.getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ScoreFragment.this.getActivity(), LoginActivity.class);
                    intent.putExtra("idtheme", ((ExerciceActivity)ScoreFragment.this.getActivity()).getThemeID());
                    intent.putExtra("score", progress);
                    startActivity(intent);
                }
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

    public boolean checkAuth(){
        User u =(User) Serializer.deSerialize(filename, this.getActivity());
        if(u != null){
            if(u.getEmail() != null){
                setLogged(u);
                return true;
            }
        }
        return false;
    }

    public static void saveScore(int score, int idtheme, int iduser, RetrofitInterface retrofitInterface){
        HashMap<String, Object> map = new HashMap<>();
        map.put("idtheme", idtheme);
        map.put("score", score);
        map.put("id", ""+iduser);

        Call<User> call = retrofitInterface.saveScore(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    Log.println(Log.VERBOSE, "LOG", "=====SCORE SAVED");
                    //Toast.makeText(ScoreFragment.this.getActivity().getApplicationContext(), "Score enregistrer", Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);*/
                }else if(response.code() == 201){
                    Log.println(Log.VERBOSE, "LOG", "=====SCORE UNSAVED");
                    //Toast.makeText(ScoreFragment.this.getActivity().getApplicationContext(), "Score non enregistrer", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public User getLogged() {
        return logged;
    }

    public void setLogged(User logged) {
        this.logged = logged;
    }
}