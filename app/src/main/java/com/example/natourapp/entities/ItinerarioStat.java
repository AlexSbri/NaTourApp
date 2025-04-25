package com.example.natourapp.entities;

public class ItinerarioStat {
    private int id;
    private int itinerario_id;
    private double avg;
    private int numero_recensioni;
    private int numero_segnalazioni;

    public int getNumero_segnalazioni() {
        return numero_segnalazioni;
    }

    public void setNumero_segnalazioni(int numero_segnalazioni) {
        this.numero_segnalazioni = numero_segnalazioni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItinerario_id() {
        return itinerario_id;
    }

    public void setItinerario_id(int itinerario_id) {
        this.itinerario_id = itinerario_id;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getNumero_recensioni() {
        return numero_recensioni;
    }

    public void setNumero_recensioni(int count) {
        this.numero_recensioni = count;
    }

    @Override
    public String toString() {
        return "ItinerarioStat{" +
                "id=" + id +
                ", itinerario_id=" + itinerario_id +
                ", avg=" + avg +
                ", numero_recensioni=" + numero_recensioni +
                ", numero_segnalazioni=" + numero_segnalazioni +
                '}';
    }
}
