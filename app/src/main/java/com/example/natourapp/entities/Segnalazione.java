package com.example.natourapp.entities;

public class Segnalazione {
    private Integer segnalazione_id;
    private String titolo_segnalazione;
    private String descrizione_segnalazione;
    private String motivazione_segnalazione;
    private String risposta_segnalazione;
    private Utente utente_entity;
    private Itinerario itinerario_entity;

    public int getSegnalazione_id() {
        return segnalazione_id;
    }

    public void setSegnalazione_id(int segnalazione_id) {
        this.segnalazione_id = segnalazione_id;
    }

    public String getTitolo_segnalazione() {
        return titolo_segnalazione;
    }

    public void setTitolo_segnalazione(String titolo_segnalazione) {
        this.titolo_segnalazione = titolo_segnalazione;
    }

    public String getDescrizione_segnalazione() {
        return descrizione_segnalazione;
    }

    public void setDescrizione_segnalazione(String descrizione_segnalazione) {
        this.descrizione_segnalazione = descrizione_segnalazione;
    }

    public String getMotivazione_segnalazione() {
        return motivazione_segnalazione;
    }

    public void setMotivazione_segnalazione(String motivazione_segnalazione) {
        this.motivazione_segnalazione = motivazione_segnalazione;
    }

    public String getRisposta_segnalazione() {
        return risposta_segnalazione;
    }

    public void setRisposta_segnalazione(String risposta_segnalazione) {
        this.risposta_segnalazione = risposta_segnalazione;
    }

    public Utente getUtente_entity() {
        return utente_entity;
    }

    public void setUtente_entity(Utente utente_entity) {
        this.utente_entity = utente_entity;
    }

    public Itinerario getItinerario_entity() {
        return itinerario_entity;
    }

    public void setItinerario_entity(Itinerario itinerario_entity) {
        this.itinerario_entity = itinerario_entity;
    }

    @Override
    public String toString() {
        return "Segnalazione{" +
                "segnalazione_id=" + segnalazione_id +
                ", titolo_segnalazione='" + titolo_segnalazione + '\'' +
                ", descrizione_segnalazione='" + descrizione_segnalazione + '\'' +
                ", motivazione_segnalazione='" + motivazione_segnalazione + '\'' +
                ", risposta_segnalazione='" + risposta_segnalazione + '\'' +
                ", utente_entity=" + utente_entity +
                ", itinerario_entity=" + itinerario_entity +
                '}';
    }
}
