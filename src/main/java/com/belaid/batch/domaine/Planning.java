package com.belaid.batch.domaine;

import java.util.List;

public class Planning {

    private Formateur formateur;
    private List<PlanningItem> seances;

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public List<PlanningItem> getSeances() {
        return seances;
    }

    public void setSeances(List<PlanningItem> seances) {
        this.seances = seances;
    }
}
