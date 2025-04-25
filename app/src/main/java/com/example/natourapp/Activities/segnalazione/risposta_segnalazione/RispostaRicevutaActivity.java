package com.example.natourapp.Activities.segnalazione.risposta_segnalazione;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.Activities.segnalazione.SegnalazioneViewModel;
import com.example.natourapp.R;

public class RispostaRicevutaActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RispostaRicevutaActivity:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SegnalazioneViewModel segnalazioneViewModel = new ViewModelProvider(this).get(SegnalazioneViewModel.class);
        setContentView(R.layout.activity_risposta_ricevuta);
        Intent intent = getIntent();
        int segnalazione_id = intent.getIntExtra("segnalazione_id",-1);
        segnalazioneViewModel.retrieveSegnalazioneById(segnalazione_id);
        TextView textViewRispostaDellaSegnalazione = findViewById(R.id.textViewRispostaDellaSegnalazione);
        textViewRispostaDellaSegnalazione.setText(segnalazioneViewModel.getSegnalazione().getRisposta_segnalazione());
        findViewById(R.id.buttonOk).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone ok cliccato");
            Intent intent1 = new Intent(RispostaRicevutaActivity.this, MainActivity.class);
            startActivity(intent1);
        });


    }
}