package com.example.natourapp.entities;

import com.example.natourapp.services.image.ImageCollection;

public class Itinerario {
    private Integer itinerario_id;
    private String nome_itinerario;
    private String difficolta;
    private boolean servizi_disabili;
    private Integer durata;
    private String punto_di_partenza;
    private String tracciato;
    private String descrizione;
    private Utente utente_entity;

    private transient Integer imgResource;

    public Itinerario(){
        setImgResource();
    }
    public Itinerario(int itinerario_id) {
        setImgResource();
        this.itinerario_id = itinerario_id;
    }
    public void setImgResource() {
        this.imgResource = ImageCollection.getInstance().getRandomImmaginiItinerarioResource();
    }

    public Integer getItinerario_id() {
        return itinerario_id;
    }


    public Integer getImgResource() {
        return imgResource;
    }

    public void setItinerario_id(Integer itinerario_id) {
        this.itinerario_id = itinerario_id;
    }

    public String getNome_itinerario() {
        return nome_itinerario;
    }

    public void setNome_itinerario(String nome_itinerario) {
        this.nome_itinerario = nome_itinerario;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
    }

    public boolean isServizi_disabili() {
        return servizi_disabili;
    }

    public void setServizi_disabili(boolean servizi_disabili) {
        this.servizi_disabili = servizi_disabili;
    }

    public Integer getDurata() {
        return durata;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public String getPunto_di_partenza() {
        return punto_di_partenza;
    }

    public void setPunto_di_partenza(String punto_di_partenza) {
        this.punto_di_partenza = punto_di_partenza;
    }

    public String getTracciato() {
        return tracciato;
    }

    public void setTracciato(String tracciato) {
        this.tracciato = tracciato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Utente getUtente_entity() {
        return utente_entity;
    }

    public void setUtente_entity(Utente utente_entity) {
        this.utente_entity = utente_entity;
    }

    @Override
    public String toString() {
        return "Itinerario{" +
                "itinerario_id=" + itinerario_id +
                ", nome_itinerario='" + nome_itinerario + '\'' +
                ", difficolta='" + difficolta + '\'' +
                ", servizi_disabili=" + servizi_disabili +
                ", durata=" + durata +
                ", punto_di_partenza='" + punto_di_partenza + '\'' +
                ", tracciato='" + tracciato + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", utente_entity=" + utente_entity +
                '}';
    }
}
