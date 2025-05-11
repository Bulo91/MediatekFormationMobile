package com.example.mediatekformationmobile.controleur;

import com.example.mediatekformationmobile.MyApplication;
import com.example.mediatekformationmobile.modele.AccesDistant;
import com.example.mediatekformationmobile.modele.FavorisSQLite;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;

public class Controle {

    private static Controle instance = null;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
    private Formation formation = null;
    private static AccesDistant accesDistant;

    /**
     * Constructeur privé
     */
    private Controle() {
        super();
    }

    /**
     * Récupération de l'instance unique de Controle
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

    /**
     * Met à jour la liste des formations et supprime les favoris locaux obsolètes
     */
    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;

        // Nettoyage des favoris locaux supprimés du serveur
        FavorisSQLite favorisSQLite = new FavorisSQLite(MyApplication.getContext());
        ArrayList<Integer> favoris = favorisSQLite.getTousLesFavoris();

        for (int id : favoris) {
            boolean existeEncore = false;
            for (Formation f : lesFormations) {
                if (f.getId() == id) {
                    existeEncore = true;
                    break;
                }
            }
            if (!existeEncore) {
                favorisSQLite.supprimerFavori(id);
            }
        }
    }

    /**
     * Retourne la liste des formations dont le titre contient le filtre (insensible à la casse)
     */
    public ArrayList<Formation> getFormationsFiltrees(String filtre) {
        ArrayList<Formation> formationsFiltrees = new ArrayList<>();
        if (filtre != null && !filtre.isEmpty()) {
            for (Formation f : lesFormations) {
                if (f.getTitle().toUpperCase().contains(filtre.toUpperCase())) {
                    formationsFiltrees.add(f);
                }
            }
        } else {
            formationsFiltrees = new ArrayList<>(lesFormations);
        }
        return formationsFiltrees;
    }
}
