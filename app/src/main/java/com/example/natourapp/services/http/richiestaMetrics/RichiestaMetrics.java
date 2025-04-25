package com.example.natourapp.services.http.richiestaMetrics;

import android.util.Log;

import com.example.natourapp.services.http.Richiesta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RichiestaMetrics {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaMetrics:";

    public String retrieveNumeroRichieste() throws IOException {

        URL url = new URL(Richiesta.HOST + "metric/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG ,CLASSNAME + "retrieve numero richieste " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG ,CLASSNAME + "retrieve numero richieste " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG ,CLASSNAME + "retrieve numero richieste " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "retrieve numero richieste " + "richiesta: " + response);
        return response;
    }

    public String retrieveNumeroRicerche() throws IOException {
        URL url = new URL(Richiesta.HOST + "metric/itinerario/search");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG, CLASSNAME + "retrieve numero ricerche " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG, CLASSNAME + "retrieve numero ricerche " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG, CLASSNAME + "retrieve numero ricerche " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String respone = reader.readLine();
        Log.d(TAG,CLASSNAME + "retrieve numero ricerche " +"risposta: " + respone);
        return respone;
    }

}
