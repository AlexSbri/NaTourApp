package com.example.natourapp.Activities.statistiche_admin;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Recensione;
import com.example.natourapp.entities.Segnalazione;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;
import com.example.natourapp.services.http.richiestaMetrics.RichiestaMetrics;
import com.example.natourapp.services.http.richiestaRecensione.RichiestaRecensione;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazione;
import com.example.natourapp.services.http.richiestaUtente.RichiesteUtente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatisticheAdminViewModel extends ViewModel {
    private Long utentiIscritti;
    private Long numeroRecensioni;
    private Long numeroItineari;
    private Long numeroSegnalazioni;
    private int numeroRichieste;
    private int numeroRicerche;


    public void loadStatisticheApplication() {
        Richiesta<Utente> utenteRichiesta = new RichiesteUtente();
        Richiesta<Itinerario> itinerarioRichiesta = new RichiestaItinerario();
        Richiesta<Recensione> recensioneRichiesta = new RichiestaRecensione();
        Richiesta<Segnalazione> segnalazioneRichiesta = new RichiestaSegnalazione();
        RichiestaMetrics richiestaMetrics = new RichiestaMetrics();
        Future<Long> utentiIscrittiFuture = Executors.newSingleThreadExecutor()
                .submit(utenteRichiesta::count);
        Future<Long> numeroRecensioniFuture = Executors.newSingleThreadExecutor()
                .submit(recensioneRichiesta::count);
        Future<Long> numeroItinerariFuture = Executors.newSingleThreadExecutor()
                .submit(itinerarioRichiesta::count);
        Future<Long> numeroSegnalazioniFuture = Executors.newSingleThreadExecutor()
                .submit(segnalazioneRichiesta::count);
        Future<String> jsonNumeroRichiesteFuture = Executors.newSingleThreadExecutor()
                .submit(richiestaMetrics::retrieveNumeroRichieste);
        Future<String> jsonNumeroRicercheFuture = Executors.newSingleThreadExecutor()
                .submit(richiestaMetrics::retrieveNumeroRicerche);
        String jsonContentNumeroRichieste = "";
        String jsonContentNumeroRicerche = "";
        try {
            utentiIscritti = utentiIscrittiFuture.get();
            numeroItineari = numeroItinerariFuture.get();
            numeroRecensioni = numeroRecensioniFuture.get();
            numeroSegnalazioni = numeroSegnalazioniFuture.get();
            jsonContentNumeroRichieste = jsonNumeroRichiesteFuture.get();
            jsonContentNumeroRicerche = jsonNumeroRicercheFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        numeroRichieste = getNumeroRichiesteFromJson(jsonContentNumeroRichieste);
        numeroRicerche = getNumeroRicercheFromJson(jsonContentNumeroRicerche);
    }

    public Long getNumeroItineari() {
        return numeroItineari;
    }

    public Long getNumeroRecensioni() {
        return numeroRecensioni;
    }

    public Long getUtentiIscritti() {
        return utentiIscritti;
    }

    public Long getNumeroSegnalazioni() {
        return numeroSegnalazioni;
    }
    public int getNumeroRichieste() {
        return numeroRichieste;
    }
    public int getNumeroRicerche() {
        return numeroRicerche;
    }
    private int getNumeroRichiesteFromJson(String json)  {
        try {
            JSONObject jsonObject =new JSONObject(json.replace("\"", ""));
            JSONObject jsonCountObject = new JSONObject(jsonObject.getJSONArray("measurements").get(0).toString());
            return (int) jsonCountObject.get("value");
        } catch (JSONException e) {
            return 0;
        }
    }

    private int getNumeroRicercheFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json.replace("\"", ""));
            JSONObject jsonCountObject = new JSONObject(jsonObject.getJSONArray("measurements").get(0).toString());
            return (int) jsonCountObject.get("value");
        } catch (JSONException e) {
            return 0;
        }
    }


}
