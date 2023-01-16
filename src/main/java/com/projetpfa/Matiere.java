package com.projetpfa;

public class Matiere {
    int id;
    String nom_mat;
    int coef;
    int volume;
    String module;

    public Matiere() {
        super();
    }

    public Matiere(int id, String nom_mat, String module, int coef, int volume) {
        this.id = id;
        this.nom_mat = nom_mat;
        this.coef = coef;
        this.volume = volume;
        this.module = module;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_mat() {
        return nom_mat;
    }

    public void setNom_mat(String nom_mat) {
        this.nom_mat = nom_mat;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
