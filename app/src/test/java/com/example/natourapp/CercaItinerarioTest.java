package com.example.natourapp;

import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;

import org.junit.Assert;
import org.junit.Test;

public class CercaItinerarioTest {

    private final RichiestaItinerario richiestaItinerario = new RichiestaItinerario();

    @Test
    public void cercaItinerarioDifficoltaNonValida() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("turistico",1,true));
    }

    @Test
    public void cercaItinerarioDurataNegativa() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Turistico",-1,false));
    }

    @Test
    public void cercaItinerarioDurataZero() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Turistico",0,true));
    }

    @Test
    public void cercaItinerarioDurataOutOfBuond() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Esperti",11,true));
    }

    @Test
    public void cercaItinerarioDurataIsNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Turistico",null,false));
    }

    @Test
    public void cercaItinerarioServiziDisabiliIsNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Turistico",1,null));
    }

    @Test
    public void cercaItinerarioDurataAndServiziDisabiliAreNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Turistico",null,null));
    }

    @Test
    public void cercaItinerarioDifficoltaIsNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters(null,1,true));
    }

    @Test
    public void cercaItinerarioAllNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters(null,null,null));
    }

    @Test
    public void cercaItinerarioDifficoltaAndDurataAreNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters(null,null,true));
    }

    @Test
    public void cercaItinerarioDIfficoltaAndServiziDisabiliAreNull() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters(null,1,null));
    }

    @Test
    public void cercaItinerarioTestNearBound() {
        Assert.assertTrue(richiestaItinerario.checkItinerarioSearchParameters("Esperti",10,true));
    }

    @Test
    public void cercaItinerarioTestOutOfBoundv2() {
        Assert.assertFalse(richiestaItinerario.checkItinerarioSearchParameters("Esperti",12,true));
    }

    @Test
    public void cercaItinerarioTest() {
        Assert.assertTrue(richiestaItinerario.checkItinerarioSearchParameters("Esperti attrezzati",9,false));
    }
}
