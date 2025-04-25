package com.example.natourapp.entities;

import com.example.natourapp.services.image.ImageCollection;

public class Utente {
    private String username;
    private transient Integer immagineResource;

    public Utente(){
        setImmagineResource();
    }
    public Utente(String username){
        this.username = username;
        setImmagineResource();
    }

    public void setImmagineResource() {
        this.immagineResource = ImageCollection.getInstance().getRandomImmaginiProfiloResource();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Integer getImmagineResource() {
        return immagineResource;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                '}';
    }
}
