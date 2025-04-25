package com.example.natourapp.Activities.mostra_itinerario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;
import com.example.natourapp.entities.Recensione;

import java.util.List;

public class MostraitinerarioAdapter extends RecyclerView.Adapter<MostraitinerarioAdapter.MostraItinerarioViewHolder> {

    private final List<Recensione> recensioneList;
    public MostraitinerarioAdapter(List<Recensione> recensioneList) {
        this.recensioneList = recensioneList;
    }

    @NonNull
    @Override
    public MostraItinerarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recensione_item_mostra_itinerario,parent,false);
        return new MostraItinerarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostraItinerarioViewHolder holder, int position) {
        holder.getImmagineProfilo().setImageResource(recensioneList.get(position).getUtente_entity().getImmagineResource());
        holder.getRecensione().setText(recensioneList.get(position).getDescrizione());
        holder.getTitolorecensione().setText(recensioneList.get(position).getTitolo_recensione());
        holder.getRatingBar().setRating((float) recensioneList.get(position).getValutazione());
        holder.getUsername().setText(recensioneList.get(position).getUtente_entity().getUsername());
    }

    @Override
    public int getItemCount() {
        return recensioneList.size();
    }

    public static class MostraItinerarioViewHolder extends RecyclerView.ViewHolder {

        private final ImageView immagineProfilo;
        private final TextView username;
        private final TextView titolorecensione;
        private final TextView recensione;
        private final RatingBar ratingBar;

        public MostraItinerarioViewHolder(@NonNull View itemView) {
            super(itemView);
            immagineProfilo = itemView.findViewById(R.id.imageViewProfiloMostraItinerario);
            username = itemView.findViewById(R.id.textViewUsernameMostraItinerario);
            titolorecensione = itemView.findViewById(R.id.textViewTitoloRecensioneMostraItinerario);
            recensione = itemView.findViewById(R.id.textViewRecensioneMostraItinerarioItem);
            ratingBar = itemView.findViewById(R.id.ratingBarRecensioneItem);
        }

        public ImageView getImmagineProfilo() {
            return immagineProfilo;
        }

        public TextView getUsername() {
            return username;
        }

        public TextView getTitolorecensione() {
            return titolorecensione;
        }

        public TextView getRecensione() {
            return recensione;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }
    }
}
