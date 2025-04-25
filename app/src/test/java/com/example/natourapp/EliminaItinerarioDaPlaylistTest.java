package com.example.natourapp;

import com.example.natourapp.services.http.richiestaPlaylist.RichiestaPlaylist;

import org.junit.Assert;
import org.junit.Test;

public class EliminaItinerarioDaPlaylistTest {

    @Test
    public void checkParametriVicinoAlloZeroTrue(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertTrue(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(1, 1));
    }
    @Test
    public void checkParametriVicinoAlloZeroTrueV2(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertTrue(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(2, 2));
    }
    @Test
    public void checkParametriVicinoAlloZeroFalse(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertFalse(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(0, 0));
    }
    @Test
    public void checkParametriVicinoAlloZeroFalseV2(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertFalse(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(-1, -1));
    }
    @Test
    public void checkParametriSbagliati(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertFalse(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(-10, -10));
    }
    @Test
    public void checkParametriGiusti(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertTrue(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(10, 10));
    }
    @Test
    public void checkParametriItinerario_idSbagliatoPlaylist_idGiusto(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertFalse(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(-4, 8));
    }

    @Test
    public void checkParametriItinerario_idGiustoPlaylist_idSbagliato(){
        RichiestaPlaylist richiestaPlaylist = new RichiestaPlaylist();
        Assert.assertFalse(richiestaPlaylist.checkDeleteItinerarioFromPlaylistparameters(8, -6));
    }
}
