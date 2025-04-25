package com.example.natourapp.ui.profilo.segnalazioni_effettuate;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.segnalazione.risposta_segnalazione.RispostaRicevutaActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.Segnalazione;

import java.util.List;

public class SegnalazioniEffettuateAdapter extends RecyclerView.Adapter<SegnalazioniEffettuateAdapter.SegnalazioniEffettuateViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "SegnalazioniEffettuateAdapter:";
    private final List<Segnalazione> segnalazioneList;

    public SegnalazioniEffettuateAdapter(List<Segnalazione> segnalazioneList) {
        this.segnalazioneList = segnalazioneList;
    }

    @NonNull
    @Override
    public SegnalazioniEffettuateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_segnalazioni_effettuate, parent, false);
        return new SegnalazioniEffettuateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SegnalazioniEffettuateViewHolder holder, int position) {
        if (segnalazioneList.get(position).getRisposta_segnalazione() != null) {
            holder.getTextViewTitoloSegnalazioni().setText(segnalazioneList.get(position).getTitolo_segnalazione());
            holder.getTextViewDescrizioniSegnalazioni().setText(segnalazioneList.get(position).getDescrizione_segnalazione());
            holder.segnalazione_id = segnalazioneList.get(position).getSegnalazione_id();
        }
    }

    @Override
    public int getItemCount() {
        return segnalazioneList.size();
    }

    public static class SegnalazioniEffettuateViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitoloSegnalazioni;
        private final TextView textViewDescrizioniSegnalazioni;
        private int segnalazione_id;

        public SegnalazioniEffettuateViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitoloSegnalazioni = itemView.findViewById(R.id.textViewTitoloSegnalazioniEffettuate);
            textViewDescrizioniSegnalazioni = itemView.findViewById(R.id.textViewDescrizioneSegnalazioneEffettuate);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME +"item cliccato");
                Intent intent = new Intent(v.getContext(), RispostaRicevutaActivity.class);
                intent.putExtra("segnalazione_id", segnalazione_id);
                v.getContext().startActivity(intent);
            });
        }

        public TextView getTextViewTitoloSegnalazioni() {
            return textViewTitoloSegnalazioni;
        }

        public TextView getTextViewDescrizioniSegnalazioni() {
            return textViewDescrizioniSegnalazioni;
        }
    }
}