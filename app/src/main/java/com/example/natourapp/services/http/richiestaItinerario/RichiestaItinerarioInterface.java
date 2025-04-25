package com.example.natourapp.services.http.richiestaItinerario;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.services.http.Richiesta;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface RichiestaItinerarioInterface extends Richiesta<Itinerario> {
    Itinerario deserializeItinerario(Reader reader);
    List<Itinerario> searchItinerario(String nomeItinerario, String difficolta, boolean serviziDisabili,Integer durata) throws IOException;
    Itinerario getItinerarioById(int itinerarioId) throws IOException;
    List<Itinerario> getItinerariByUtenteUsername(String username) throws IOException;
    boolean checkItinerarioSearchParameters(String difficolta, Integer durata, Boolean servizi_disabili);
}
