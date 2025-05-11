
package com.example.mediatekformationmobile.vue;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.controleur.Controle;
import com.example.mediatekformationmobile.modele.FavorisSQLite;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;

public class FavorisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Controle controle;
    private FavorisSQLite favorisSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        controle = Controle.getInstance();
        favorisSQLite = new FavorisSQLite(this);
        recyclerView = findViewById(R.id.lstFavoris);

        afficherFavoris();
    }

    private void afficherFavoris() {
        ArrayList<Formation> toutes = controle.getLesFormations();
        ArrayList<Formation> onlyFavoris = new ArrayList<>();

        for (Formation formation : toutes) {
            if (favorisSQLite.estFavori(formation.getId())) {
                onlyFavoris.add(formation);
            }
        }

        FormationListAdapter adapter = new FormationListAdapter(onlyFavoris, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
