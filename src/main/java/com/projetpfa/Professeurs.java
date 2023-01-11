package com.projetpfa;

import java.sql.Date;

public class Professeurs {
    int id;
    String Nom;
    String Prenom;
    int Matiere;
    int age;
    int departement;
    Date date_naissance;

    public Professeurs() {
        super();
    }

    public Professeurs(int id, String Nom, String Prenom, int Matiere, int age, int departement,
            Date date_naissance) {
        this.id = id;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Matiere = Matiere;
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
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public int getMatiere() {
        return Matiere;
    }

    public void setMatiere(int matiere) {
        Matiere = matiere;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDepartement() {
        return departement;
    }

    public void setDepartement(int departement) {
        this.departement = departement;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
}
