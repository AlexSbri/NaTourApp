package com.example.natourapp.services.http.richiestaItinerarioStat;

import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.services.http.Richiesta;

import java.io.IOException;
import java.io.Reader;

public interface RichiestaItinerarioStatInterface extends Richiesta<ItinerarioStat> {
    ItinerarioStat retrieveItinerarioStatByItinerarioId(int itinerarioId) throws IOException;
    ItinerarioStat deserializeItinerarioStat(Reader reader);

}
