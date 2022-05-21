package com.example.learning.model;

import android.database.Cursor;

import com.example.learning.utils.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class ThemeResource {

    private int idThemeResource;
    private int idTheme;
    private String name;
    private String image;
    private String voice;
    private int resOrder;

    public ThemeResource(int idThemeResource, int idTheme, String name, String image, String voice, int resOrder) {
        setIdThemeResource(idThemeResource);
        setIdTheme(idTheme);
        setName(name);
        setImage(image);
        setVoice(voice);
        setResOrder(resOrder);
    }

    public static List<ThemeResource> getThemeResources(DatabaseManager dbManager, int idTheme) {
        List<ThemeResource> themeResources = new ArrayList<>();

        String sql = "select * from T_ThemeResource where idTheme=" + idTheme + " order by resOrder";
        Cursor cursor = dbManager.getDb().rawQuery(sql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int idThemeResource = cursor.getInt(0);
            String name = cursor.getString(2);
            String image = cursor.getString(3);
            String voice = cursor.getString(4);
            int resOrder = cursor.getInt(5);
            ThemeResource themeResource = new ThemeResource(idThemeResource, idTheme, name, image, voice, resOrder);
            themeResources.add(themeResource);
            cursor.moveToNext();
        }
        cursor.close();

        return themeResources;
    }

    public ThemeResource(String name, String image, String voice) {
        setName(name);
        setImage(image);
        setVoice(voice);
    }

    public int getIdThemeResource() {
        return idThemeResource;
    }

    public void setIdThemeResource(int idThemeResource) {
        this.idThemeResource = idThemeResource;
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

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public int getResOrder() {
        return resOrder;
    }

    public void setResOrder(int resOrder) {
        this.resOrder = resOrder;
    }
}
