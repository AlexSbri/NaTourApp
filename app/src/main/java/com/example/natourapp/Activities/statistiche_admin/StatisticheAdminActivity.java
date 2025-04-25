package com.example.natourapp.Activities.statistiche_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.R;

import java.util.Locale;

public class StatisticheAdminActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "StatisticheAdminActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatisticheAdminViewModel statisticheAdminViewModel = new ViewModelProvider(this).get(StatisticheAdminViewModel.class);
        statisticheAdminViewModel.loadStatisticheApplication();
        setContentView(R.layout.activity_statistiche_admin);
        TextView textViewNumeroRichiesteEffettuate = findViewById(R.id.numeroRichiesteEffettuate);
        TextView textViewUtentiIscritti = findViewById(R.id.numeroUtentiIscritti);
        TextView textViewNumeroRecensioni = findViewById(R.id.numeroRecensioni);
        TextView textViewNumeroRicerche = findViewById(R.id.numeroRicercheEffettuate);
        TextView textViewItinerari = findViewById(R.id.numeroItinerari);
        TextView textViewSegnalazioni = findViewById(R.id.numeroSegnalazioni);
        Long utentiIscritti = statisticheAdminViewModel.getUtentiIscritti();
        Long recensioni = statisticheAdminViewModel.getNumeroRecensioni();
        Long itinerari = statisticheAdminViewModel.getNumeroItineari();
        Long segnalazioni = statisticheAdminViewModel.getNumeroSegnalazioni();
        int richiesteEffettuate = statisticheAdminViewModel.getNumeroRichieste();
        int ricercheEffettuate = statisticheAdminViewModel.getNumeroRicerche();
        textViewUtentiIscritti.setText(String.format(Locale.ITALY,"%d",utentiIscritti));
        textViewNumeroRecensioni.setText(String.format(Locale.ITALY,"%d",recensioni));
        textViewItinerari.setText(String.format(Locale.ITALY,"%d",itinerari));
        textViewSegnalazioni.setText(String.format(Locale.ITALY,"%d",segnalazioni));
        textViewNumeroRichiesteEffettuate.setText(String.format(Locale.ITALY,"%d",richiesteEffettuate));
        textViewNumeroRicerche.setText(String.format(Locale.ITALY,"%d",ricercheEffettuate));
        Button buttonAggiornaStatistiche= findViewById(R.id.aggiornaStatisticheButton);
        buttonAggiornaStatistiche.setOnClickListener(view
                -> {
            statisticheAdminViewModel.loadStatisticheApplication();
            Log.d(TAG,CLASSNAME + "bottone aggiorna cliccato");
        });
    }
}