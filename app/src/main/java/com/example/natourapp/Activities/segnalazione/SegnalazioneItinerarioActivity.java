package com.example.natourapp.Activities.segnalazione;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.Activities.segnalazione.risposta_segnalazione.RispostaSegnalazioneActivity;
import com.example.natourapp.R;

public class SegnalazioneItinerarioActivity extends AppCompatActivity {

    private SegnalazioneViewModel segnalazioneViewModel;
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "SegnalazioneItinerarioActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        segnalazioneViewModel = new ViewModelProvider(this)
                .get(SegnalazioneViewModel.class);
        setContentView(R.layout.activity_segnalazione_itinerario);
        Intent intent = getIntent();
        int segnalazione_id = intent.getIntExtra("segnalazione_id",-1);
        segnalazioneViewModel.retrieveSegnalazioneById(segnalazione_id);
        TextView textViewDescrizioneSegnalazione = findViewById(R.id.textViewDescrizioneSegnalazione);
        TextView textViewTitoloSegnalazione = findViewById(R.id.textViewTitoloSegnalazione);
        textViewTitoloSegnalazione.setText(segnalazioneViewModel.getSegnalazione().getTitolo_segnalazione());
        textViewDescrizioneSegnalazione.setText(segnalazioneViewModel.getSegnalazione().getTitolo_segnalazione());

        findViewById(R.id.buttonRispondiSegnalazioneSegnalazioneItinerario).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME +  "bottone rispondi cliccato");
            Intent intent1 = new Intent(SegnalazioneItinerarioActivity.this, RispostaSegnalazioneActivity.class);
            intent1.putExtra("user_id", segnalazioneViewModel.getSegnalazione().getUtente_entity().getUsername());
            intent1.putExtra("segnalazione_id",segnalazione_id);
            startActivity(intent1);
        });


    }
}