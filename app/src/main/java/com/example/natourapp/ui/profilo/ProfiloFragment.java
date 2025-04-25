package com.example.natourapp.ui.profilo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.LoginActivity;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;

public class ProfiloFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "ProfiloFragment:";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilo_fragment, container, false);
        Button logoutButton = view.findViewById(R.id.buttonLogout);
        ImageView immagineProfilo = view.findViewById(R.id.immagineProfilo);
        immagineProfilo.setImageResource(R.drawable.profilo_sample);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMieiItinerari);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        logoutButton.setOnClickListener(v -> {
            Log.d(TAG, CLASSNAME+" bottone logout cliccato");
            Cognito cognito = new Cognito(getContext());
            cognito.logOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        String utenteId = Cognito.SharedPrefApp.getInstance().getUsername(getContext());
        TextView textView = view.findViewById(R.id.textViewNomeProfilo);
        textView.setText(utenteId);
        ProfiloViewModel profiloViewModel = new ViewModelProvider(requireActivity()).get(ProfiloViewModel.class);
        profiloViewModel.loadItinerariUtenteAndSegnalazioni(utenteId);
        recyclerView.setAdapter(new ItinerariProfiloAdapter(profiloViewModel.getItinerarioList(),profiloViewModel.getSegnalazioneList()));

        view.findViewById(R.id.buttonRisposteSegnalazioni).setOnClickListener(view1 -> {
            Log.d(TAG, CLASSNAME + "bottone risposte segnalazioni cliccato");
            if(profiloViewModel.getSegnalazioneList().isEmpty()) {
                Toast.makeText(getContext(), "non sono presenti segnalazioni", Toast.LENGTH_SHORT).show();
                return;
            }
            NavDirections navDirections = ProfiloFragmentDirections.actionNavigationProfiloToSegnalazioneEffettuateFragment();
            Navigation.findNavController(view1).navigate(navDirections);
        });
        return view;
    }
}