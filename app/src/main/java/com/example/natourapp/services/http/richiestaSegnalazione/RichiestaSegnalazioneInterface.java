package com.example.natourapp.services.http.richiestaSegnalazione;

import com.example.natourapp.entities.Segnalazione;
import com.example.natourapp.services.http.Richiesta;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface RichiestaSegnalazioneInterface extends Richiesta<Segnalazione> {
    List<Segnalazione> retrieveSegnalazioniByItinerarioId(int itinerarioId) throws IOException;
    Segnalazione retrieveSegnalazioneById(int segnalazioneId) throws IOException;
    List<Segnalazione> retrieveSegnalazioniByUtenteUsername(String username) throws IOException;
    String rispondiSegnalazioneById(String rispostaSegnalazione,int segnalazioneId, String username) throws IOException;
    String checkRispondiSegnalazioneParameters(String rispostaSegnalazione, int segnalazioneId, String username);
    Long countSegnalazioneItinerarioById(int itinerarioId) throws IOException;
    Segnalazione deserializeSegnalazione(Reader reader);
}
