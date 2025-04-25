package com.example.natourapp.ui.profilo.segnalazioni_ricevute;

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
import com.example.natourapp.ui.profilo.SegnalazioniFragmentViewModel;

public class SegnalazioniRicevuteFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_segnalazioni_ricevute, container, false);
        recyclerView = view.findViewById(R.id.RecyclerViewSegnalazioniRicevute);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        int itinerario_id = SegnalazioniRicevuteFragmentArgs.fromBundle(getArguments()).getItinerarioId();
        SegnalazioniFragmentViewModel segnalazioniRicevuteViewModel = new ViewModelProvider(requireActivity()).get(SegnalazioniFragmentViewModel.class);
        segnalazioniRicevuteViewModel.loadSegnalazioni(itinerario_id);
        if(segnalazioniRicevuteViewModel.getSegnalazioneList().isEmpty())
            Toast.makeText(getContext(), "Hai risposto a tutte le segnalazioni", Toast.LENGTH_SHORT).show();
        else recyclerView.setAdapter(new SegnalazioniRicevuteAdapter(segnalazioniRicevuteViewModel.getSegnalazioneList()));
        return view;
    }
}
