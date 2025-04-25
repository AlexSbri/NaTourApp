package com.example.natourapp.services.http.richiestaSegnalazione;

import android.util.Log;

import com.example.natourapp.entities.Segnalazione;
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

public class RichiestaSegnalazione implements RichiestaSegnalazioneInterface{
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaSegnalazione:";

    @Override
    public String rispondiSegnalazioneById(String riposta_segnalazione, int segnalazione_id, String user_id) throws IOException {
        URL url = new URL(HOST + "/segnalazione/rispondi/segnalazione?" +
                "risposta_segnalazione="+riposta_segnalazione +
                "&segnalazione_id="+segnalazione_id+
                "&username="+user_id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) return "Errore nel server";
        if(connection.getResponseCode() >= 400) return "Errore nella richiesta";
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) return "Risorsa non presente";
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG ,CLASSNAME+ "invia risposta segnalazione"+" risposta: " + response);
        return response;
    }

    @Override
    public String checkRispondiSegnalazioneParameters(String riposta_segnalazione,int segnalazione_id,String user_id){
        Log.d(TAG,"controllo parametri");
        if(segnalazione_id <= 0) return "Inserire un id segnalazione corretta";
        if(riposta_segnalazione.isEmpty() && user_id.isEmpty()) return "Inserire risposta e utente a cui vuoi rispondere";
        if(riposta_segnalazione.isEmpty()) return "Inserire risposta";
        if(user_id.isEmpty()) return "Inserire utente a cui vuoi rispondere";
        return "Dati inseriti correttamente";
    }

    @Override
    public Long countSegnalazioneItinerarioById(int itinerarioId) throws IOException {
        URL url = new URL(HOST + "/segnalazione/get?itinerario_id="+itinerarioId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Long numeroSegnalazioniItinerario = Long.parseLong(reader.readLine());
        Log.d(TAG ,CLASSNAME + "count segnalazioni itinerario"+" numero segnalazioni: " + numeroSegnalazioniItinerario);
        return numeroSegnalazioniItinerario;
    }

    @Override
    public Segnalazione deserializeSegnalazione(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader,Segnalazione.class);
    }

    @Override
    public List<Segnalazione> retrieveSegnalazioniByItinerarioId(int itinerarioId) throws IOException {
        URL url = new URL(HOST + "/segnalazione/get?itinerario_id="+itinerarioId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Segnalazione> segnalazioneList = deserializeObjectsToList(reader);
        Log.d(TAG ,CLASSNAME+ "get tramite itinerarioId "+"numero di segnalazioni: " + segnalazioneList.size());
        return segnalazioneList;
    }

    @Override
    public Segnalazione retrieveSegnalazioneById(int segnalazioneId) throws IOException {
        URL url = new URL(HOST + "/segnalazione/get/segnalazione_id?segnalazione_id="+segnalazioneId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Segnalazione segnalazione = deserializeSegnalazione(reader);
        Log.d(TAG,CLASSNAME + "get tramite segnalazioneId"+" segnalazioneId: " + segnalazione.getSegnalazione_id());
        return segnalazione;
    }

    @Override
    public List<Segnalazione> retrieveSegnalazioniByUtenteUsername(String username) throws IOException {
        URL url = new URL(HOST + "/segnalazione/get/utente?username="+username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Segnalazione> segnalazioneList = deserializeObjectsToList(reader);
        Log.d(TAG ,CLASSNAME+ "retrieve segnalazioni effettuate dall'utente"+" numero segnalazioni: " + segnalazioneList.size());
        return segnalazioneList;
    }

    @Override
    public List<Segnalazione> retrieve() throws IOException {
        URL url = new URL(HOST + "/segnalazione/retrieve_segnalazioni");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Segnalazione> segnalazioneList = deserializeObjectsToList(reader);
        Log.d(TAG ,CLASSNAME+ "retrieve segnalazioni"+" numero segnalazioni: " + segnalazioneList.size());
        return segnalazioneList;
    }

    @Override
    public Long count() throws IOException {
        URL url = new URL(HOST + "/segnalazione/count");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Long numeroSegnalazioni = Long.parseLong(reader.readLine());
        Log.d(TAG ,CLASSNAME+ "count segnalazioni"+" numero segnalazioni: " + numeroSegnalazioni);
        return numeroSegnalazioni;
    }

    @Override
    public String add(Segnalazione segnalazione) throws IOException {
        Gson gson = new Gson();
        String gsonContent = gson.toJson(segnalazione);
        Log.d("gsonContent",gsonContent);
        URL url = new URL(HOST + "segnalazione/segnala");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream(),true);
        printWriter.println(gsonContent);
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG ,CLASSNAME+ "add segnalazione "+message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG ,CLASSNAME+ "add segnalazione "+message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG ,CLASSNAME+ "add segnalazione "+message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "add segnalazione"+" risposta: " + response);
        return response;
    }

    @Override
    public List<Segnalazione> deserializeObjectsToList(Reader reader) {
        Gson gson = new Gson();
        Segnalazione[] segnalazioneArray = gson.fromJson(reader,Segnalazione[].class);
        return new ArrayList<>(Arrays.asList(segnalazioneArray));
    }
}
