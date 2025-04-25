package com.example.natourapp.ui.profilo.segnalazioni_effettuate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.ui.profilo.SegnalazioniFragmentViewModel;

public class SegnalazioneEffettuateFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_segnalazioni_effettuate, container, false);
        recyclerView = view.findViewById(R.id.RecyclerViewSegnalazioniEffettuate);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        String username = Cognito.SharedPrefApp.getInstance().getUsername(getContext());
        SegnalazioniFragmentViewModel segnalazioneEffettuateViewModel = new ViewModelProvider(requireActivity()).get(SegnalazioniFragmentViewModel.class);
        segnalazioneEffettuateViewModel.loadSegnalazioni(username);
        if(segnalazioneEffettuateViewModel.getSegnalazioneList().isEmpty())
            Toast.makeText(getContext(), "Non hai ricevute risposte alle tue segnalazioni", Toast.LENGTH_SHORT).show();
        else recyclerView.setAdapter(new SegnalazioniEffettuateAdapter(segnalazioneEffettuateViewModel.getSegnalazioneList()));
        return view;
    }
}
