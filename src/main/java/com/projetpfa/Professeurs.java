package com.projetpfa;

import java.sql.Date;

public class Professeurs {
    int id;
    String nom;
    String prenom;
    String matiere;
    int age;
    String departement;
    Date date_naissance;

    public Professeurs() {
        super();
    }

    public Professeurs(int id, String nom, String prenom, String matiere, int age, String departement,
            Date date_naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.matiere = matiere;
        this.age = age;
        this.date_naissance = date_naissance;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
}
