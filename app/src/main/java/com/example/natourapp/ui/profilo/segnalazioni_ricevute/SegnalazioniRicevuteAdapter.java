package com.example.natourapp.ui.profilo.segnalazioni_ricevute;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.segnalazione.risposta_segnalazione.RispostaSegnalazioneActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.Segnalazione;

import java.util.List;

public class SegnalazioniRicevuteAdapter extends RecyclerView.Adapter<SegnalazioniRicevuteAdapter.SegnalazioniRicevuteViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "SegnalazioniRicevuteAdapter:";
    private final List<Segnalazione> segnalazioneList;
    public SegnalazioniRicevuteAdapter(List<Segnalazione> segnalazioneList){
        this.segnalazioneList=segnalazioneList;
    }
    @NonNull
    @Override
    public SegnalazioniRicevuteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_segnalazioni_ricevute,parent,false);
        return new SegnalazioniRicevuteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SegnalazioniRicevuteViewHolder holder, int position) {
        if(segnalazioneList.get(position).getRisposta_segnalazione()==null) {
            holder.getTextViewTitoloSegnalazioni().setText(segnalazioneList.get(position).getTitolo_segnalazione());
            holder.getTextViewDescrizioniSegnalazioni().setText(segnalazioneList.get(position).getDescrizione_segnalazione());
            holder.segnalazione_id = segnalazioneList.get(position).getSegnalazione_id();
            holder.user_id = segnalazioneList.get(position).getUtente_entity().getUsername();
        }
    }

    @Override
    public int getItemCount() {
        return segnalazioneList.size();
    }

    public static class SegnalazioniRicevuteViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTitoloSegnalazioni;
        private final TextView textViewDescrizioniSegnalazioni;
        private int segnalazione_id;
        private String user_id;

        public SegnalazioniRicevuteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitoloSegnalazioni = itemView.findViewById(R.id.textViewTitoloSegnalazioniRicevute);
            textViewDescrizioniSegnalazioni = itemView.findViewById(R.id.textViewDescrizioneSegnalazioneRicevute);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME+"item cliccato");
                Intent intent = new Intent(v.getContext(), RispostaSegnalazioneActivity.class);
                intent.putExtra("segnalazione_id",segnalazione_id);
                intent.putExtra("user_id",user_id);
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
