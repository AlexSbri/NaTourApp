package com.example.natourapp.ui.playlist.mostra_playlist;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.mostra_itinerario.MostraItinerarioActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.Itinerario;

import java.util.List;

public class MostraPlaylistAdapter extends RecyclerView.Adapter<MostraPlaylistAdapter.MostraPlaylistViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME ="MostraPlaylistAdapter:";
    private final List<Itinerario> contentPlaylist;
    private final int playlist_id;
    public MostraPlaylistAdapter(List<Itinerario> contentPlaylist,int playlist_id) {
        this.contentPlaylist = contentPlaylist;
        this.playlist_id=playlist_id;
    }

    @NonNull
    @Override
    public MostraPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,parent,false);
        return new MostraPlaylistViewHolder(view,playlist_id);
    }

    @Override
    public void onBindViewHolder(@NonNull MostraPlaylistViewHolder holder, int position) {
        holder.getImmagineItinerario().setImageResource(contentPlaylist.get(position).getImgResource());
        holder.getNomeItinerario().setText(contentPlaylist.get(position).getNome_itinerario());
        holder.getDescrizioneItinerario().setText(contentPlaylist.get(position).getDescrizione());
        holder.setItinerario_id(contentPlaylist.get(position).getItinerario_id());
    }

    @Override
    public int getItemCount() {
        return contentPlaylist.size();
    }

    public static class MostraPlaylistViewHolder extends RecyclerView.ViewHolder {
        private final ImageView immagineItinerario;
        private final TextView nomeItinerario;
        private final TextView descrizioneItinerario;
        private int itinerario_id;

        public MostraPlaylistViewHolder(@NonNull View itemView,int playlist_id) {
            super(itemView);
            immagineItinerario = itemView.findViewById(R.id.imageViewPlaylist);
            nomeItinerario = itemView.findViewById(R.id.textViewNomePlaylist);
            descrizioneItinerario = itemView.findViewById(R.id.textViewDescrizionePlaylist);
            ImageButton imageViewButtonDots = itemView.findViewById(R.id.imageFilterButtonPlaylist);
            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME +"item cliccato");
                Intent intent = new Intent(itemView.getContext(), MostraItinerarioActivity.class);
                intent.putExtra("titolo_itinerario", nomeItinerario.getText());
                intent.putExtra("itinerario_id",itinerario_id);
                v.getContext().startActivity(intent);
            });

            imageViewButtonDots.setClickable(true);
            imageViewButtonDots.setOnClickListener(view -> DialogAdapterContentPlaylist
                    .showDialog(view.getContext(),itinerario_id,playlist_id));
        }

        public void setItinerario_id(int itinerario_id) {
            this.itinerario_id = itinerario_id;
        }

        public int getItinerario_id() {
            return itinerario_id;
        }

        public ImageView getImmagineItinerario() {
            return immagineItinerario;
        }

        public TextView getNomeItinerario() {
            return nomeItinerario;
        }

        public TextView getDescrizioneItinerario() {
            return descrizioneItinerario;
        }
    }
}
