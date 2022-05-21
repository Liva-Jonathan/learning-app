package com.example.learning.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.learning.model.Theme;
import com.example.learning.model.ThemeResource;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Learning.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setDb(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("DATABASE", "onCreate invoked");
        createDatabaseSchema(sqLiteDatabase);
        insertDatabaseInitialData(sqLiteDatabase);
    }

    public void createDatabaseSchema(SQLiteDatabase db) {
        String themeSql = "create table T_theme ("
                        + "    idTheme integer primary key autoincrement,"
                        + "    name text not null,"
                        + "    image text not null,"
                        + "    description text not null,"
                        + "    urlVideo text not null,"
                        + "    typeExo text not null"
                        + ")";
        db.execSQL(themeSql);

        String themeResourceSql = "create table T_themeResource ("
                                + "    idThemeResource integer primary key autoincrement,"
                                + "    idTheme integer not null,"
                                + "    name text not null,"
                                + "    image text not null,"
                                + "    voice text not null,"
                                + "    resOrder integer not null,"
                                + "    foreign key(idTheme) references T_theme(idTheme)"
                                + ")";
        db.execSQL(themeResourceSql);
    }

    public void insertDatabaseInitialData(SQLiteDatabase db) {
        List<Theme> themes = new ArrayList<>();
        themes.add(new Theme(0, "Alphabet", "alphabet.png", "Un alphabet est un système d'écriture constitué d'un ensemble de symboles dont chacun représente, par exemple, un des phonèmes d’une langue.",
                                "5xuZxGirWQI", "chooseImage"));
        themes.add(new Theme(0, "Nombres", "nombres.png", "Un nombre est un objet mathématique utilisé pour compter, mesurer et étiqueter. Les exemples originaux sont les nombres naturels 1, 2, 3, 4, etc. Les nombres peuvent être représentés dans la langue avec des mots numériques.",
                "dhj9SqrIZqI", "sorting"));
        themes.add(new Theme(0, "Couleurs", "couleurs.png", "La couleur est la perception visuelle de l'aspect d'une surface ou d'une lumière, basée, sans lui être rigoureusement liée, sur la répartition spectrale de la lumière, qui stimule des cellules nerveuses spécialisées situées sur la rétine nommées cônes.",
                "m50U33brThk", "chooseWord"));
        themes.add(new Theme(0, "Jours de la semaine", "jours.png", "Les noms des jours de la semaine, dans les langues latines, tiennent leur origine des noms de divinités de la mythologie romaine. Les noms samedi et dimanche sont deux exceptions.",
                "eA5jSbKd5cM", "writing"));
//        themes.add(new Theme(0, "Formes", "formes.png", "Les noms des jours de la semaine, dans les langues latines, tiennent leur origine des noms de divinités de la mythologie romaine. Les noms samedi et dimanche sont deux exceptions.",
//                "video", "dragging"));

        for(Theme theme: themes) {
            String themeDescription = theme.getDescription().replace("'", "''");
            String sql = "insert into T_theme (name, image, description, urlVideo, typeExo) values ('"
                        + theme.getName() + "','" + theme.getImage() + "','" + themeDescription + "','" + theme.getUrlVideo()
                        + "','" + theme.getTypeExo() + "')";
            db.execSQL(sql);
        }

        List<ThemeResource> themeResources = new ArrayList<>();

        // Alphabet
        themeResources.add(new ThemeResource(0, 1, "A", "a.jpg", "a.mp3", 1));
        themeResources.add(new ThemeResource(0, 1, "B", "b.jpg", "b.mp3", 2));
        themeResources.add(new ThemeResource(0, 1, "C", "c.jpg", "c.mp3", 3));
        themeResources.add(new ThemeResource(0, 1, "D", "d.jpg", "d.mp3", 4));
        themeResources.add(new ThemeResource(0, 1, "E", "e.jpg", "e.mp3", 5));
        themeResources.add(new ThemeResource(0, 1, "F", "f.jpg", "f.mp3", 6));
        themeResources.add(new ThemeResource(0, 1, "G", "g.jpg", "g.mp3", 7));
        themeResources.add(new ThemeResource(0, 1, "H", "h.jpg", "h.mp3", 8));
        themeResources.add(new ThemeResource(0, 1, "I", "i.jpg", "i.mp3", 9));
        themeResources.add(new ThemeResource(0, 1, "J", "j.jpg", "j.mp3", 10));
        themeResources.add(new ThemeResource(0, 1, "K", "k.jpg", "k.mp3", 11));
        themeResources.add(new ThemeResource(0, 1, "L", "l.jpg", "l.mp3", 11));
        themeResources.add(new ThemeResource(0, 1, "M", "m.jpg", "m.mp3", 13));
        themeResources.add(new ThemeResource(0, 1, "N", "n.jpg", "n.mp3", 14));
        themeResources.add(new ThemeResource(0, 1, "O", "o.jpg", "o.mp3", 15));
        themeResources.add(new ThemeResource(0, 1, "P", "p.jpg", "p.mp3", 16));
        themeResources.add(new ThemeResource(0, 1, "Q", "q.jpg", "q.mp3", 17));
        themeResources.add(new ThemeResource(0, 1, "R", "r.jpg", "r.mp3", 18));
        themeResources.add(new ThemeResource(0, 1, "S", "s.jpg", "s.mp3", 19));
        themeResources.add(new ThemeResource(0, 1, "T", "t.jpg", "t.mp3", 20));
        themeResources.add(new ThemeResource(0, 1, "U", "u.jpg", "u.mp3", 21));
        themeResources.add(new ThemeResource(0, 1, "V", "v.jpg", "v.mp3", 22));
        themeResources.add(new ThemeResource(0, 1, "W", "w.jpg", "w.mp3", 23));
        themeResources.add(new ThemeResource(0, 1, "X", "x.jpg", "x.mp3", 24));
        themeResources.add(new ThemeResource(0, 1, "Y", "y.jpg", "y.mp3", 25));
        themeResources.add(new ThemeResource(0, 1, "Z", "z.jpg", "z.mp3", 26));

        // Nombres
        themeResources.add(new ThemeResource(0, 2, "0", "zero.jpg", "zero.mp3", 1));
        themeResources.add(new ThemeResource(0, 2, "1", "un.jpg", "un.mp3", 2));
        themeResources.add(new ThemeResource(0, 2, "2", "deux.jpg", "deux.mp3", 3));
        themeResources.add(new ThemeResource(0, 2, "3", "trois.jpg", "trois.mp3", 4));
        themeResources.add(new ThemeResource(0, 2, "4", "quatre.jpg", "quatre.mp3", 5));
        themeResources.add(new ThemeResource(0, 2, "5", "cinq.jpg", "cinq.mp3", 6));
        themeResources.add(new ThemeResource(0, 2, "6", "six.jpg", "six.mp3", 7));
        themeResources.add(new ThemeResource(0, 2, "7", "sept.jpg", "sept.mp3", 8));
        themeResources.add(new ThemeResource(0, 2, "8", "huit.jpg", "huit.mp3", 9));
        themeResources.add(new ThemeResource(0, 2, "9", "neuf.jpg", "neuf.mp3", 10));
        themeResources.add(new ThemeResource(0, 2, "10", "dix.jpg", "dix.mp3", 11));

        // Couleurs
        themeResources.add(new ThemeResource(0, 3, "Bleu", "bleu.png", "bleu.mp3", 1));
        themeResources.add(new ThemeResource(0, 3, "Jaune", "jaune.png", "jaune.mp3", 2));
        themeResources.add(new ThemeResource(0, 3, "Marron", "marron.png", "marron.mp3", 3));
        themeResources.add(new ThemeResource(0, 3, "Noir", "noir.png", "noir.mp3", 4));
        themeResources.add(new ThemeResource(0, 3, "Blanc", "blanc.png", "blanc.mp3", 5));
        themeResources.add(new ThemeResource(0, 3, "Orange", "orange.png", "orange.mp3", 6));
        themeResources.add(new ThemeResource(0, 3, "Rouge", "rouge.png", "rouge.mp3", 7));
        themeResources.add(new ThemeResource(0, 3, "Vert", "vert.png", "vert.mp3", 8));
        themeResources.add(new ThemeResource(0, 3, "Violet", "violet.png", "violet.mp3", 9));

        // Jours de la semaine
        themeResources.add(new ThemeResource(0, 4, "Lundi", "lundi.png", "lundi.mp3", 1));
        themeResources.add(new ThemeResource(0, 4, "Mardi", "mardi.png", "mardi.mp3", 2));
        themeResources.add(new ThemeResource(0, 4, "Mercredi", "mercredi.png", "mercredi.mp3", 3));
        themeResources.add(new ThemeResource(0, 4, "Jeudi", "jeudi.png", "jeudi.mp3", 4));
        themeResources.add(new ThemeResource(0, 4, "Vendredi", "vendredi.png", "vendredi.mp3", 5));
        themeResources.add(new ThemeResource(0, 4, "Samedi", "samedi.png", "samedi.mp3", 6));
        themeResources.add(new ThemeResource(0, 4, "Dimanche", "dimanche.png", "dimanche.mp3", 7));

        // Formes
//        themeResources.add(new ThemeResource(0, 5, "Carré", "carre.png", "carre.mp3", 1));
//        themeResources.add(new ThemeResource(0, 5, "Coeur", "coeur.png", "coeur.mp3", 2));
//        themeResources.add(new ThemeResource(0, 5, "Etoile", "etoile.png", "etoile.mp3", 3));
//        themeResources.add(new ThemeResource(0, 5, "Losange", "losange.png", "losange.mp3", 4));
//        themeResources.add(new ThemeResource(0, 5, "Rond", "rond.png", "rond.mp3", 5));
//        themeResources.add(new ThemeResource(0, 5, "Triangle", "triangle.png", "triangle.mp3", 6));

        for(ThemeResource themeResource: themeResources) {
            String sql = "insert into T_themeResource (idTheme, name, image, voice, resOrder) values ("
                    + themeResource.getIdTheme() + ",'" + themeResource.getName() + "','" + themeResource.getImage() + "','" + themeResource.getVoice()
                    + "'," + themeResource.getResOrder() + ")";
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
