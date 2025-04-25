package com.example.natourapp.ui.profilo;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Segnalazione;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerarioInterface;
import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazione;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProfiloViewModel extends ViewModel {
    private List<Itinerario> itinerarioList;
    private List<Segnalazione> segnalazioneList;

    public void loadItinerariUtenteAndSegnalazioni(String username){
        RichiestaItinerarioInterface richiestaItinerario = new RichiestaItinerario();
        Richiesta<Segnalazione> richiestaSegnalazione = new RichiestaSegnalazione();
        Future<List<Itinerario>> listFuture = Executors.newSingleThreadExecutor().submit(() -> richiestaItinerario.getItinerariByUtenteUsername(username));
        Future<List<Segnalazione>> listSegnalazioneFuture = Executors.newSingleThreadExecutor().submit(richiestaSegnalazione::retrieve);
        try {
            itinerarioList=listFuture.get();
            segnalazioneList=listSegnalazioneFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Itinerario> getItinerarioList() {
        return itinerarioList;
    }

    public List<Segnalazione> getSegnalazioneList() {
        return segnalazioneList;
    }
}
