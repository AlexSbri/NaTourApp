package com.example.natourapp.ui.cerca;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerarioInterface;
import com.example.natourapp.services.http.richiestaItinerarioStat.RichiestaItinerarioStat;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CercaViewModel extends ViewModel {
    private RichiestaItinerarioInterface richiestaItinerarioInterface;
    private List<Itinerario> itinerarioList;
    private List<ItinerarioStat> itinerarioStatList;
    private String difficolta;
    private String nomeItinerario;
    private boolean serviziDisabili;
    private int durata;

    public void setItinerarioDatas(String nomeItinerario,String difficolta,boolean serviziDisabili,int durata) {
        this.nomeItinerario = nomeItinerario;
        this.difficolta = difficolta;
        this.serviziDisabili = serviziDisabili;
        this.durata = durata;
    }

    public boolean checkParametersRequest() {
        richiestaItinerarioInterface = new RichiestaItinerario();
        return richiestaItinerarioInterface.checkItinerarioSearchParameters(difficolta,durata,serviziDisabili);
    }

    public void loadItinerari(){
        richiestaItinerarioInterface = new RichiestaItinerario();
        Richiesta<ItinerarioStat> richiestaItinerarioStat = new RichiestaItinerarioStat();
        Future<List<ItinerarioStat>> listFutureStats = Executors.newSingleThreadExecutor()
                .submit(richiestaItinerarioStat::retrieve);
        Future<List<Itinerario>> listFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaItinerarioInterface.searchItinerario(nomeItinerario,difficolta,serviziDisabili,durata));
        try {
            itinerarioList = listFuture.get();
            itinerarioStatList = listFutureStats.get();
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Itinerario> getItinerarioList() {
        return itinerarioList;
    }

    public List<ItinerarioStat> getItinerarioStatList() {
        return itinerarioStatList;
    }
}