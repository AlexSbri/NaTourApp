package com.example.natourapp;

import com.example.natourapp.services.http.richiestaSegnalazione.RichiestaSegnalazione;

import org.junit.Assert;
import org.junit.Test;

public class RispondiSegnalazioneTest {

    final private RichiestaSegnalazione richiestaSegnalazione = new RichiestaSegnalazione();
    @Test
    public void checkRispondiSegnalazioneSegnalazioneMin1(){
        Assert.assertEquals("Inserire un id segnalazione corretta",richiestaSegnalazione.checkRispondiSegnalazioneParameters("",-10,""));
    }
    @Test
    public void checkRispondiSegnalazioneSegnalazioneMin1Uguale0(){
        Assert.assertEquals("Inserire un id segnalazione corretta",richiestaSegnalazione.checkRispondiSegnalazioneParameters("Risposta",0,"User"));
    }
    @Test
    public void checkRispondiSegnalazioneSegnalazioneMin1Uguale1negativo(){
        Assert.assertEquals("Inserire un id segnalazione corretta",richiestaSegnalazione.checkRispondiSegnalazioneParameters("Risposta",-1,""));
    }
    @Test
    public void checkRispondiSegnalazioneRispostaEmpty(){
        Assert.assertEquals("Inserire risposta",richiestaSegnalazione.checkRispondiSegnalazioneParameters("",1,"user"));
    }
    @Test
    public void checkRispondiSegnalazioneUserEmpty(){
        Assert.assertEquals("Inserire utente a cui vuoi rispondere",richiestaSegnalazione.checkRispondiSegnalazioneParameters("Risposta",2,""));
    }
    @Test
    public void checkRispondiSegnalazioneUserAndRispostaEmpty(){
        Assert.assertEquals("Inserire risposta e utente a cui vuoi rispondere",richiestaSegnalazione.checkRispondiSegnalazioneParameters("",10,""));
    }
    @Test
    public void checkRispondiSegnalazioneDatiCorretti(){
        Assert.assertEquals("Dati inseriti correttamente",richiestaSegnalazione.checkRispondiSegnalazioneParameters("Risposta",10,"User"));
    }
}
