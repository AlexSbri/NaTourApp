package com.example.natourapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.natourapp.Activities.segnalazione.SegnalazioneActivity;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.entities.Playlist;
import com.example.natourapp.ui.playlist.PlaylistViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogAdapter {
    private static final String TAG = "TEST";
    private static final String CLASSNAME="DialogAdapter:";
    private static CharSequence[] convertToCHarSequence(List<Playlist> playlistList) {
        List<CharSequence> charSequences = new ArrayList<>();
        for (Playlist p: playlistList) {
            charSequences.add(p.getTitolo_playlist());
        }
        return charSequences.toArray(new CharSequence[playlistList.size()]);
    }

    public static void showDialog(Context context, int itinerario_id, String usernameUtenteItinerario) {
        Log.d("userameaUtenteItinerario showDialog",usernameUtenteItinerario);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        bottomSheetDialog.findViewById(R.id.aggiungiPlaylistDialogButton)
                .setOnClickListener(v ->{
                    Log.d(TAG, CLASSNAME+"bottone aggiungi a playlist cliccato");
                    showDialogAggiungiPlaylist(context,itinerario_id);
                    bottomSheetDialog.dismiss();
                });

        bottomSheetDialog.findViewById(R.id.segnalaButtonDialog)
                .setOnClickListener(v -> {
                    Log.d(TAG, CLASSNAME+"bottone segnala cliccato");
                    Intent intent = new Intent(context, SegnalazioneActivity.class);
                    intent.putExtra("itinerario_id",itinerario_id);
                    intent.putExtra("itinerario_username",usernameUtenteItinerario);
                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                });
        bottomSheetDialog.show();
    }
        private static void showDialogAggiungiPlaylist(Context context,int itinerario_id) {
            HashMap<CharSequence,Integer> mappa = new HashMap<>();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            PlaylistViewModel playlistViewModel = new ViewModelProvider(ViewModelStore::new).get(PlaylistViewModel.class);
            playlistViewModel.loadPlaylistByutenteUsername(Cognito.SharedPrefApp.getInstance().getUsername(context));
            CharSequence[] items;
            List<Playlist> playlistList = playlistViewModel.getPlaylistList();
            List<CharSequence> charSequenceList = new ArrayList<>();
            items = convertToCHarSequence(playlistList);
            for (Playlist p: playlistList) {
                mappa.put(p.getTitolo_playlist(),p.getPlaylist_id());
            }
            if(items.length == 0) {
                Toast.makeText(context, "non hai ancora creato una playlist", Toast.LENGTH_SHORT).show();
                return;
            }
            charSequenceList.add(items[0]);
            builder.setTitle("Le tue playlist");
            builder.setSingleChoiceItems(items, 0, (dialogInterface, i) -> {
                if(!charSequenceList.isEmpty())
                    charSequenceList.clear();
                charSequenceList.add(items[i]);
            }).setPositiveButton("Ok", (dialogInterface, i) -> {
                Log.d(TAG, CLASSNAME +"bottone ok cliccato per aggiungere itinerario alla playlist selezionata");
                Integer playlistId = mappa.get(charSequenceList.get(0));
                if(playlistId != null) {
                    String response = playlistViewModel.addToPlaylistContent(itinerario_id,playlistId);
                }
            }).setNegativeButton("Cancel", (dialogInterface, i) ->{
                Log.d(TAG, CLASSNAME +"bottone cancel cliccato");
                dialogInterface.dismiss();
            });
            builder.create().show();
        }
    }



