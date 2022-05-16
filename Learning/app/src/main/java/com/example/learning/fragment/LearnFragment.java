package com.example.learning.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning.R;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;


public class LearnFragment extends Fragment {
    private String PACKAGE_NAME;
    private MediaPlayer player;
    private ImageView image;
    private TextView libelle;
    private ImageButton audio;
    private ImageButton next;
    private ImageButton previous;
    private TextView libellePrev;
    private TextView libelleNext;
    private ThemeResource theme;
    String[] alph = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t", "u", "v", "w", "x", "y", "z"};
    int indice = 0;

    public void loadAudio(){
        stopPlayer();
        int audioID = getResources().getIdentifier(PACKAGE_NAME+":raw/"+theme.getVoice(), null, null);
        if(player!=null){
            player.release();
        }
        player = MediaPlayer.create(this.getContext(), audioID);
    }
    public void play(View v){
        if(player == null){
            loadAudio();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    private void stopPlayer(){
        if(player!=null){
            player.release();
            player = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();

        String strtext = getArguments().getString("categorie");
        Log.println(Log.VERBOSE, "ARG","===========CATEGORIE "+strtext);
        ThemeResource them = new ThemeResource(alph[indice], "alphabet_"+alph[indice], "alphabet_"+alph[indice]);

        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        image = (ImageView) rootView.findViewById(R.id.learnImage);
        libelle = (TextView) rootView.findViewById(R.id.learnLibelle);
        audio = (ImageButton) rootView.findViewById(R.id.learnAudio);
        next = (ImageButton)rootView.findViewById(R.id.learnNext);
        previous = (ImageButton)rootView.findViewById(R.id.learnPrev);
        libellePrev = (TextView) rootView.findViewById(R.id.libellePrev);
        libelleNext = (TextView) rootView.findViewById(R.id.libelleNext);

        // Inflate the layout for this fragment
        setTheme(them);
        return rootView;
    }

    private void playAudio(ImageButton btn, final Class classe){
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                play(v);
            }
        });
    }

    private void setTheme(ImageButton btn, ThemeResource t, int i){
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(i == 0){
                    indice++;
                }
                if(i == 1){
                    indice--;
                }
                setTheme(t);
            }
        });
    }

    private void setTheme(ThemeResource t){
        theme = t;
        int imgID = getResources().getIdentifier(PACKAGE_NAME+":drawable/"+theme.getImage(), null, null);
        int audioID = getResources().getIdentifier(PACKAGE_NAME+":raw/"+theme.getVoice(), null, null);
        int indiceprev = indice - 1;
        int indicenext = indice + 1;
        image.setImageResource(imgID);

        libelle.setText(theme.getName());

        playAudio(audio, this.getContext().getClass());
        if(indiceprev<0){
            previous.setVisibility(View.INVISIBLE);
            libellePrev.setVisibility(View.INVISIBLE);
        }else{
            previous.setVisibility(View.VISIBLE);
            libellePrev.setVisibility(View.VISIBLE);
            setTheme(previous, new ThemeResource(alph[indiceprev], "alphabet_"+alph[indiceprev], "alphabet_"+alph[indiceprev]), 1);
        }
        if(indicenext >= alph.length){
            next.setVisibility(View.INVISIBLE);
            libelleNext.setVisibility(View.INVISIBLE);
        }else{
            next.setVisibility(View.VISIBLE);
            libelleNext.setVisibility(View.VISIBLE);
            setTheme(next, new ThemeResource(alph[indicenext], "alphabet_"+alph[indicenext], "alphabet_"+alph[indicenext]), 0);
        }


    }


}