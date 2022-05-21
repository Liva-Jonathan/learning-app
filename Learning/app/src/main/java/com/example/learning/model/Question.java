package com.example.learning.model;

public class Question {
    private int idQuestion;
    private int idThemeResource;
    private String question;
    private ThemeResource reponse;

    public ThemeResource getReponse() {
        return reponse;
    }

    public void setReponse(ThemeResource reponse) {
        this.reponse = reponse;
    }

    public Question() {

    }

    public Question(int idQuestion, int idThemeResource, String question) {
        this.idQuestion = idQuestion;
        this.idThemeResource = idThemeResource;
        this.question = question;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdThemeResource() {
        return idThemeResource;
    }

    public void setIdThemeResource(int idThemeResource) {
        this.idThemeResource = idThemeResource;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
