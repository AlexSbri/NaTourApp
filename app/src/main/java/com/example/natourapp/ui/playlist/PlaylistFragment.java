package com.example.natourapp.ui.playlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;

public class PlaylistFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "PlaylistFragment:";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPlaylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        String utente = Cognito.SharedPrefApp.getInstance().getUsername(getContext());
        PlaylistViewModel playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        playlistViewModel.loadPlaylistByutenteUsername(utente);
        recyclerView.setAdapter(new PlaylistAdapter(playlistViewModel.getPlaylistList()));

        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Log.d(TAG, CLASSNAME +"bottone crea playlist cliccato");
            NavDirections navDirections = PlaylistFragmentDirections.actionNavigationPlaylistToCreaPlaylistFragment();
            Navigation.findNavController(v).navigate(navDirections);
        });
        return view;
    }
}