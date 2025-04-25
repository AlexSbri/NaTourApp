package com.example.natourapp.ui.playlist.mostra_playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;
import com.example.natourapp.ui.playlist.PlaylistViewModel;

public class MostraPlaylistFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mostra_playlist_fragment, container, false);
        String nomePlaylist = MostraPlaylistFragmentArgs.fromBundle(getArguments()).getNomePlaylist();
        int idPlaylist = MostraPlaylistFragmentArgs.fromBundle(getArguments()).getIdPlaylist();
        requireActivity().setTitle(nomePlaylist);
        RecyclerView recyclerView = view.findViewById(R.id.mostraItinerariPlaylistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PlaylistViewModel playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        playlistViewModel.loadContentPlaylistByPlaylistId(idPlaylist);
        recyclerView.setAdapter(new MostraPlaylistAdapter(playlistViewModel.getPlaylisContent(),idPlaylist));
        return view;
    }
}