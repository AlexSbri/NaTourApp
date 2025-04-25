package com.example.natourapp.services.http.richiestaPlaylist;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Playlist;
import com.example.natourapp.services.http.Richiesta;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface RichiestaPlaylistInterface extends Richiesta<Playlist> {
    String addToPlaylist(int itinerarioId, int playlistId) throws IOException;

    String deletePlaylist(int playlistId) throws IOException;

    String deleteItinerarioByIdFromPlaylist(int itinerarioId, int playlistId) throws IOException;

    List<Playlist> getPlaylistByUtenteUsername(String username) throws IOException;

    List<Itinerario> deserializeItinerarioListForPlaylist(Reader reader);

    List<Itinerario> getContentPlaylist(int playlistId) throws IOException;

    boolean checkDeleteItinerarioFromPlaylistparameters(int itinerarioId, int playlistId);
}
