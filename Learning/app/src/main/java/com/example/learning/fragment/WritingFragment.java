package com.example.learning.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learning.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class WritingFragment extends Fragment {
    private String PACKAGE_NAME;
    private Button button;
    private LinearLayout typingZone;
    private LinearLayout outputZone;
    private TextView textWriting;
    private Button submitWriting;
    private Character [] letters = new Character[12];

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();


        View rootView = inflater.inflate(R.layout.fragment_writing, container, false);
        outputZone = (LinearLayout) rootView.findViewById(R.id.outputZone);
        typingZone = (LinearLayout) rootView.findViewById(R.id.typingZone);
        textWriting = (TextView) rootView.findViewById(R.id.textWriting);
        textWriting.setText("Allo");
        submitWriting = (Button)rootView.findViewById(R.id.submitWriting);
        generateLetters("lundi");

        generateKeyboard(typingZone);

        getStringOutput();
        return rootView;
    }

    public Character[] shuffleLetters(Character[] arrayOfLetter){
        List<Character> list = Arrays.asList(arrayOfLetter);

        Collections.shuffle(list);

        list.toArray(arrayOfLetter);

        return arrayOfLetter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Character generateAlphabetic(){
        Random random = new Random();

        char generatedCharacter = (char) (random.nextInt(26) + 'a');
        return generatedCharacter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void generateLetters(String word){
        int nbWord = word.length();
        for(int i = 0; i<letters.length; i++){
            if(i >= nbWord){
                letters[i] = generateAlphabetic();
            }else{
                letters[i] = word.charAt(i);
            }
        }
        letters = shuffleLetters(letters);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void generateKeyboard(LinearLayout layout){
        int indice = 0;
        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this.getContext());
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j<4; j++){
                Button btnTag = setKeyboard(letters[indice].toString(), j + (i * 10), this.getContext(), R.color.purple_200);
                addLetter(btnTag, this.getContext());
                row.addView(btnTag);
                indice++;
            }
            layout.addView(row);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Button setKeyboard(String text, int id, Context context, int color){
        Button btnTag = new Button(context);
        btnTag.setAllCaps(true);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        btnTag.setText(text);
        if(color>=0){
            btnTag.setBackgroundTintList(context.getResources().getColorStateList(color));
        }
        btnTag.setId(id);
        return btnTag;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addLetter(Button btn, Context context){
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.println(Log.VERBOSE, "TYPELETTER", "==== id "+btn.getId());
                btn.setVisibility(View.INVISIBLE);
                Button btnTagOut = setKeyboard(btn.getText().toString(), btn.getId(), context, -1);
                removeLetter(btnTagOut, context);
                outputZone.addView(btnTagOut);
                submitWriting.setVisibility(View.VISIBLE);
            }
        });
    }

    private void removeLetter(Button btn, Context context){
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.println(Log.VERBOSE, "REMOVELETTER", "==== id "+btn.getId());
                //Log.println(Log.VERBOSE, "WIDTH", "==f==zf"+button.getLayoutParams().width);
                ((ViewManager)btn.getParent()).removeView(btn);
                typingZone.findViewById(btn.getId()).setVisibility(View.VISIBLE);
                if(outputZone.getChildCount() == 0){
                    submitWriting.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getStringOutput(){
        submitWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for(int i = 0; i<outputZone.getChildCount(); i++){
                    Button btn = (Button)outputZone.getChildAt(i);
                    result = result + btn.getText().toString();
                }
                Log.println(Log.VERBOSE, "RESULT", "===== "+result);
            }
        });
    }


}