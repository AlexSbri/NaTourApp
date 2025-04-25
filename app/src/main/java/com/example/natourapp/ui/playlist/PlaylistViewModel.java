package com.example.natourapp.ui.playlist;

import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.Playlist;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaPlaylist.RichiestaPlaylist;
import com.example.natourapp.services.http.richiestaPlaylist.RichiestaPlaylistInterface;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlaylistViewModel extends ViewModel {
    private Playlist playlist;
    private List<Playlist> playlistList;
    private List<Itinerario> playlisContent;
    private RichiestaPlaylistInterface richiestaPlaylistInterface;

    public String addToPlaylistContent(int itinerarioId,int playlistId) {
        RichiestaPlaylistInterface richiestaPlaylistInterface = new RichiestaPlaylist();
        Future<String> contentFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaPlaylistInterface.addToPlaylist(itinerarioId,playlistId));
        String response = "";
        try {
            response = contentFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void loadContentPlaylistByPlaylistId(int playlistId){
        RichiestaPlaylistInterface richiestaPlaylist = new RichiestaPlaylist();
        Future<List<Itinerario>> contentPlaylistFuture = Executors.newSingleThreadExecutor().submit(() ->
                richiestaPlaylist.getContentPlaylist(playlistId));
        try {
            playlisContent = contentPlaylistFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadPlaylistByutenteUsername(String username){
        richiestaPlaylistInterface = new RichiestaPlaylist();
        Future<List<Playlist>> playlistsFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaPlaylistInterface.getPlaylistByUtenteUsername(username));
        try {
            playlistList = playlistsFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createPlaylist(String username,String nomePlaylist, String descrizionePlaylist) {
        Utente utente = new Utente(username);
        playlist = new Playlist();
        playlist.setTitolo_playlist(nomePlaylist);
        playlist.setDescrizione_playlist(descrizionePlaylist);
        playlist.setUtente_entity(utente);
    }

    public String sendPlaylist() {
        Richiesta<Playlist> richiestaPlaylist = new RichiestaPlaylist();
        Future<String> playlistFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaPlaylist.add(playlist));
        String response = "";
        try {
            response = playlistFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean deleteContentPlaylist(int itinerarioId, int playlistId) {
        RichiestaPlaylistInterface richiestaPlaylistInterface = new RichiestaPlaylist();
        if(richiestaPlaylistInterface.checkDeleteItinerarioFromPlaylistparameters(itinerarioId,playlistId))
            return playlisContent.removeIf(itinerario -> itinerario.getItinerario_id() == itinerarioId);
        return false;
    }

    public String deleteItinerarioFromPlaylist(int itinerarioId, int playlistId) {
        RichiestaPlaylistInterface richiestaPlaylistInterface = new RichiestaPlaylist();
        Future<String> playlistFuture = Executors.newSingleThreadExecutor()
                .submit(() -> richiestaPlaylistInterface.deleteItinerarioByIdFromPlaylist(itinerarioId, playlistId));
        String response = "";
        try {
            response = playlistFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String deletePlaylistById(int playlistId) {
        RichiestaPlaylistInterface richiestaPlaylistInterface = new RichiestaPlaylist();
        Future<String> playlistFuture = Executors.newSingleThreadExecutor()
                .submit(()->richiestaPlaylistInterface.deletePlaylist(playlistId));
        String response = "";
        try {
            response = playlistFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public List<Playlist> getPlaylistList() {
        return playlistList;
    }

    public List<Itinerario> getPlaylisContent() {
        return playlisContent;
    }
}