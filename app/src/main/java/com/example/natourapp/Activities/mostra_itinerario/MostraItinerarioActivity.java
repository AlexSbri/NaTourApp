package com.example.natourapp.Activities.mostra_itinerario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.Activities.recensione.RecensioneActivity;
import com.example.natourapp.R;
import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.entities.Recensione;
import com.example.natourapp.Activities.recensione.RecensioneActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;
import java.util.Locale;

public class MostraItinerarioActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MostraItinerarioViewModel mostraItinerarioViewModel;
    private MapView mapView;
    private static final String TAG="TEST";
    private static final String CLASSNAME = "MostraItinerarioActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostraItinerarioViewModel = new ViewModelProvider(this).get(MostraItinerarioViewModel.class);
        setContentView(R.layout.activity_mostra_itinerario);
        MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LEGACY, renderer -> {});
        Intent intent = getIntent();
        int itinerarioId = intent.getIntExtra("itinerario_id",-1);
        mostraItinerarioViewModel.loadItinerarioData(itinerarioId);
        String titoloItinerario = intent.getStringExtra("titolo_itinerario");
        setTitle(titoloItinerario);
        findViewById(R.id.buttonScriviRecensione).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone scrivi recensione cliccato");
            Intent intentForRecensione = new Intent(this, RecensioneActivity.class);
            intentForRecensione.putExtra("itinerario_id",itinerarioId);
            intentForRecensione.putExtra("titolo_itinerario",titoloItinerario);
            startActivity(intentForRecensione);
        });
        RatingBar ratingBarValutazione = findViewById(R.id.ratingBarValutazione);
        TextView textViewNumeroRecensioni = findViewById(R.id.textViewNumeroRecensioniMostraItinerario);
        TextView textViewDescrizioneItinerario = findViewById(R.id.textViewDescrizioneMostraItinerario);
        TextView textViewDurataItinerario = findViewById(R.id.textViewDurata);
        TextView textViewPuntoDiPartenza = findViewById(R.id.textViewPuntoPartenzaContentMostraItinerario);
        ImageView imageViewServiziDisabili = findViewById(R.id.imageViewServiziDisabili);
        TextView textViewDifficolta = findViewById(R.id.textViewDifficultMostraItinerarioContent);
        ImageView imageViewIsSegnalato = findViewById(R.id.imageViewIsSegnalato);
        Itinerario itinerario = mostraItinerarioViewModel.getItinerario();
        ItinerarioStat itinerarioStat = mostraItinerarioViewModel.getItinerarioStat();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        if(itinerarioStat != null){
            ratingBarValutazione.setRating((float) itinerarioStat.getAvg());
            textViewNumeroRecensioni.setText(String.format(Locale.ITALY,"%d", itinerarioStat.getNumero_recensioni()));
            if(itinerarioStat.getNumero_segnalazioni() == 0){
                imageViewIsSegnalato.setVisibility(View.INVISIBLE);
            }else imageViewIsSegnalato.setVisibility(View.VISIBLE);
        }else imageViewIsSegnalato.setVisibility(View.INVISIBLE);

        textViewDescrizioneItinerario.setText(itinerario.getDescrizione());
        textViewDurataItinerario.setText(String.format(Locale.ITALY, "%d", itinerario.getDurata()));
        textViewPuntoDiPartenza.setText(itinerario.getPunto_di_partenza());
        textViewDifficolta.setText(itinerario.getDifficolta());
        if (itinerario.isServizi_disabili())
            imageViewServiziDisabili.setImageResource(R.mipmap.wheelchair_foreground);
        List<Recensione> recensioneList = mostraItinerarioViewModel.getRecensioneList();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewMostraItinerario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new MostraitinerarioAdapter(recensioneList));
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        if(!mostraItinerarioViewModel.getItinerario().getTracciato().isEmpty()) {
            List<LatLng> latLngList = PolyUtil.decode(mostraItinerarioViewModel.getItinerario().getTracciato());
            googleMap.addMarker(new MarkerOptions().position(latLngList.get(0)).title("punto di partenza"));
            googleMap.setMinZoomPreference(5.0f);
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(latLngList);
            googleMap.addPolyline(polylineOptions);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}