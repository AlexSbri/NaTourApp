package com.example.natourapp.services.http.richiestaRecensione;

import com.example.natourapp.entities.Recensione;
import com.example.natourapp.services.http.Richiesta;

import java.io.IOException;
import java.util.List;

public interface RichiestaRecensioneInterface extends Richiesta<Recensione> {
    List<Recensione> retrieveRecensioniByItinerarioId(int itinerarioId) throws IOException;
}
