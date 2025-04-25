package com.example.natourapp.Activities.segnalazione;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Segnalazione;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazione;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazioneInterface;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SegnalazioneViewModel extends ViewModel {
    private Segnalazione segnalazione;
    private Itinerario itinerario;
    private Utente utenteSegnalazione;

    public void createSegnalazione(String titolo, String descrizione, String motivazione,
                                   String usernameItinerario,String utenteSegnalazione, int itinerarioId) {
        itinerario = new Itinerario(itinerarioId);
        Utente utenteitinerario = new Utente(usernameItinerario);
        this.utenteSegnalazione = new Utente(utenteSegnalazione);
        segnalazione = new Segnalazione();
        itinerario.setUtente_entity(utenteitinerario);
        segnalazione.setUtente_entity(this.utenteSegnalazione);
        segnalazione.setDescrizione_segnalazione(descrizione);
        segnalazione.setTitolo_segnalazione(titolo);
        segnalazione.setMotivazione_segnalazione(motivazione);
        segnalazione.setItinerario_entity(itinerario);
    }

    public String sendResponseSegnalazione(String rispostaSegnalazione,int segnalazioneId,String username) {
        RichiestaSegnalazioneInterface richiestaSegnalazione = new RichiestaSegnalazione();
        String responseRequest = richiestaSegnalazione.checkRispondiSegnalazioneParameters(rispostaSegnalazione,segnalazioneId,username);
        if(responseRequest.equals("Dati inseriti correttamente")) {
            Future<String> richiestaSegnalazioneFuture = Executors.newSingleThreadExecutor()
                    .submit(() -> richiestaSegnalazione.rispondiSegnalazioneById(rispostaSegnalazione,segnalazioneId,username));
            try {
                responseRequest = richiestaSegnalazioneFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return responseRequest;
    }

    public String sendSegnalazione() {
        String response = "";
        Richiesta<Segnalazione> segnalazioneRichiesta = new RichiestaSegnalazione();
        Future<String> responseFuture = Executors.newSingleThreadExecutor()
                .submit(() ->segnalazioneRichiesta.add(segnalazione));
        try {
            response = responseFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void retrieveSegnalazioneById(int segnalazioneId) {
        RichiestaSegnalazioneInterface richiestaSegnalazione = new RichiestaSegnalazione();
        Future<Segnalazione> segnalazioneFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaSegnalazione.retrieveSegnalazioneById(segnalazioneId));
        try {
            segnalazione = segnalazioneFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Segnalazione getSegnalazione() {
        return segnalazione;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public Utente getUtenteSegnalazione() {
        return utenteSegnalazione;
    }
}
