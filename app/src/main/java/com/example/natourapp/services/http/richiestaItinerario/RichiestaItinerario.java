package com.example.natourapp.services.http.richiestaItinerario;

import android.net.Uri;
import android.util.Log;

import com.example.natourapp.entities.Itinerario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RichiestaItinerario implements RichiestaItinerarioInterface {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaItinerario:";
    private enum Difficolta {
        TURISTICO("Turistico"),
        ESPERTO("Esperti"),
        ESPERTI_ATTREZZATI("Esperti attrezzati"),
        AMBIENTE_INNEVATO("Ambiente innevato"),
        ESCURSIONISTICO("Escursionistico");

        private final String value;

        Difficolta(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean contains(String value) {
            return Arrays.stream(values()).anyMatch(v -> v.getValue().equals(value));
        }
    }

    @Override
    public List<Itinerario> retrieve() throws IOException {
        URL url = new URL(HOST + "itinerario/home");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Itinerario> itinerarioList = deserializeObjectsToList(reader);
        Log.d(TAG,CLASSNAME + "numero itinerari: " + itinerarioList.size());
        return itinerarioList;
    }

    @Override
    public Long count() throws IOException {
        URL url = new URL(HOST + "itinerario/count");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Long numeroItinerari = Long.parseLong(reader.readLine());
        Log.d(TAG,CLASSNAME + "numero itinerari: " + numeroItinerari);
        return numeroItinerari;
    }

    @Override
    public String add(Itinerario itinerario) throws IOException {
        Gson gson = new Gson();
        String gsonContent = gson.toJson(itinerario);
        Log.d("gson content",gsonContent);
        URL url = new URL(HOST + "itinerario/add");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(),true);
        writer.println(gsonContent);
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG,CLASSNAME + "add itinerario " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "add itinerario " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "add itinerario " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "risposta: " + response);
        return response;
    }

    @Override
    public List<Itinerario> deserializeObjectsToList(Reader reader) {
        Gson gson = new Gson();
        Itinerario[] itinerarioArray = gson.fromJson(reader,Itinerario[].class);
        return new ArrayList<>(Arrays.asList(itinerarioArray));
    }

    @Override
    public Itinerario deserializeItinerario(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader,Itinerario.class);
    }

    @Override
    public List<Itinerario> searchItinerario(String nomeItinerario, String difficolta, boolean serviziDisabili, Integer durata) throws IOException {
        URL url = new URL(HOST + "itinerario/search");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        String query = queryBuilder(nomeItinerario,difficolta,serviziDisabili,durata);
        writePostParameters(connection.getOutputStream(),query);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Itinerario> itinerarioList = deserializeObjectsToList(reader);
        Log.d(TAG,CLASSNAME + "numero itinerari risultato ricerca: " + itinerarioList.size());
        return itinerarioList;
    }

    @Override
    public Itinerario getItinerarioById(int itinerarioId) throws IOException {
        URL url = new URL(HOST + "itinerario/get?itinerario_id="+itinerarioId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Itinerario itinerario = deserializeItinerario(reader);
        Log.d(TAG,CLASSNAME + "retrieve itineario tramite itinerarioId " +"itinerarioId: " + itinerario.getItinerario_id());
        return itinerario;
    }

    @Override
    public List<Itinerario> getItinerariByUtenteUsername(String username) throws IOException {
        URL url = new URL(HOST + "itinerario/get/utente?username="+username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Itinerario> itinerarioList = deserializeObjectsToList(reader);
        Log.d(TAG,CLASSNAME + "retrieve itinerari tramite username utente " +"numero itinerari: " + itinerarioList.size());
        return itinerarioList;
    }

    private String queryBuilder(String nomeItinerario,String difficolta,Boolean serviziDisabili,Integer durata) {
        Uri.Builder uriBuilder = new Uri.Builder()
                .appendQueryParameter("nome_itinerario",nomeItinerario)
                .appendQueryParameter("difficolta",difficolta)
                .appendQueryParameter("servizi_disabili",serviziDisabili.toString())
                .appendQueryParameter("durata",durata.toString());
        return uriBuilder.build().getEncodedQuery();
    }

    private void writePostParameters(OutputStream outputStream, String query) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,StandardCharsets.UTF_8));
        try {
            writer.write(query);
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkItinerarioSearchParameters(String difficolta, Integer durata, Boolean servizi_disabili) {
        Log.d(TAG,CLASSNAME + "controllo parametri ricerca");
        return Difficolta.contains(difficolta) && durata != null && durata > 0 && durata <= 10 && servizi_disabili != null;
    }
}
