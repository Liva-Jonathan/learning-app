package com.example.learning.model;

import android.database.Cursor;

import com.example.learning.utils.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class Theme {

    private int idTheme;
    private String name;
    private String image;
    private String description;
    private String urlVideo;
    private String typeExo;

    public Theme(int idTheme, String name, String image, String description, String urlVideo, String typeExo) {
        setIdTheme(idTheme);
        setName(name);
        setImage(image);
        setDescription(description);
        setUrlVideo(urlVideo);
        setTypeExo(typeExo);
    }

    public static List<Theme> getAllThemes(DatabaseManager dbManager) {
        List<Theme> themes = new ArrayList<>();

        String sql = "select * from T_Theme order by idTheme limit 4";
        Cursor cursor = dbManager.getDb().rawQuery(sql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int idTheme = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            String description = cursor.getString(3);
            String urlVideo = cursor.getString(4);
            String typeExo = cursor.getString(5);
            Theme theme = new Theme(idTheme, name, image, description, urlVideo, typeExo);
            themes.add(theme);
            cursor.moveToNext();
        }
        cursor.close();

        return themes;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getTypeExo() {
        return typeExo;
    }

    public void setTypeExo(String typeExo) {
        this.typeExo = typeExo;
    }
}
