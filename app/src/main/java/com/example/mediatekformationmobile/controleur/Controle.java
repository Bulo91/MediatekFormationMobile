package com.example.mediatekformationmobile.controleur;

import com.example.mediatekformationmobile.modele.AccesDistant;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;

public class Controle {

    private static Controle instance = null;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
    private Formation formation = null;
    private static AccesDistant accesDistant;

    /**
     * constructeur privé
     */
    private Controle() {
        super();
    }

    /**
     * récupération de l'instance unique de Controle
     * @return instance
     */
    public static final Controle getInstance() {
        if (Controle.instance == null) {
            Controle.instance = new Controle();
            accesDistant = AccesDistant.getInstance();
            accesDistant.envoi("tous", "formation", null);
        }
        return Controle.instance;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }

    /**
     * Retourne les formations dont le titre contient le filtre (insensible à la casse)
     */
    public ArrayList<Formation> getFormationsFiltrees(String filtre) {
        ArrayList<Formation> listeFiltree = new ArrayList<>();
        filtre = filtre.toUpperCase();
        for (Formation formation : lesFormations) {
            if (formation.getTitle().toUpperCase().contains(filtre)) {
                listeFiltree.add(formation);
            }
        }
        return listeFiltree;
    }
}
