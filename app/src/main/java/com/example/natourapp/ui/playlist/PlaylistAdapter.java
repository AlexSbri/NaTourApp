package com.example.natourapp.ui.playlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;
import com.example.natourapp.entities.Playlist;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "PlaylistAdapter:";
    private final List<Playlist> playlistList;

    public PlaylistAdapter(List<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,parent,false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.getImmaginePlaylist().setImageResource(playlistList.get(position).getCopertinaPlaylist());
        holder.getNomePlaylist().setText(playlistList.get(position).getTitolo_playlist());
        holder.getDescrizionePlaylist().setText(playlistList.get(position).getDescrizione_playlist());
        holder.setPlaylistId(playlistList.get(position).getPlaylist_id());
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private final ImageView immaginePlaylist;
        private final TextView nomePlaylist;
        private final TextView descrizionePlaylist;
        private Integer playlistId;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            immaginePlaylist = itemView.findViewById(R.id.imageViewPlaylist);
            nomePlaylist = itemView.findViewById(R.id.textViewNomePlaylist);
            descrizionePlaylist = itemView.findViewById(R.id.textViewDescrizionePlaylist);
            ImageButton imageViewButtonDots = itemView.findViewById(R.id.imageFilterButtonPlaylist);
            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                Log.d(TAG, CLASSNAME + "item cliccato");
                String nomePlaylistname = nomePlaylist.getText().toString();
                //NavDirections navDirections = PlaylistFragmentDirections.actionNavigationPlaylistToMostraPlaylistFragment();
                PlaylistFragmentDirections.ActionNavigationPlaylistToMostraPlaylistFragment args =
                        PlaylistFragmentDirections.actionNavigationPlaylistToMostraPlaylistFragment(getPlaylistId());
                args.setNomePlaylist(nomePlaylistname);
                Navigation.findNavController(v).navigate(args);
            });
            imageViewButtonDots.setClickable(true);
            imageViewButtonDots.setOnClickListener(view -> DialogAdapterPlaylist.showDialog(view.getContext(),playlistId));

        }
        public void setPlaylistId(Integer playlistId){this.playlistId = playlistId;}

        public Integer getPlaylistId(){return playlistId;}

        public ImageView getImmaginePlaylist() {
            return immaginePlaylist;
        }

        public TextView getNomePlaylist() {
            return nomePlaylist;
        }

        public TextView getDescrizionePlaylist() {
            return descrizionePlaylist;
        }
    }
}
