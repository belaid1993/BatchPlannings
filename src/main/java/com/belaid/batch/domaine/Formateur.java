package com.belaid.batch.domaine;

public class Formateur {

    private Integer id;
    private String nom;
    private String prenom;
    private String adresseEmail;


    public Formateur(){}
    public Formateur(final Integer id, final String nom, final String prenom, final String adresseEmail) {
        super();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseEmail = adresseEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    @Override
    public String toString() {
        return "Formateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresseEmail='" + adresseEmail + '\'' +
                '}';
    }
}
