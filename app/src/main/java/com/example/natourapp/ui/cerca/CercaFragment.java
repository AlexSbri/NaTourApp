package com.example.natourapp.ui.cerca;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.natourapp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class CercaFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "CercaFragment:";
    private EditText editTextNomeItinerario;
    private SwitchMaterial switchServiziDisabili;
    private NumberPicker numberPicker;
    private RadioButton radioButtonTuristico;
    private RadioButton radioButtonEscursionistico;
    private RadioButton radioButtonEsperti;
    private RadioButton radioButtonEspertiAttrezzati;
    private RadioButton radioButtonAmbienteInnevato;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cerca_fragment,container,false);
        String[] valuesNumberPicker = new String[]{
                "1","2","3","4","5","6","7","8","9","10"
        };
        numberPicker = view.findViewById(R.id.numberPicker2);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);
        numberPicker.setDisplayedValues(valuesNumberPicker);
        editTextNomeItinerario = view.findViewById(R.id.editTextCercaNomeItinerario);
        switchServiziDisabili = view.findViewById(R.id.switchDisabiliFilter);
        radioButtonTuristico = view.findViewById(R.id.radioButtonTuristicoFilter);
        radioButtonEscursionistico = view.findViewById(R.id.radioButtonEscursionisticoFilter);
        radioButtonEsperti = view.findViewById(R.id.radioButtonEspertiFilter);
        radioButtonEspertiAttrezzati = view.findViewById(R.id.radioButtonEscursionistiAttrezzatiFilter);
        radioButtonAmbienteInnevato = view.findViewById(R.id.radioButtonInnevatoFilter);

        view.findViewById(R.id.cercaButton).setOnClickListener(view1 -> {
            Log.d(TAG, CLASSNAME+"bottone cerca cliccato");
            CercaViewModel cercaViewModel = new ViewModelProvider(requireActivity()).get(CercaViewModel.class);
            String nomeItinerarioEditText = editTextNomeItinerario.getText().toString();
            int numeroSelezionato = numberPicker.getValue();
            boolean serviziDisabili = switchServiziDisabili.isChecked();
            String difficolta =getTextRadioButtonSelected();
            cercaViewModel.setItinerarioDatas(nomeItinerarioEditText,difficolta,serviziDisabili,numeroSelezionato);
            if(!cercaViewModel.checkParametersRequest()) {
                Toast.makeText(getContext(), "Dati inseriti non validi!", Toast.LENGTH_SHORT).show();
                return;
            }
            NavDirections directions = CercaFragmentDirections.actionNavigationSearchToSearchResultFragment();
            cercaViewModel.loadItinerari();
            Navigation.findNavController(view1).navigate(directions);
        });
        return view;
    }

    private String getTextRadioButtonSelected(){
        if(radioButtonEscursionistico.isChecked())return radioButtonEscursionistico.getText().toString();
        if(radioButtonEsperti.isChecked())return radioButtonEsperti.getText().toString();
        if(radioButtonEspertiAttrezzati.isChecked())return radioButtonEspertiAttrezzati.getText().toString();
        if(radioButtonAmbienteInnevato.isChecked())return radioButtonAmbienteInnevato.getText().toString();
        return radioButtonTuristico.getText().toString();
    }

}