package com.example.learning.controller;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learning.R;
import com.example.learning.fragment.LearnFragment;
import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;
import com.example.learning.utils.DatabaseManager;

public class LearnActivity extends AppCompatActivity {
    private DatabaseManager db;
    private Theme mytheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //themeID = extras.getInt("theme");
            mytheme = (Theme)extras.get("theme");

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final LearnFragment myFragment = new LearnFragment();
        Bundle bundle = new Bundle();
        //bundle.putInt("theme", getThemeID());
        bundle.putSerializable("theme", getMytheme());
        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.learnfragment, myFragment).commit();
        getSupportActionBar().setTitle("Apprendre les '"+getMytheme().getName() + "'");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBB86FC")));
        init();
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


    public ThemeResource getThemeResource(int id){
        ThemeResource result = null;
        String req = "select b.* \n" +
                "from T_themeResource as b\n" +
                "inner join (select * from T_themeResource where idThemeResource = "+id+" order by resOrder asc) as d\n" +
                "on d.idTheme = b.idTheme\n" +
                "where b.resOrder between (d.resOrder - 1) and (d.resOrder + 1) order by b.resOrder asc limit 3";
        Cursor curseur = getDb().getReadableDatabase().rawQuery(req, null);
        int nb_result = curseur.getCount();
        //Log.println(Log.VERBOSE, "REQUETE", "========="+req);
        //Log.println(Log.VERBOSE, "REQUETE-RESULT", "====count "+nb_result);
        ThemeResource prev = null;
        ThemeResource next = null;
        if(nb_result == 3){
            curseur.moveToNext();
            prev = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            curseur.moveToNext();
            result = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            curseur.moveToNext();
            next = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            result.setNext(next);
            result.setPrevious(prev);
        }
        else if(nb_result == 2){
            curseur.moveToNext();
            if(curseur.getInt(0) == id){
                result = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
                curseur.moveToNext();
                next = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
                result.setNext(next);
            }else{
                curseur.moveToLast();
                if(curseur.getInt(0) == id){
                    result = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
                }
                curseur.moveToPrevious();
                prev = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
                result.setPrevious(prev);
            }
        }
        curseur.close();
        return result;
    }

    public Theme getMytheme() {
        return mytheme;
    }

    public ThemeResource getThemeResourceFirst(int id){
        ThemeResource result = null;
        String req = "select b.* \n" +
                "from T_themeResource as b\n" +
                "inner join (select * from T_themeResource where idTheme = "+id+" order by resOrder asc limit 1) as d\n" +
                "on d.idTheme = b.idTheme\n" +
                "where b.resOrder between (d.resOrder - 1) and (d.resOrder + 1) order by b.resOrder asc";
        Cursor curseur = getDb().getReadableDatabase().rawQuery(req, null);
        int nb_result = curseur.getCount();
        //Log.println(Log.VERBOSE, "REQUETE", "========="+req);
        //Log.println(Log.VERBOSE, "REQUETE-RESULT", "====count "+nb_result);
        ThemeResource next = null;
        if(nb_result == 2){
            curseur.moveToNext();
            result = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            curseur.moveToNext();
            next = new ThemeResource(curseur.getInt(0), curseur.getInt(1), curseur.getString(2), curseur.getString(3), curseur.getString(4), curseur.getInt(5));
            result.setNext(next);
        }
        curseur.close();
        return result;
    }


}