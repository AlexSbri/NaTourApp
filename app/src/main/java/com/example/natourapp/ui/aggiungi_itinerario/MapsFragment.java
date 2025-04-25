package com.example.natourapp.ui.aggiungi_itinerario;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.natourapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.LinkedList;

public class MapsFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "MapsFragment:";
    private MapView mapView;
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private LinkedList<LatLng> polylineList;
    private LinkedList<Polyline> LLpolyline;
    private GoogleMap googleMapFragment;


    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMapFragment = googleMap;
            MapsFragmentArgs boundle = MapsFragmentArgs.fromBundle(getArguments());
            double lat = boundle.getUserLat();
            double lng = boundle.getUserLng();
            LatLng userPostition = new LatLng(lat,lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(userPostition));
            googleMap.setMinZoomPreference(5.0f);
            googleMap.setOnMapClickListener(latLng -> {
                if(LLpolyline.size()<1) googleMap.addMarker(new MarkerOptions().position(latLng));
                polylineList.add(latLng);
                polylineOptions.color(Color.BLUE);
                polyline = googleMap.addPolyline(polylineOptions);
                LLpolyline.add(polyline);
                polyline.setPoints(polylineList);
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AggiungiItinerarioViewModel aggiungiItinerarioViewModel = new ViewModelProvider(requireActivity()).get(AggiungiItinerarioViewModel.class);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        MapsInitializer.initialize(view.getContext(), MapsInitializer.Renderer.LEGACY, renderer -> {});
        mapView = view.findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(callback);
        polylineOptions = new PolylineOptions();
        polylineList = new LinkedList<>();
        LLpolyline = new LinkedList<>();

        view.findViewById(R.id.floatingActionButtonDeleteMarker).setOnClickListener(v->{
            Log.d(TAG, CLASSNAME+"bottone elimina marker cliccato");
            if(!LLpolyline.isEmpty()){
                LLpolyline.removeLast().remove();
                polylineList.removeLast();
            }else googleMapFragment.clear();
        });

        view.findViewById(R.id.floatingActionButtonAddTracciato).setOnClickListener(v->{
            Log.d(TAG, CLASSNAME+"bottone aggiungi tracciato cliccato");
            if(polylineList.isEmpty()){
                Toast.makeText(getContext(), "disegnare tracciato",Toast.LENGTH_SHORT).show();
                return;
            }
            //NavDirections navDirections = MapsFragmentDirections.actionMapsFragmentToNavigationAggiungiItinerario();
            MapsFragmentDirections.ActionMapsFragmentToNavigationAggiungiItinerario args =
                    MapsFragmentDirections.actionMapsFragmentToNavigationAggiungiItinerario();
            args.setPath(PolyUtil.encode(polylineList));
            aggiungiItinerarioViewModel.getItinerario().setTracciato(args.getPath());
            Navigation.findNavController(v).navigate(args);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
}