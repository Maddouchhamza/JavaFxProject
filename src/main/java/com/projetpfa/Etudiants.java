package com.projetpfa;

public class Etudiants {
    int id;
    String nom;
    String prenom;
    String ville;
    int age;
    String departement;

    public Etudiants() {
        super();
    }

    public Etudiants(int id, String nom, String prenom, String ville, int age, String departement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.age = age;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

}
