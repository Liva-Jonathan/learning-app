package com.example.learning.Model;

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
