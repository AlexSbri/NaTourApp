package com.example.natourapp.entities;

import com.example.natourapp.services.image.ImageCollection;

public class Playlist {
    private int playlist_id;
    private String titolo_playlist;
    private String descrizione_playlist;
    private Utente utente_entity;
    private transient Integer copertinaPlaylist;

    public void setCopertinaPlaylist() {
        copertinaPlaylist = ImageCollection.getInstance().getRandomImmaginiItinerarioResource();
    }

    public Playlist() {
        setCopertinaPlaylist();
    }

    public Integer getCopertinaPlaylist() {
        return copertinaPlaylist;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getTitolo_playlist() {
        return titolo_playlist;
    }

    public void setTitolo_playlist(String titolo_playlist) {
        this.titolo_playlist = titolo_playlist;
    }

    public String getDescrizione_playlist() {
        return descrizione_playlist;
    }

    public void setDescrizione_playlist(String descrizione_playlist) {
        this.descrizione_playlist = descrizione_playlist;
    }

    public Utente getUtente_entity() {
        return utente_entity;
    }

    public void setUtente_entity(Utente utente_entity) {
        this.utente_entity = utente_entity;
    }

}
