package com.example.natourapp.ui.playlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;

public class CreaPlaylistFragment extends Fragment {
    private static final String TAG = "TEST";
    private static final String CLASSNAME ="CreaPlaylistFragment:";
    private EditText editTextNomePlaylist;
    private EditText editTextDescrizionePlaylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crea_playlist, container, false);
        editTextNomePlaylist = view.findViewById(R.id.NomePlaylistEditText);
        editTextDescrizionePlaylist = view.findViewById(R.id.DescrizionePlaylistEditText);

        Button buttonCreaPlaylist = view.findViewById(R.id.CreaPlaylistButton);
        buttonCreaPlaylist.setOnClickListener(view1 -> {
            Log.d(TAG, CLASSNAME+"bottone crea playlist cliccato");
            setCustomField();
            if (checkEditText()) {
                String nomePlaylist = editTextNomePlaylist.getText().toString();
                String descrizionePlaylist = editTextDescrizionePlaylist.getText().toString();
                String username = Cognito.SharedPrefApp.getInstance().getUsername(getContext());
                PlaylistViewModel playlistViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);
                playlistViewModel.createPlaylist(username,nomePlaylist,descrizionePlaylist);
                String response = playlistViewModel.sendPlaylist();
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private boolean checkEditText(){
        boolean check = true;
        if(TextUtils.isEmpty(editTextNomePlaylist.getText().toString())){
            editTextNomePlaylist.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(getContext(), "Inserisci il nome della playlist ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if(TextUtils.isEmpty(editTextDescrizionePlaylist.getText().toString())){
            editTextDescrizionePlaylist.setBackgroundResource(R.drawable.edit_text_error_state);
            Toast.makeText(getContext(), "Inserisci la descrizione ", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    private void setCustomField(){
        editTextNomePlaylist.setBackgroundResource(R.drawable.text_field_custom);
        editTextDescrizionePlaylist.setBackgroundResource(R.drawable.text_field_custom);
    }
}