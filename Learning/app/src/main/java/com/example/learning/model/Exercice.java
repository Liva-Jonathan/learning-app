package com.example.learning.model;

public class Exercice {
    private final int fin = 5;
    private int totale;
    private int bonne;
    private int mauvaise;

    public int getFin() {
        return fin;
    }

    public int getTotale() {
        return totale;
    }

    public void setTotale(int totale) {
        this.totale = totale;
    }

    public int getBonne() {
        return bonne;
    }

    public void setBonne(int bonne) {
        this.bonne = bonne;
    }

    public int getMauvaise() {
        return mauvaise;
    }

    public void setMauvaise(int mauvaise) {
        this.mauvaise = mauvaise;
    }
}
