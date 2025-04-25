package com.example.natourapp.entities;

public class Recensione {
    private int recensione_id;
    private String titolo_recensione;
    private String descrizione;
    private double valutazione;
    private Utente utente_entity;
    private Itinerario itinerario_entity;

    public Recensione(){}

    public Recensione(int recensione_id) {this.recensione_id = recensione_id;}

    public int getRecensione_id() {
        return recensione_id;
    }

    public void setRecensione_id(int recensione_id) {
        this.recensione_id = recensione_id;
    }

    public String getTitolo_recensione() {
        return titolo_recensione;
    }

    public void setTitolo_recensione(String titolo_recensione) {
        this.titolo_recensione = titolo_recensione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getValutazione() {
        return valutazione;
    }

    public void setValutazione(double valutazione) {
        this.valutazione = valutazione;
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
        return "Recensione{" +
                "recensione_id=" + recensione_id +
                ", titolo_recensione='" + titolo_recensione + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", valutazione=" + valutazione +
                ", utente_entity=" + utente_entity +
                ", itinerario_entity=" + itinerario_entity +
                '}';
    }
}
