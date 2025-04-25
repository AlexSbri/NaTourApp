package com.example.natourapp.services.http.richiestaItinerarioStat;

import android.util.Log;

import com.example.natourapp.entities.ItinerarioStat;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RichiestaItinerarioStat implements RichiestaItinerarioStatInterface {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RichiestaItinerarioStat:";

    @Override
    public ItinerarioStat retrieveItinerarioStatByItinerarioId(int itinerarioId) throws IOException {
        URL url = new URL(HOST + "stat_itinerario/get/itinerario?itinerario_id="+itinerarioId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        ItinerarioStat itinerarioStat = deserializeItinerarioStat(reader);
        Log.d(TAG,CLASSNAME + "retrieve statistiche itinerario tramite itinerario id " + "itinerarioStatId: " + itinerarioStat.getItinerario_id());
        return itinerarioStat;
    }
    @Override
    public ItinerarioStat deserializeItinerarioStat(Reader reader) {
        return new Gson().fromJson(reader,ItinerarioStat.class);
    }
    @Override
    public List<ItinerarioStat> retrieve() throws IOException {
        URL url = new URL(HOST + "stat_itinerario/get_view");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<ItinerarioStat> itinerarioStatList = deserializeObjectsToList(reader);
        Log.d(TAG,CLASSNAME + "retrieve statistiche itinerari " + "numero di statistiche itinerario: " + itinerarioStatList.size());
        return itinerarioStatList;
    }

    @Override
    public List<ItinerarioStat> deserializeObjectsToList(Reader reader) {
        Gson gson = new Gson();
        ItinerarioStat[] statArray = gson.fromJson(reader,ItinerarioStat[].class);
        return new ArrayList<>(Arrays.asList(statArray));
    }
    @Override
    public Long count() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public String add(ItinerarioStat item) throws IOException ,UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
