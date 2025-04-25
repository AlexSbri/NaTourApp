package com.example.natourapp.ui.cerca;

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
import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.services.image.ImageCollection;
import com.example.natourapp.ui.home.DialogAdapter;

import java.util.List;
import java.util.Locale;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "SearchResultAdapter:";
    private final List<Itinerario> searchResultList;
    private final List<ItinerarioStat> itinerarioStatList;

    public SearchResultAdapter(List<Itinerario> list,List<ItinerarioStat> itinerarioStatList){
        this.searchResultList = list;
        this.itinerarioStatList = itinerarioStatList;
    }

    private ItinerarioStat getItinerarioStatFromItinerarioId(int itinerario_id) {
        for (ItinerarioStat itinerario:itinerarioStatList) {
            if(itinerario.getItinerario_id() == itinerario_id) return itinerario;
        }
        return new ItinerarioStat();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item,parent,false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        ItinerarioStat itinerarioStat = getItinerarioStatFromItinerarioId(searchResultList.get(position).getItinerario_id());
        holder.getImageFilterViewProfilo().setImageResource(ImageCollection.getInstance().getRandomImmaginiProfiloResource());
        holder.getNomeUtente().setText(searchResultList.get(position).getUtente_entity().getUsername());
        holder.getTitoloItinerario().setText(searchResultList.get(position).getNome_itinerario());
        holder.getImageFilterViewImmagineItinerario().setImageResource(ImageCollection.getInstance().getRandomImmaginiItinerarioResource());
        holder.getDescrizione().setText(searchResultList.get(position).getDescrizione());
        holder.setItinerario_id(searchResultList.get(position).getItinerario_id());
        holder.getTextViewDifficult().setText(searchResultList.get(position).getDifficolta());
        if(searchResultList.get(position).isServizi_disabili())
            holder.getImageFilterViewDisability().setImageResource(R.mipmap.wheelchair_foreground);
        holder.getRatingBar().setRating((float) itinerarioStat.getAvg());
        holder.getNumeroRecensioni().setText(String.format(Locale.ITALIAN,"%d",itinerarioStat.getNumero_recensioni()));
        holder.getImageFilterViewPositionPin().setImageResource(R.drawable.ic_location_pin);
        holder.getImageFilterViewMenuButton().setImageResource(R.drawable.ic_dot_menu);
        holder.getPuntoDiPartenza().setText(searchResultList.get(position).getPunto_di_partenza());
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder{

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

        public SearchResultViewHolder(@NonNull View itemView) {
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
                Log.d(TAG, CLASSNAME +"item cliccato");
                Intent intent = new Intent(itemView.getContext(), MostraItinerarioActivity.class);
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

        public TextView getPuntoDiPartenza() {
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
