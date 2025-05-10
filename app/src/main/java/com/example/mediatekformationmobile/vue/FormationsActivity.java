package com.example.mediatekformationmobile.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
    private ArrayList<Formation> lesFormations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * initialisations
     */
    private void init() {
        controle = Controle.getInstance();

        btnFiltrer = findViewById(R.id.btnFiltrer);
        txtFiltre = findViewById(R.id.txtFiltre);

        // Attente de 0.5s avant d'afficher la liste initiale
        new Handler().postDelayed(() -> {
            lesFormations = controle.getLesFormations();
            afficherListe();
        }, 500);

        btnFiltrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filtre = txtFiltre.getText().toString().trim();
                if (filtre.isEmpty()) {
                    lesFormations = controle.getLesFormations();
                } else {
                    lesFormations = controle.getFormationsFiltrees(filtre);
                }
                afficherListe();
            }
        });
    }

    /**
     * affichage de la liste dans le RecyclerView
     */
    private void afficherListe() {
        if (lesFormations != null) {
            Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
            RecyclerView lstFormations = findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations, FormationsActivity.this);
            lstFormations.setAdapter(adapter);
            lstFormations.setLayoutManager(new LinearLayoutManager(FormationsActivity.this));
        }
    }
}
