package com.belaid.batch.domaine;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Formation {

    private String code;
    private String libelle;
    private String descriptif;

    public Formation(){}

    public Formation(final String code, final String libelle, final String descriptif) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.descriptif = descriptif;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", descriptif='" + descriptif + '\'' +
                '}';
    }
}
