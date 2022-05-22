package com.example.learning.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learning.R;
import com.example.learning.controller.LearnActivity;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;
import com.example.learning.utils.Util;


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
    private Theme mytheme;
    private View rootView;


    public Theme getMytheme() {
        return mytheme;
    }

    public void setMytheme(Theme mytheme) {
        this.mytheme = mytheme;
    }

    public void loadAudio(){
        stopPlayer();
        //int audioID = getResources().getIdentifier(PACKAGE_NAME+":raw/alphabet_a", null, null);
        Uri audioURI = Util.getAudioURI(theme.getVoice());
        if(player!=null){
            player.release();
        }

        player = MediaPlayer.create(this.getContext(), audioURI);
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

        //themeID = getArguments().getInt("theme");
        mytheme = (Theme)getArguments().get("theme");

        ThemeResource them = ((LearnActivity)this.getActivity()).getThemeResourceFirst(getMytheme().getIdTheme());

        rootView = inflater.inflate(R.layout.fragment_learn, container, false);

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

    private void setTheme(ImageButton btn, int id){
        LearnActivity learnActivity = ((LearnActivity)this.getActivity());
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Log.println(Log.VERBOSE, "CLICK","===========NEXT" );
                ThemeResource them = learnActivity.getThemeResource(id);
                setTheme(them);
            }
        });
    }

    private void setTheme(ThemeResource t){
        theme = null;
        theme = t;
        Uri imageURI = Util.getImageURI(theme.getImage());
        //int audioID = getResources().getIdentifier(PACKAGE_NAME+":raw/"+theme.getVoice(), null, null);

        image.setImageURI(imageURI);

        libelle.setText(theme.getName());

        playAudio(audio, this.getContext().getClass());
        if(this.getTheme().getPrevious() == null){
            previous.setVisibility(View.INVISIBLE);
            libellePrev.setVisibility(View.INVISIBLE);
        }else{
            previous.setVisibility(View.VISIBLE);
            libellePrev.setVisibility(View.VISIBLE);
            setTheme(previous, this.getTheme().getPrevious().getIdThemeResource());
        }
        if(this.getTheme().getNext() == null){
            next.setVisibility(View.INVISIBLE);
            libelleNext.setVisibility(View.INVISIBLE);
        }else{
            next.setVisibility(View.VISIBLE);
            libelleNext.setVisibility(View.VISIBLE);
            setTheme(next, this.getTheme().getNext().getIdThemeResource());
        }
        play(rootView);
    }

    public ThemeResource getTheme() {
        return theme;
    }

}