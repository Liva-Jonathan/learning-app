package com.example.learning.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.example.learning.R;
import com.example.learning.fragment.ChooseImageFragment;
import com.example.learning.fragment.ChooseWordFragment;
import com.example.learning.fragment.DraggingFragment;
import com.example.learning.fragment.LearnFragment;
import com.example.learning.fragment.ScoreFragment;
import com.example.learning.fragment.SortingFragment;
import com.example.learning.fragment.WritingFragment;
import com.example.learning.utils.DatabaseManager;
import com.example.learning.model.Exercice;
import com.example.learning.model.Question;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;
import com.example.learning.utils.Constante;
import com.example.learning.utils.DatabaseManager;

import java.util.List;

public class ExerciceActivity extends AppCompatActivity {
    private DatabaseManager db;
    private int themeID;
    private Exercice exercice;

    public Exercice getExercice() {
        return exercice;
    }

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setDbManager(new DatabaseManager(this));

        Fragment myFragment = null;
        resetExercice();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            themeID = extras.getInt("theme");
        }
        Log.println(Log.VERBOSE, "THEMEEXO", "===NOM  == id "+themeID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(getThemeID() == Constante.themeID_jour){
            myFragment = new WritingFragment();
        }
        if(getThemeID() == Constante.themeID_nombre){
            myFragment = new SortingFragment();
        }
        if(getThemeID() == Constante.themeID_forme){
            myFragment = new DraggingFragment();
        }
        if(getThemeID() == Constante.themeID_alphabet) {
            myFragment = new ChooseImageFragment();
        }
        if(getThemeID() == Constante.themeID_couleur) {
            myFragment = new ChooseWordFragment();
        }

        Bundle bundle = new Bundle();
        String myMessage = "Alphabet";
        bundle.putString("categorie", myMessage );
        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.exercicefragment, myFragment).commit();
        init();
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public int getThemeID(){
        return this.themeID;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    public void init() {
        setDb(new DatabaseManager(this));
    }

    public DatabaseManager getDb() {
        return db;
    }

    public void setDb(DatabaseManager db) {
        this.db = db;
    }

    public ThemeResource getRandTheme(){
        ThemeResource result = null;
        String req = "SELECT * FROM T_themeResource\n" +
                "where idTheme = "+this.getThemeID()+"\n" +
                "ORDER BY random() \n" +
                "LIMIT 1;";
        Cursor curseur = getDb().getReadableDatabase().rawQuery(req, null);
        int nb_result = curseur.getCount();
        Log.println(Log.VERBOSE, "REQUETE", "========="+req);
        Log.println(Log.VERBOSE, "REQUETE-RESULT", "====count "+nb_result);
        curseur.moveToNext();
        result = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
        curseur.close();
        return result;
    }

    public ThemeResource [] getRandTheme(int nb){
        ThemeResource[]result = null;
        String req = "SELECT * FROM T_themeResource\n" +
                "where idTheme = "+this.getThemeID()+"\n" +
                "ORDER BY random() \n" +
                "LIMIT "+nb;
        Cursor curseur = getDb().getReadableDatabase().rawQuery(req, null);
        int nb_result = curseur.getCount();
        Log.println(Log.VERBOSE, "REQUETE", "========="+req);
        Log.println(Log.VERBOSE, "REQUETE-RESULT", "====count "+nb_result);
        result = new ThemeResource[nb];
        int i = 0;
        curseur.moveToNext();
        while(!curseur.isAfterLast()){
            ThemeResource tmp = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            result [i] = tmp;
            curseur.moveToNext();
            i++;
        }
        curseur.close();
        return result;
    }

    public boolean checkOrderTab(List<ThemeResource> tab, int order){
        Log.println(Log.VERBOSE, "RANDTHEME", "=========checkorder ");
        for(int i =0; i<tab.size(); i++){
            int i_suivant = i + 1;
            Log.println(Log.VERBOSE, "RANDTHEME", "=========value "+tab.get(i).getResOrder());
            if(order == 0){
                if(i_suivant < tab.size()){
                    if(tab.get(i).getResOrder() > tab.get(i_suivant).getResOrder()){
                        return false;
                    }
                }
            }else if(order == 1){
                if(i_suivant < tab.size()){
                    if(tab.get(i).getResOrder() < tab.get(i_suivant).getResOrder()){
                        return false;
                    }
                }
            }else{
                return false;
            }
        }
        return true;
    }

    public boolean checkOrderTab(ThemeResource[] tab, int order){
        Log.println(Log.VERBOSE, "RANDTHEME", "=========checkorder ");
        for(int i =0; i<tab.length; i++){
            int i_suivant = i + 1;
            Log.println(Log.VERBOSE, "RANDTHEME", "=========value "+tab[i].getResOrder());
            if(order == 0){
                if(i_suivant < tab.length){
                    if(tab[i].getResOrder() > tab[i_suivant].getResOrder()){
                        return false;
                    }
                }
            }else if(order == 1){
                if(i_suivant < tab.length){
                    if(tab[i].getResOrder() < tab[i_suivant].getResOrder()){
                        return false;
                    }
                }
            }else{
                return false;
            }
        }
        return true;
    }

    public ThemeResource [] getURandTheme(int nb, int orderFinal){
        ThemeResource [] result = new ThemeResource[nb];
        Log.println(Log.VERBOSE, "RANDTHEME", "=========order "+orderFinal + " =====nb "+nb);
        while(true){
            result = getRandTheme(nb);
            boolean resultOrder = checkOrderTab(result, orderFinal);
            Log.println(Log.VERBOSE, "RANDTHEME", "=========order result "+resultOrder);
            if(!resultOrder){
                break;
            }
        }
        return result;
    }

    public Question getRandQuestion(){
        Question result = null;
        String req = "select T_question.*, T_themeResource.idTheme, T_themeResource.name,\n" +
                "\tT_themeResource.image, T_themeResource.voice, T_themeResource.resOrder\n" +
                "\tfrom T_question\n" +
                "\tjoin T_themeResource\n" +
                "\ton T_question.idThemeResource = T_themeResource.idThemeResource\n" +
                "\twhere T_themeResource.idTheme = \n"+this.getThemeID() +
                "\tORDER BY random()\n" +
                "\tlimit 1";
        Log.println(Log.VERBOSE, "REQUETE", "========="+req);
        Cursor curseur = getDb().getReadableDatabase().rawQuery(req, null);
        int nb_result = curseur.getCount();
        Log.println(Log.VERBOSE, "REQUETE-RESULT", "====count "+nb_result);
        int i = 0;
        curseur.moveToNext();
        while(!curseur.isAfterLast()){
            ThemeResource reponse = new ThemeResource(curseur.getInt(1), curseur.getInt(3), curseur.getString(4), curseur.getString(5), curseur.getString(6), curseur.getInt(7));
            result = new Question(curseur.getInt(0), curseur.getInt(1), curseur.getString(2));
            result.setReponse(reponse);
            curseur.moveToNext();
        }
        curseur.close();
        return result;
    }

    public boolean checkReponse(Question question, String reponse){
        Log.println(Log.VERBOSE, "RESULT", "===Q : "+question.getReponse().getName()+ "====R "+reponse);
        if(question.getReponse().getName().compareToIgnoreCase(reponse) != 0){
            return false;
        }
        return true;
    }

    public void resetExercice(){
        setExercice(new Exercice());
    }

    public void showScore(){
        ScoreFragment myFragment = new ScoreFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.exercicefragment, myFragment).commit();
    }

    public void playAudio(int resourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resourceId);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public boolean checkExerciceIfContinue(boolean isAnswerTrue) {
        if(isAnswerTrue) {
            getExercice().setBonne(getExercice().getBonne() + 1);
        } else {
            getExercice().setMauvaise(getExercice().getMauvaise() + 1);
        }
        getExercice().setTotale(getExercice().getTotale() + 1);

        if(getExercice().getTotale() >= getExercice().getFin()) {
            this.showScore();
            return false;
        }
        return true;
    }

}
