package com.example.natourapp.Activities.recensione;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.Activities.mostra_itinerario.MostraItinerarioActivity;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;

public class RecensioneActivity extends AppCompatActivity {
    private EditText titoloRecensioneEditText;
    private EditText contenutoRecensioneEditText;
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RecensioneActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecensioneViewModel recensioneViewModel = new ViewModelProvider(this).get(RecensioneViewModel.class);
        setContentView(R.layout.activity_recensione);
        Intent intent = getIntent();
        String titoloItinerario = intent.getStringExtra("titolo_itinerario");
        int itinerarioId = intent.getIntExtra("itinerario_id",-1);
        setTitle(titoloItinerario);
        String autoreRecensione = Cognito.SharedPrefApp.getInstance().getUsername(this);
        titoloRecensioneEditText = findViewById(R.id.titoloRecensioneEditText);
        contenutoRecensioneEditText = findViewById(R.id.descrizioneRecensioneEditText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        findViewById(R.id.pubblicaRecensioneButton).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone pubblica recensione cliccato");
            setCustomField();
            if(checkEditText()) {
                String titoloRecensione = titoloRecensioneEditText.getText().toString();
                String descrizioneRecensione = contenutoRecensioneEditText.getText().toString();
                recensioneViewModel.createRecensione(titoloRecensione,descrizioneRecensione,ratingBar.getRating(),autoreRecensione,itinerarioId);
                String response = recensioneViewModel.sendRecensione();
                Toast.makeText(RecensioneActivity.this, response, Toast.LENGTH_SHORT).show();
                Intent navigate = new Intent(getApplicationContext(), MostraItinerarioActivity.class);
                navigate.putExtra("itinerario_id",itinerarioId);
                navigate.putExtra("titolo_itinerario",titoloItinerario);
                startActivity(navigate);
            } else {
                Toast.makeText(RecensioneActivity.this, "il titolo e la descrizione non possono essere vuoti",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkEditText(){
        boolean check = true;
        if(TextUtils.isEmpty(titoloRecensioneEditText.getText().toString())){
            titoloRecensioneEditText.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(this, "Inserisci il titolo della recensioni ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if(TextUtils.isEmpty(contenutoRecensioneEditText.getText().toString())){
            contenutoRecensioneEditText.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(this, "Inserisci la descrizione ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    private void setCustomField(){
        titoloRecensioneEditText.setBackgroundResource(R.drawable.text_field_custom);
        contenutoRecensioneEditText.setBackgroundResource(R.drawable.text_field_custom);
    }
}