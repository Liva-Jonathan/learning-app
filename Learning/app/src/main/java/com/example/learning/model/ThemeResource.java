package com.example.learning.model;

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
