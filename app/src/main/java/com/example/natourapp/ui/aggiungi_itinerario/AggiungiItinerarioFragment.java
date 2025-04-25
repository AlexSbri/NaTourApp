package com.example.natourapp.ui.aggiungi_itinerario;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.databinding.AggiungiItinerarioFragmentBinding;
import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.services.location.LocationService;
import com.example.natourapp.services.xml.XmlParser;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AggiungiItinerarioFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "AggiungiItinerarioFragment:";
    private String encodedPath;
    private NumberPicker numberPickerDurata;
    private RadioButton radioButtonTuristico;
    private RadioButton radioButtonEscursionistico;
    private RadioButton radioButtonEsperti;
    private RadioButton radioButtonAttrezzati;
    private RadioButton radioButtonInnevato;
    private EditText editTextNomeItinerario;
    private EditText editTextPuntoDiPartenza;
    private CheckBox checkBoxServiziDisabili;
    private EditText editTextDescrizione;
    private ImageButton imageButtonGoogleMaps;
    private ImageButton imageButtonGPX;
    private AggiungiItinerarioViewModel aggiungiItinerarioViewModel;
    private LocationService locationService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        aggiungiItinerarioViewModel = new ViewModelProvider(requireActivity()).get(AggiungiItinerarioViewModel.class);
        View view =inflater.inflate(R.layout.aggiungi_itinerario_fragment, container, false);
        checkCoarsePermission();
        encodedPath = AggiungiItinerarioFragmentArgs.fromBundle(getArguments()).getPath();
        if(aggiungiItinerarioViewModel.getItinerario()!=null)
            encodedPath = aggiungiItinerarioViewModel.getItinerario().getTracciato();
        numberPickerDurata = view.findViewById(R.id.numberPicker);
        numberPickerDurata.setMinValue(1);
        numberPickerDurata.setMaxValue(10);
        numberPickerDurata.setValue(1);
        numberPickerDurata.setDisplayedValues(aggiungiItinerarioViewModel.getValuesNumberPicker());
        radioButtonTuristico = view.findViewById(R.id.radioButtonTuristico);
        radioButtonEscursionistico = view.findViewById(R.id.radioButtonEscursionistico);
        radioButtonEsperti = view.findViewById(R.id.radioButtonEsperti);
        radioButtonAttrezzati = view.findViewById(R.id.radioButtonEscursionistiAttrezzati);
        radioButtonInnevato = view.findViewById(R.id.radioButtonInnevato);
        editTextNomeItinerario = view.findViewById(R.id.editTextNomeItinerario);
        editTextPuntoDiPartenza = view.findViewById(R.id.editTextPuntoDiPartenza);
        checkBoxServiziDisabili = view.findViewById(R.id.checkBox);
        editTextDescrizione = view.findViewById(R.id.editTextDescrizione);
        imageButtonGoogleMaps = view.findViewById(R.id.googleMapsButton);
        imageButtonGPX = view.findViewById(R.id.gpxButton);
        locationService = new LocationService(view.getContext());
        locationService.startLocationUpdates();
        tracciatoInserito(view);
        imageButtonGoogleMaps.setOnClickListener(view1 -> {
            Log.d(TAG, CLASSNAME+"bottone google maps cliccato");
            SharedPreferences sharedPreferences = view1.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
            aggiungiItinerarioViewModel.setLat(((double) sharedPreferences.getFloat("latitude", 0)));
            aggiungiItinerarioViewModel.setLng(((double) sharedPreferences.getFloat("longitude", 0)));
            AggiungiItinerarioFragmentDirections
                    .ActionNavigationAggiungiItinerarioToMapsFragment action =
                    AggiungiItinerarioFragmentDirections.actionNavigationAggiungiItinerarioToMapsFragment();
            action.setUserLat(aggiungiItinerarioViewModel.getLat().floatValue());
            action.setUserLng(aggiungiItinerarioViewModel.getLng().floatValue());
            Navigation.findNavController(view1).navigate(action);
        });

        imageButtonGPX.setOnClickListener(view12 -> {
            Log.d(TAG+"gpxButton", "bottone gpx cliccato");
            openFile(Uri.parse("Android/data/"));
        });

        view.findViewById(R.id.pubblicaButton).setOnClickListener(view13 -> {
            Log.d(TAG, CLASSNAME+"bottone pubblica cliccato");
            setCustomField();
            String nomeItinerario = editTextNomeItinerario.getText().toString();
            String descrizioneItinerario = editTextDescrizione.getText().toString();
            String difficolta = getTextRadioButtonSelected();
            boolean serviziDisabili = checkBoxServiziDisabili.isChecked();
            int durata = numberPickerDurata.getValue();
            String puntoDiPartenza = editTextPuntoDiPartenza.getText().toString();
            String tracciato = encodedPath;
            String username = Cognito.SharedPrefApp.getInstance().getUsername(getContext());
            if (checkEditText()) {
                aggiungiItinerarioViewModel.createItinerario(nomeItinerario,descrizioneItinerario
                        ,difficolta,serviziDisabili,durata,puntoDiPartenza,tracciato,username);
                String response = aggiungiItinerarioViewModel.addItinerario();
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(locationService != null) locationService.stopLocationLocationUpdates();
        saveState();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(aggiungiItinerarioViewModel.getItinerario() != null) {
            editTextDescrizione.setText(aggiungiItinerarioViewModel.getItinerario().getDescrizione());
            editTextNomeItinerario.setText(aggiungiItinerarioViewModel.getItinerario().getNome_itinerario());
            editTextPuntoDiPartenza.setText(aggiungiItinerarioViewModel.getItinerario().getPunto_di_partenza());
            numberPickerDurata.setValue(aggiungiItinerarioViewModel.getItinerario().getDurata());
            checkBoxServiziDisabili.setChecked(aggiungiItinerarioViewModel.getItinerario().isServizi_disabili());
            selectRadioButton(aggiungiItinerarioViewModel.getItinerario());
            encodedPath = aggiungiItinerarioViewModel.getItinerario().getTracciato();
        }
    }

    private void selectRadioButton(Itinerario itinerario) {
        if(itinerario.getDifficolta().equals(radioButtonAttrezzati.getText().toString())) radioButtonAttrezzati.toggle();
        if(itinerario.getDifficolta().equals(radioButtonEscursionistico.getText().toString())) radioButtonEscursionistico.toggle();
        if(itinerario.getDifficolta().equals(radioButtonEsperti.getText().toString())) radioButtonEsperti.toggle();
        if(itinerario.getDifficolta().equals(radioButtonInnevato.getText().toString())) radioButtonInnevato.toggle();
        if(itinerario.getDifficolta().equals(radioButtonTuristico.getText().toString())) radioButtonTuristico.toggle();
    }

    private String getTextRadioButtonSelected(){
        if(radioButtonEscursionistico.isChecked())return radioButtonEscursionistico.getText().toString();
        if(radioButtonEsperti.isChecked())return radioButtonEsperti.getText().toString();
        if(radioButtonAttrezzati.isChecked())return radioButtonAttrezzati.getText().toString();
        if(radioButtonInnevato.isChecked())return radioButtonInnevato.getText().toString();
        return radioButtonTuristico.getText().toString();

    }

    private boolean checkEditText(){
        boolean check = true;
        if(TextUtils.isEmpty(editTextNomeItinerario.getText().toString())){
            editTextNomeItinerario.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(getContext(), "Inserisci il nome dell'itinerario ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if(TextUtils.isEmpty(editTextPuntoDiPartenza.getText().toString())){
            editTextPuntoDiPartenza.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(getContext(), "Inserisci il punto di partenza ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }
    private void setCustomField(){
        editTextNomeItinerario.setBackgroundResource(R.drawable.text_field_custom);
        editTextPuntoDiPartenza.setBackgroundResource(R.drawable.text_field_custom);
    }

    private void openFile(Uri uri){
        Log.d(TAG, CLASSNAME+"apertura file manager");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI,uri);
        fileHandlingGpx.launch(intent);
    }

    ActivityResultLauncher<Intent> fileHandlingGpx =
            this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        List<LatLng> lista = null;
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri;
                            if (result.getData() != null) {
                                uri = result.getData().getData();
                                if (uri.toString().contains(".gpx")) {
                                    try {
                                        InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                                        XmlParser xmlParser = new XmlParser(inputStream);
                                        lista = xmlParser.getListLatLng();
                                        Log.d(TAG, CLASSNAME+"file selezionato corretto");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Log.e(TAG, CLASSNAME + "file selezionato non corretto");
                                    Toast.makeText(AggiungiItinerarioFragment.this.getContext(), "il file selezionato non è un file gpx", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if (lista != null){
                            lista = PolyUtil.simplify(lista,100);
                            encodedPath = PolyUtil.encode(lista);
                            saveState();
                            tracciatoInserito(this.getView());
                        }
                    });

    private void checkCoarsePermission(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),permesso->{
                if(!permesso) {
                    Toast.makeText(getContext(), "accettare i permessi per avere una migliore esperienza", Toast.LENGTH_SHORT).show();
                }
            });
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    private void tracciatoInserito(View view){
        if (!encodedPath.isEmpty()) {
            imageButtonGPX.setClickable(false);
            imageButtonGoogleMaps.setClickable(false);
            imageButtonGoogleMaps.setVisibility(View.INVISIBLE);
            imageButtonGPX.setVisibility(View.INVISIBLE);
            view.findViewById(R.id.label_for_google_maps_button).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.label_for_gpx_file).setVisibility(View.INVISIBLE);
            TextView textViewLabelTracciato = view.findViewById(R.id.label_for_inserisci_tracciato);
            textViewLabelTracciato.setText(R.string.tracciato_inserito);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState(){
        String nomeItinerario = editTextNomeItinerario.getText().toString();
        String descrizioneItinerario = editTextDescrizione.getText().toString();
        String difficolta = getTextRadioButtonSelected();
        boolean serviziDisabili = checkBoxServiziDisabili.isChecked();
        int durata = numberPickerDurata.getValue();
        String puntoDiPartenza = editTextPuntoDiPartenza.getText().toString();
        String tracciato = encodedPath;
        aggiungiItinerarioViewModel.createItinerario(nomeItinerario,descrizioneItinerario,difficolta,
                serviziDisabili,durata,puntoDiPartenza,tracciato,Cognito.SharedPrefApp.getInstance().getUsername(getContext()));
    }

}