package com.example.natourapp.services.http.richiestaPlaylist;

import android.net.Uri;
import android.util.Log;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Playlist;
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

public class RichiestaPlaylist implements RichiestaPlaylistInterface{
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaPlaylist:";

    @Override
    public boolean checkDeleteItinerarioFromPlaylistparameters(int itinerario_id,int playlist_id){
        Log.d(TAG, CLASSNAME + "controllo parametri");
        return itinerario_id > 0 && playlist_id > 0;
    }

    @Override
    public List<Itinerario> getContentPlaylist(int playlist_id) throws IOException {
        URL url = new URL(HOST + "playlist/get/content?playlist_id="+playlist_id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Itinerario> itinerarioList = deserializeItinerarioListForPlaylist(reader);
        Log.d(TAG,CLASSNAME + "retrieve contenuto playlist " + "numero itinerari contenuti nella playlist: " + itinerarioList.size());
        return itinerarioList;
    }

    @Override
    public String deletePlaylist(int playlist_id) throws IOException {
        URL url = new URL(HOST + "playlist/delete?playlist_id="+playlist_id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG,CLASSNAME + "delete playlist "+ message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "delete playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "delete playlist " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "delete playlist "+ "risposta: " + response);
        return response;
    }

    @Override
    public String addToPlaylist(int itinerario_id,int playlist_id) throws IOException {
        URL url = new URL(HOST + "playlist/add");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        String query = queryBuilder(itinerario_id,playlist_id);
        writePostParameters(connection.getOutputStream(),query);
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG, CLASSNAME + "add alla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG, CLASSNAME + "add alla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG, CLASSNAME + "add alla playlist " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "add alla playlist " + "risposta: " + response);
        return reader.readLine();
    }

    @Override
    public String deleteItinerarioByIdFromPlaylist(int itinerarioId, int playlistId) throws IOException {
        URL url = new URL(HOST + "playlist/delete/itinerario?itinerario="+itinerarioId+"&playlist_id="+playlistId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG,CLASSNAME + "delete dalla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "delete dalla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "delete dalla playlist " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "delete dalla playlist "+"risposta: " + response);
        return response;
    }

    @Override
    public List<Playlist> getPlaylistByUtenteUsername(String username) throws IOException {
        URL url = new URL(HOST + "playlist/get/utente?username="+username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<Playlist> playlistsList = deserializeObjectsToList(reader);
        Log.d(TAG,CLASSNAME + "retrieve playlist dell'utente " +"numero playlist: " + playlistsList.size());
        return playlistsList;
    }

    @Override
    public List<Itinerario> deserializeItinerarioListForPlaylist(Reader reader) {
        Gson gson = new Gson();
        Itinerario[] itinerarioArray = gson.fromJson(reader,Itinerario[].class);
        return new ArrayList<>(Arrays.asList(itinerarioArray));
    }

    @Override
    public List<Playlist> retrieve() throws IOException, UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Long count() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }


    @Override
    public String add(Playlist playlist) throws IOException {
        Gson gson = new Gson();
        String gsonContent = gson.toJson(playlist);
        Log.d("gson content",gsonContent);
        URL url = new URL(HOST + "playlist/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(),true);
        writer.println(gsonContent);
        if(connection.getResponseCode() >= 500) {
            String message = "Errore nel server";
            Log.e(TAG,CLASSNAME + "add alla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 400) {
            String message = "Errore nella richiesta";
            Log.e(TAG,CLASSNAME + "add alla playlist " + message);
            return message;
        }
        if(connection.getResponseCode() >= 300 && connection.getResponseCode() != 400) {
            String message = "Risorsa non presente";
            Log.e(TAG,CLASSNAME + "add alla playlist " + message);
            return message;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Log.d(TAG,CLASSNAME + "add alla playlist " + "risposta: " + response);
        return response;
    }

    @Override
    public List<Playlist> deserializeObjectsToList(Reader reader) {
        Gson gson = new Gson();
        Playlist[] playlists = gson.fromJson(reader,Playlist[].class);
        return new ArrayList<>(Arrays.asList(playlists));
    }

    private String queryBuilder(int itinerario_id,int playlist_id){
        Uri.Builder uriBuilder = new Uri.Builder()
                .appendQueryParameter("itinerario", String.valueOf(itinerario_id))
                .appendQueryParameter("playlist_id", String.valueOf(playlist_id));
        return uriBuilder.build().getEncodedQuery();
    }
    private void writePostParameters(OutputStream outputStream, String query) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        try {
            writer.write(query);
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
