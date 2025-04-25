package com.example.natourapp.Activities.mostra_itinerario;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.entities.Recensione;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerarioInterface;
import com.example.natourapp.services.http.richiestaItinerarioStat.RichiestaItinerarioStat;
import com.example.natourapp.services.http.richiestaItinerarioStat.RichiestaItinerarioStatInterface;
import com.example.natourapp.services.http.richiestaRecensione.RichiestaRecensione;
import com.example.natourapp.services.http.richiestaRecensione.RichiestaRecensioneInterface;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MostraItinerarioViewModel extends ViewModel {
    private Itinerario itinerario;
    private ItinerarioStat itinerarioStat;
    private List<Recensione> recensioneList;

    public void loadItinerarioData(int itinerarioId) {
        RichiestaRecensioneInterface richiestaRecensione = new RichiestaRecensione();
        RichiestaItinerarioInterface itinerarioRichiesta = new RichiestaItinerario();
        RichiestaItinerarioStatInterface itinerarioStatRichiesta = new RichiestaItinerarioStat();
        Future<ItinerarioStat> itinerarioStatFuture = Executors.newSingleThreadExecutor()
                .submit(() -> itinerarioStatRichiesta.retrieveItinerarioStatByItinerarioId(itinerarioId));
        Future<List<Recensione>> futureRecensioneList = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaRecensione.retrieveRecensioniByItinerarioId(itinerarioId));
        Future<Itinerario> itinerarioFuture = Executors.newSingleThreadExecutor()
                .submit(() -> itinerarioRichiesta.getItinerarioById(itinerarioId));
        try {
            itinerario = itinerarioFuture.get();
            recensioneList = futureRecensioneList.get();
            itinerarioStat = itinerarioStatFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public ItinerarioStat getItinerarioStat() {
        return itinerarioStat;
    }

    public List<Recensione> getRecensioneList() {
        return recensioneList;
    }
}
