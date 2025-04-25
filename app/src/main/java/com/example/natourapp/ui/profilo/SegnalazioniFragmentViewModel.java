package com.example.natourapp.ui.profilo;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Segnalazione;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazione;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazioneInterface;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SegnalazioniFragmentViewModel extends ViewModel {
    private List<Segnalazione> segnalazioneList ;

    public void loadSegnalazioni(int itinerarioId){
        RichiestaSegnalazioneInterface richiestaSegnalazione = new RichiestaSegnalazione();
        Future<List<Segnalazione>> futureList = Executors.newSingleThreadExecutor().submit(() ->
                richiestaSegnalazione.retrieveSegnalazioniByItinerarioId(itinerarioId));
        try {
            segnalazioneList = futureList.get();
            segnalazioneList.removeIf(segnalazione -> segnalazione.getRisposta_segnalazione()!=null);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void loadSegnalazioni(String username) {
        RichiestaSegnalazioneInterface richiestaSegnalazione = new RichiestaSegnalazione();
        Future<List<Segnalazione>> futureList = Executors.newSingleThreadExecutor().submit(() ->
                richiestaSegnalazione.retrieveSegnalazioniByUtenteUsername(username));
        try {
            segnalazioneList = futureList.get();
            segnalazioneList.removeIf(segnalazione -> segnalazione.getRisposta_segnalazione() == null);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Segnalazione> getSegnalazioneList() {
        return segnalazioneList;
    }
}
