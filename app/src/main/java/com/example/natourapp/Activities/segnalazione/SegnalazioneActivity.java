package com.example.natourapp.Activities.segnalazione;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;

public class SegnalazioneActivity extends AppCompatActivity {

    private EditText editTextTitoloSegnalazione;
    private EditText editTextDescrizioneSegnalazione;
    private RadioButton radioButtonInformazioniNonAggiornate;
    private RadioButton radioButtonInformazioniInesatte;
    private RadioButton radioButtonAltro;
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "SegnalazioneActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalazione);
        SegnalazioneViewModel segnalazioneViewModel = new ViewModelProvider(this)
                .get(SegnalazioneViewModel.class);

        editTextTitoloSegnalazione = findViewById(R.id.TitoloSegnalazioneEditText);
        editTextDescrizioneSegnalazione = findViewById(R.id.DescrizioneSegnalazioneEditText);
        radioButtonInformazioniNonAggiornate = findViewById(R.id.InfoNonAggiornateRadioButton);
        radioButtonInformazioniInesatte = findViewById(R.id.InfoInesatteRadioButton);
        radioButtonAltro = findViewById(R.id.AltroRadioButton);
        Button segnala = findViewById(R.id.InviaSegnalazioneButton);
        Intent intent = getIntent();
        int itinerario_id = intent.getIntExtra("itinerario_id",-1);
        String creatoreItinerario = intent.getStringExtra("itinerario_username");
        String username = Cognito.SharedPrefApp.getInstance().getUsername(this);
        segnala.setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone segnala cliccato");
            setCustomField();
            if(checkEditText()) {
                String titoloSegnalazione = editTextTitoloSegnalazione.getText().toString();
                String motivazioneSegnalazione = getTextRadioButtonSelected();
                String descrizioneSegnalazione = editTextDescrizioneSegnalazione.getText().toString();
                segnalazioneViewModel.createSegnalazione(titoloSegnalazione,descrizioneSegnalazione,
                        motivazioneSegnalazione,creatoreItinerario,username,itinerario_id);
                String response = segnalazioneViewModel.sendSegnalazione();
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private String getTextRadioButtonSelected(){
        if(radioButtonInformazioniNonAggiornate.isChecked())return radioButtonInformazioniNonAggiornate.getText().toString();
        if(radioButtonInformazioniInesatte.isChecked())return radioButtonInformazioniInesatte.getText().toString();
        return radioButtonAltro.getText().toString();
    }

    private boolean checkEditText(){
        boolean check = true;
        if(TextUtils.isEmpty(editTextTitoloSegnalazione.getText().toString())){
            editTextTitoloSegnalazione.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(this, "Inserisci il titolo della segnalazione ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if(TextUtils.isEmpty(editTextDescrizioneSegnalazione.getText().toString())){
            editTextDescrizioneSegnalazione.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(this, "Inserisci la descrizione ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    private void setCustomField(){
        editTextTitoloSegnalazione.setBackgroundResource(R.drawable.text_field_custom);
        editTextDescrizioneSegnalazione.setBackgroundResource(R.drawable.text_field_custom);
    }
}