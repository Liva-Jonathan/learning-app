package com.example.learning.model;

public class Theme {
    private String id;
    private String libelle;
    private String src_image;
    private String src_audio;
    private String valeur;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSrc_image() {
        return src_image;
    }

    public void setSrc_image(String src_image) {
        this.src_image = src_image;
    }

    public String getSrc_audio() {
        return src_audio;
    }

    public void setSrc_audio(String src_audio) {
        this.src_audio = src_audio;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Theme(String libelle, String src_image, String src_audio) {
        this.libelle = libelle;
        this.src_image = src_image;
        this.src_audio = src_audio;
    }
}
