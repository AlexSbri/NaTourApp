package com.example.natourapp.services.http.richiestaUtente;

import android.util.Log;

import com.example.natourapp.entities.Utente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RichiesteUtente implements RichiestaUtenteInterface{
    private static final String TAG = "TEST";
    private static final String CLASSNAME ="RichiesteUtente:";

    @Override
    public List<Utente> retrieve() throws IOException, UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Long count() throws IOException {
        URL url = new URL(HOST + "/utente/count");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Long numeroUtenti = Long.parseLong(reader.readLine());
        Log.d(TAG ,CLASSNAME + " count utenti" + " numero utenti: " + numeroUtenti);
        return numeroUtenti;
    }

    @Override
    public String add(Utente utente) throws IOException {
        URL url = new URL(HOST + "/utente/registrati?username="+utente.getUsername());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG ,CLASSNAME + "richiesta registrazione "+message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "richiesta registrazione "+message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "richiesta registrazione "+message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "richiesta registrazione" + " risposta:" + response);
        return response;
    }

    @Override
    public List<Utente> deserializeObjectsToList(Reader reader) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
