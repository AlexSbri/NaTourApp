package com.example.natourapp.Activities.segnalazione.risposta_segnalazione;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.Activities.segnalazione.SegnalazioneViewModel;
import com.example.natourapp.R;

public class RispostaSegnalazioneActivity extends AppCompatActivity {
    private EditText editTextRispostaSegnalazione;
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RispostaSegnalazioneActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SegnalazioneViewModel segnalazioneViewModel = new ViewModelProvider(this).get(SegnalazioneViewModel.class);
        setContentView(R.layout.activity_risposta_segnalazione);
        Intent intent = getIntent();
        String username = intent.getStringExtra("user_id");
        int segnalazione_id = intent.getIntExtra("segnalazione_id",-1);
        editTextRispostaSegnalazione = findViewById(R.id.editTextRispostaSegnalazione);

        findViewById(R.id.buttonRispondiSegnalazione).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone rispondi cliccato");
            setCustomField();
            if(checkEditText()) {
                String rispostaSegnalazione = editTextRispostaSegnalazione.getText().toString();
                String response = segnalazioneViewModel.sendResponseSegnalazione(rispostaSegnalazione,segnalazione_id,username);
                Toast.makeText(RispostaSegnalazioneActivity.this, response, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(RispostaSegnalazioneActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private boolean checkEditText(){
        boolean check = true;
        if(TextUtils.isEmpty(editTextRispostaSegnalazione.getText().toString())){
            editTextRispostaSegnalazione.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(this, "Inserisci il titolo della recensioni ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    private void setCustomField(){
        editTextRispostaSegnalazione.setBackgroundResource(R.drawable.text_field_custom);
    }
}