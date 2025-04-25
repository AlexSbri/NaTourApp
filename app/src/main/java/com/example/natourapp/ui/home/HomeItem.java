package com.example.natourapp.ui.home;

import com.example.natourapp.R;
import com.example.natourapp.entities.Itinerario;

import java.util.Objects;

public class HomeItem {
    private final int itinerario_id;
    private final Integer imageFilterViewProfilo;
    private final String nomeUtente;
    private final String titoloItinerario;
    private final Integer imageFilterViewImmagineItinerario;
    private final String descrizione;
    private final String imageFilterViewDifficult;
    private Integer imageFilterViewDisability;
    private final String posizione;
    private final Integer imageFilterViewPositionPin;
    private final Integer imageFilterViewMenuButton;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeItem homeItem = (HomeItem) o;
        return Objects.equals(nomeUtente, homeItem.nomeUtente) && Objects.equals(titoloItinerario, homeItem.titoloItinerario);
    }

    public int getItinerario_id() {
        return itinerario_id;
    }

    public Integer getImageFilterViewProfilo() {
        return imageFilterViewProfilo;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public String getTitoloItinerario() {
        return titoloItinerario;
    }

    public Integer getImageFilterViewImmagineItinerario() {
        return imageFilterViewImmagineItinerario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getImageFilterViewDifficult() {
        return imageFilterViewDifficult;
    }

    public Integer getImageFilterViewDisability() {
        return imageFilterViewDisability;
    }


    public String getPosizione() {
        return posizione;
    }

    public Integer getImageFilterViewPositionPin() {
        return imageFilterViewPositionPin;
    }

    public Integer getImageFilterViewMenuButton() {
        return imageFilterViewMenuButton;
    }

    public HomeItem(Itinerario itinerario){
        this.itinerario_id=itinerario.getItinerario_id();
        imageFilterViewProfilo= R.drawable.foto_montagne_1;
        nomeUtente = itinerario.getUtente_entity().getUsername();
        titoloItinerario = itinerario.getNome_itinerario();
        imageFilterViewImmagineItinerario = itinerario.getImgResource();
        descrizione = itinerario.getDescrizione();
        imageFilterViewDifficult = itinerario.getDifficolta();
        if(itinerario.isServizi_disabili())
            imageFilterViewDisability = R.mipmap.wheelchair_foreground;
        posizione = itinerario.getPunto_di_partenza();
        imageFilterViewPositionPin = R.drawable.ic_location_pin;
        imageFilterViewMenuButton = R.drawable.ic_dot_menu;
    }

}
