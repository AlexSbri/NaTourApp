package com.example.natourapp.services.image;

import com.example.natourapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageCollection {
    private List<Integer> imageListItinerari;
    private List<Integer> imageListProfili;
    private static ImageCollection instance;

    private ImageCollection() {
        if(imageListItinerari == null && imageListProfili == null) {
            fillListWithImages();
        }
    }

    private void fillListWithImages() {
        imageListItinerari = new ArrayList<>();
        imageListProfili = new ArrayList<>();
        imageListProfili.add(R.drawable.immagine_profilo_2);
        imageListProfili.add(R.drawable.immagine_profilo_5);
        imageListProfili.add(R.drawable.immagine_profilo_6);
        imageListProfili.add(R.drawable.immagine_profilo_7);
        imageListProfili.add(R.drawable.immagine_profilo_8);
        imageListItinerari.add(R.drawable.foto_montagne_1);
        imageListItinerari.add(R.drawable.foto_montagne_2);
        imageListItinerari.add(R.drawable.foto_montagne_3);
        imageListItinerari.add(R.drawable.foto_montagne_4);
        imageListItinerari.add(R.drawable.foto_montagne_5);
    }

    public static ImageCollection getInstance() {
        if(instance == null) instance = new ImageCollection();
        return instance;
    }


    public int getRandomImmaginiItinerarioResource() {
        Random random = new Random();
        return imageListItinerari.get(random.nextInt(imageListItinerari.size()));
    }

    public int getRandomImmaginiProfiloResource() {
        Random random = new Random();
        return imageListProfili.get(random.nextInt(imageListProfili.size()));
    }
}
