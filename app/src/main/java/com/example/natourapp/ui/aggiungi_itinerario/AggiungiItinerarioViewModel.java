package com.example.natourapp.ui.aggiungi_itinerario;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AggiungiItinerarioViewModel extends ViewModel {
    private Richiesta<Itinerario> richiestaItinerario;
    private final String[] valuesNumberPicker = new String[]{
            "1","2","3","4","5","6","7","8","9","10"
    };

    private Itinerario itinerario;
    private Double lat,lng;

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void createItinerario(String nomeItinerario, String descrizione, String difficolta,
                                 boolean serviziDisabili, Integer durata, String puntoDiPartenza,
                                 String tracciato, String username) {
        itinerario = new Itinerario();
        itinerario.setNome_itinerario(nomeItinerario);
        itinerario.setDescrizione(descrizione);
        itinerario.setDifficolta(difficolta);
        itinerario.setServizi_disabili(serviziDisabili);
        itinerario.setDurata(durata);
        itinerario.setPunto_di_partenza(puntoDiPartenza);
        itinerario.setTracciato(tracciato);
        itinerario.setUtente_entity(new Utente(username));
    }

    public String addItinerario() {
        richiestaItinerario = new RichiestaItinerario();
        Future<String> respFuture = Executors.newSingleThreadExecutor().submit(() -> richiestaItinerario.add(itinerario));
        String response = "";
        try {
            response = respFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public String[] getValuesNumberPicker() {
        return valuesNumberPicker;
    }

    private Utente utente;

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Context context) {
        if(this.utente == null)
            this.utente = new Utente(Cognito.SharedPrefApp.getInstance().getUsername(context));
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}