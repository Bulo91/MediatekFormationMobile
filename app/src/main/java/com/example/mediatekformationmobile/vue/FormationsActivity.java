package com.example.mediatekformationmobile.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.controleur.Controle;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;

public class FormationsActivity extends AppCompatActivity {

    private Controle controle;
    private Button btnFiltrer;
    private EditText txtFiltre;
    private RecyclerView lstFormations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * Initialisations
     */
    private void init() {
        controle = Controle.getInstance();
        btnFiltrer = findViewById(R.id.btnFiltrer);
        txtFiltre = findViewById(R.id.txtFiltre);
        lstFormations = findViewById(R.id.lstFormations);

        // Affiche toutes les formations au démarrage
        creerListe(controle.getLesFormations());

        // Clic sur le bouton "Filtrer"
        btnFiltrer.setOnClickListener(v -> {
            String filtre = txtFiltre.getText().toString().trim();
            ArrayList<Formation> resultats = controle.getFormationsFiltrees(filtre);
            creerListe(resultats);
        });
    }

    /**
     * Création de la liste à afficher
     */
    private void creerListe(ArrayList<Formation> liste) {
        if (liste != null) {
            Collections.sort(liste, Collections.reverseOrder());
            FormationListAdapter adapter = new FormationListAdapter(liste, FormationsActivity.this);
            lstFormations.setAdapter(adapter);
            lstFormations.setLayoutManager(new LinearLayoutManager(FormationsActivity.this));
        }
    }
}
