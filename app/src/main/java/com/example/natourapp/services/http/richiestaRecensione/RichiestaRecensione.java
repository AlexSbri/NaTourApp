package com.example.natourapp.services.http.richiestaRecensione;

import android.util.Log;

import com.example.natourapp.entities.Recensione;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RichiestaRecensione implements RichiestaRecensioneInterface{
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaRecensione:";

    @Override
    public List<Recensione> retrieve() throws IOException,UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long count() throws IOException {
        URL url = new URL(HOST + "/recensione/count");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        Long numeroRecensioni = Long.parseLong(reader.readLine());
        Log.d(TAG,CLASSNAME + "count recensioni " + "numero recensioni: " + numeroRecensioni);
        return numeroRecensioni;
    }

    @Override
    public String add(Recensione recensione) throws IOException {
        Gson gson = new Gson();
        String gsonContent = gson.toJson(recensione);
        URL url = new URL(HOST + "recensione/add");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(),true);
        writer.println(gsonContent);
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG,CLASSNAME + "add recensione " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "add recensione " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "add recensione " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG, CLASSNAME + "add recensione " +"risposta: " + response);
        return response;
    }

    @Override
    public List<Recensione> deserializeObjectsToList(Reader reader) {
        Gson gson =new Gson();
        Recensione[] recensiones = gson.fromJson(reader,Recensione[].class);
        return new ArrayList<>(Arrays.asList(recensiones));
    }

    @Override
    public List<Recensione> retrieveRecensioniByItinerarioId(int itinerarioId) throws IOException {
        URL url = new URL(HOST + "/recensione/get/recensioni?itinerario_id=" + itinerarioId);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        List<Recensione> recensioneList = deserializeObjectsToList(reader);
        Log.d(TAG, CLASSNAME + "retrieve recensioni tramite itinerario id " + "numero recensioni: " + recensioneList.size());
        return recensioneList;
    }
}
