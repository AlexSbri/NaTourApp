package com.example.natourapp.ui.profilo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.mostra_itinerario.MostraItinerarioActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Segnalazione;

import java.util.List;

public class ItinerariProfiloAdapter extends RecyclerView.Adapter<ItinerariProfiloAdapter.ItinerariViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "ItinerariProfiloAdapter:";
    private final List<Itinerario> itinerarioList;
    private final List<Segnalazione> segnalazioneList;

    private Segnalazione getSegnalazioneFromId(int id){
        for (Segnalazione s:segnalazioneList) {
            if(s.getItinerario_entity().getItinerario_id()==id) return s;
        }
        return null;
    }
    public ItinerariProfiloAdapter(List<Itinerario> itinerarioList,List<Segnalazione> segnalazioneList) {
        this.itinerarioList = itinerarioList;
        this.segnalazioneList=segnalazioneList;
    }


    @NonNull
    @Override
    public ItinerariViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.miei_itinerari_item_layout,parent,false);
        return new ItinerariViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItinerariViewHolder holder, int position) {
        holder.getDescrizione().setText(itinerarioList.get(position).getDescrizione());
        holder.getNomeItinerario().setText(itinerarioList.get(position).getNome_itinerario());
        holder.setItinerario_id(itinerarioList.get(position).getItinerario_id());
        holder.getImgItinerario().setImageResource(itinerarioList.get(position).getImgResource());
        Segnalazione s = getSegnalazioneFromId(itinerarioList.get(position).getItinerario_id());
        if(s!=null){
            holder.getImageViewSegnalazione().setVisibility(View.VISIBLE);
        } else holder.getImageViewSegnalazione().setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return itinerarioList.size();
    }

    public static class ItinerariViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgItinerario;
        private final TextView nomeItinerario;
        private final TextView descrizione;
        private final ImageButton imageViewSegnalazione;
        private int itinerario_id;

        public void setItinerario_id(int itinerario_id) {
            this.itinerario_id = itinerario_id;
        }

        public ItinerariViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItinerario = itemView.findViewById(R.id.imageViewItinerario);
            nomeItinerario = itemView.findViewById(R.id.textViewNomeItinerario);
            descrizione = itemView.findViewById(R.id.textViewDescrizione);
            imageViewSegnalazione = itemView.findViewById(R.id.imageSegnalazione);
            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME + "item cliccato");
                Intent intent = new Intent(itemView.getContext(), MostraItinerarioActivity.class);
                intent.putExtra("itinerario_id",itinerario_id);
                intent.putExtra("titolo_itinerario",nomeItinerario.getText().toString());
                v.getContext().startActivity(intent);
            });
            imageViewSegnalazione.setOnClickListener(view -> {
                Log.d(TAG, CLASSNAME +"image warning cliccato");
                NavDirections navDirections = ProfiloFragmentDirections.actionNavigationProfiloToSegnalazioniRicevuteFragment(itinerario_id);
                Navigation.findNavController(view).navigate(navDirections);
            });
        }

        public ImageView getImgItinerario() {
            return imgItinerario;
        }

        public TextView getDescrizione() {
            return descrizione;
        }

        public TextView getNomeItinerario() {
            return nomeItinerario;
        }

        public ImageButton getImageViewSegnalazione(){return imageViewSegnalazione;}
    }
}
