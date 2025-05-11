package com.example.mediatekformationmobile.vue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.controleur.Controle;
import com.example.mediatekformationmobile.modele.FavorisSQLite;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;

public class FormationListAdapter extends RecyclerView.Adapter<FormationListAdapter.ViewHolder> {

    private ArrayList<Formation> lesFormations;
    private Context context;
    private Controle controle;

    public FormationListAdapter(ArrayList<Formation> lesFormations, Context context) {
        this.lesFormations = lesFormations;
        this.context = context;
        this.controle = Controle.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.layout_liste_formations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Formation formation = lesFormations.get(position);
        holder.txtListeTitle.setText(formation.getTitle());
        holder.txtListPublishedAt.setText(formation.getPublishedAtToString());

        FavorisSQLite favorisSQLite = new FavorisSQLite(context);

        // Affiche le cœur rouge ou gris
        if (favorisSQLite.estFavori(formation.getId())) {
            holder.imgFavori.setImageResource(R.drawable.coeur_rouge);
        } else {
            holder.imgFavori.setImageResource(R.drawable.coeur_gris);
        }

        // Gère le clic sur le cœur
        holder.imgFavori.setOnClickListener(v -> {
            if (favorisSQLite.estFavori(formation.getId())) {
                favorisSQLite.supprimerFavori(formation.getId());
                holder.imgFavori.setImageResource(R.drawable.coeur_gris);
            } else {
                favorisSQLite.ajouterFavori(formation.getId());
                holder.imgFavori.setImageResource(R.drawable.coeur_rouge);
            }
        });

        // Clic sur le titre ou la date → affiche le détail
        holder.txtListeTitle.setOnClickListener(v -> ouvrirUneFormationActivity(position));
        holder.txtListPublishedAt.setOnClickListener(v -> ouvrirUneFormationActivity(position));
    }

    private void ouvrirUneFormationActivity(int position) {
        controle.setFormation(lesFormations.get(position));
        Intent intent = new Intent(context, UneFormationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return lesFormations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgFavori;
        public final TextView txtListPublishedAt;
        public final TextView txtListeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtListeTitle = itemView.findViewById(R.id.txtListTitle);
            txtListPublishedAt = itemView.findViewById(R.id.txtListPublishedAt);
            imgFavori = itemView.findViewById(R.id.imgFavori);
        }
    }
}
