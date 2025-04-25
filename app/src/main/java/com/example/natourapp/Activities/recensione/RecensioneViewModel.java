package com.example.natourapp.Activities.recensione;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Recensione;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaRecensione.RichiestaRecensione;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RecensioneViewModel extends ViewModel {

    private Recensione recensione;

    public void createRecensione(String titolo, String descrizione, float valutazione, String username,int itinerarioId) {
        recensione = new Recensione();
        recensione.setTitolo_recensione(titolo);
        recensione.setDescrizione(descrizione);
        recensione.setValutazione(valutazione);
        recensione.setUtente_entity(new Utente(username));
        recensione.setItinerario_entity(new Itinerario(itinerarioId));
    }

    public String sendRecensione() {
        String response = "";
        Richiesta<Recensione> recensioneRichiesta = new RichiestaRecensione();
        Future<String> recensioneFuture = Executors.newSingleThreadExecutor()
                .submit(() -> recensioneRichiesta.add(recensione));
        try {
            response = recensioneFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
