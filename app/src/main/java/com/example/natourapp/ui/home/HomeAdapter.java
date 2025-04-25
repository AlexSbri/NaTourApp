package com.example.natourapp.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.mostra_itinerario.MostraItinerarioActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.ItinerarioStat;

import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME="HomeViewModel:";
    private final List<HomeItem> homeItemList;
    private final List<ItinerarioStat> itinerarioStats;


    private ItinerarioStat getItinerarioStatFromId(int id){
        for (ItinerarioStat i:itinerarioStats) {
            if(i.getItinerario_id()==id) return i;
        }
        return new ItinerarioStat();
    }

    public HomeAdapter(List<HomeItem> homeItemList, List<ItinerarioStat> itinerarioStats)
    {
        this.homeItemList = homeItemList;
        this.itinerarioStats = itinerarioStats;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.getImageFilterViewProfilo().setImageResource(homeItemList.get(position).getImageFilterViewProfilo());
        holder.getNomeUtente().setText(homeItemList.get(position).getNomeUtente());
        holder.getTitoloItinerario().setText(homeItemList.get(position).getTitoloItinerario());
        holder.getImageFilterViewImmagineItinerario().setImageResource(homeItemList.get(position).getImageFilterViewImmagineItinerario());
        holder.getDescrizione().setText(homeItemList.get(position).getDescrizione());
        holder.getTextViewDifficult().setText(homeItemList.get(position).getImageFilterViewDifficult());
        if(homeItemList.get(position).getImageFilterViewDisability() != null){
            holder.getImageFilterViewDisability().setVisibility(View.VISIBLE);
            holder.getImageFilterViewDisability().setImageResource(homeItemList.get(position).getImageFilterViewDisability());
        }else
            holder.getImageFilterViewDisability().setVisibility(View.GONE);
        holder.getPosizione().setText(homeItemList.get(position).getPosizione());
        holder.getImageFilterViewPositionPin().setImageResource(homeItemList.get(position).getImageFilterViewPositionPin());
        holder.getImageFilterViewMenuButton().setImageResource(homeItemList.get(position).getImageFilterViewMenuButton());
        ItinerarioStat i = getItinerarioStatFromId(homeItemList.get(position).getItinerario_id());
        double d = i.getAvg();
        holder.getRatingBar().setRating((float) d);
        holder.getNumeroRecensioni().setText(String.format(Locale.ITALY,"%d",i.getNumero_recensioni()));
        holder.setItinerario_id(homeItemList.get(position).getItinerario_id());
    }
    @Override
    public int getItemCount() {
        return homeItemList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageFilterViewProfilo;
        private final TextView nomeUtente;
        private final TextView titoloItinerario;
        private final ImageView imageFilterViewImmagineItinerario;
        private final TextView descrizione;
        private final TextView textViewDifficult;
        private final ImageView imageFilterViewDisability;
        private final RatingBar ratingBar;
        private final TextView numeroRecensioni;
        private final TextView posizione;
        private final ImageView imageFilterViewPositionPin;
        private final ImageButton imageFilterViewMenuButton;
        private int itinerario_id;

        public void setItinerario_id(int itinerario_id) {
            this.itinerario_id = itinerario_id;
        }

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            imageFilterViewProfilo = itemView.findViewById(R.id.imageViewProfilo);
            nomeUtente = itemView.findViewById(R.id.textViewUsernameHomeItem);
            titoloItinerario = itemView.findViewById(R.id.textViewTitoloItinerarioHomeItem);
            imageFilterViewImmagineItinerario = itemView.findViewById(R.id.imageViewItinerarioHomeItem);
            descrizione = itemView.findViewById(R.id.textViewDescrizioneHomeItem);
            textViewDifficult = itemView.findViewById(R.id.textViewDifficultIndicator);
            imageFilterViewDisability = itemView.findViewById(R.id.imageViewWheelChair);
            ratingBar = itemView.findViewById(R.id.ratingBarHomeItem);
            numeroRecensioni = itemView.findViewById(R.id.textViewNumeroRecensioni);
            posizione = itemView.findViewById(R.id.textViewPosizioneItinerarioHomeItem);
            imageFilterViewPositionPin = itemView.findViewById(R.id.imageFilterViewPinImage);
            imageFilterViewMenuButton = itemView.findViewById(R.id.imageFilterButtonMenuHomeItem);

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME+"item cliccato");
                Intent intent = new Intent(itemView.getContext(),MostraItinerarioActivity.class);
                intent.putExtra("titolo_itinerario",titoloItinerario.getText().toString());
                intent.putExtra("itinerario_id",itinerario_id);
                itemView.getContext().startActivity(intent);
            });
            imageFilterViewMenuButton.setOnClickListener(v ->
                    DialogAdapter.showDialog(itemView.getContext(),itinerario_id,nomeUtente.getText().toString()));
        }

        public ImageView getImageFilterViewProfilo() {
            return imageFilterViewProfilo;
        }

        public TextView getNomeUtente() {
            return nomeUtente;
        }

        public TextView getTitoloItinerario() {
            return titoloItinerario;
        }

        public ImageView getImageFilterViewImmagineItinerario() {
            return imageFilterViewImmagineItinerario;
        }

        public TextView getDescrizione() {
            return descrizione;
        }

        public TextView getTextViewDifficult() {
            return textViewDifficult;
        }

        public ImageView getImageFilterViewDisability() {
            return imageFilterViewDisability;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getNumeroRecensioni() {
            return numeroRecensioni;
        }

        public TextView getPosizione() {
            return posizione;
        }

        public ImageView getImageFilterViewPositionPin() {
            return imageFilterViewPositionPin;
        }

        public ImageButton getImageFilterViewMenuButton() {
            return imageFilterViewMenuButton;
        }
    }
}
